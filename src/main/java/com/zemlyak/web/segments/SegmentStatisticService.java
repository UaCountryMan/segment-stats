package com.zemlyak.web.segments;

import com.zemlyak.web.segments.persistance.SegmentStatsBaseEntity;
import com.zemlyak.web.segments.persistance.SegmentStatsProfilesEntity;
import com.zemlyak.web.segments.persistance.SegmentStatsProfilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class SegmentStatisticService {
    @Autowired
    private final SegmentStatsProfilesRepository profilesStatsRepo;

    public void updateProfilesStatisticsCleanUpEarliest(List<SegmentStatistics> segmentStatistics) {
        Set<SegmentStatsBaseEntity.Id> persistedIds = StreamSupport
                .stream(profilesStatsRepo.findAll().spliterator(), false)
                .map(SegmentStatsBaseEntity::getId)
                .collect(Collectors.toSet());

        Map<Integer, List<SegmentStatsProfilesEntity>> entitiesBySegmentId = segmentStatistics
                .stream()
                .map(stat -> {
                    SegmentStatsBaseEntity.Id id = SegmentStatsBaseEntity.Id.build(stat.getSegmentId(), stat.getCountryCode());
                    return (SegmentStatsProfilesEntity) new SegmentStatsProfilesEntity()
                            .setProfilesStats(new SegmentStatsBaseEntity.ProfilesStats()
                                    .setTotalProfiles(stat.getTotalProfiles())
                                    .setTotalActiveProfiles(stat.getTotalActiveProfiles())
                                    .setTotalSleepingProfiles(stat.getTotalSleepingProfiles())
                            )
                            .setId(id)
                            .setNew(!persistedIds.contains(id))
                            .setUpdateDate(stat.getUpdateDate() != null ? stat.getUpdateDate() : new Date());
                })
                .collect(groupingBy(segmentStats -> segmentStats.getId().getSegmentId()));

        entitiesBySegmentId.forEach((key, value) -> profilesStatsRepo.saveAll(value));
    }
}
