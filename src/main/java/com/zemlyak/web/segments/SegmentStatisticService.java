package com.zemlyak.web.segments;

import com.zemlyak.web.segments.persistance.SegmentStatsBaseEntity;
import com.zemlyak.web.segments.persistance.SegmentStatsProfilesEntity;
import com.zemlyak.web.segments.persistance.SegmentStatsProfilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void updateProfilesStatistics(List<SegmentStatistics> segmentStatistics) {
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

    public void updateProfilesStatisticsV2(List<SegmentStatistics> segmentStatistics) {
        Map<Integer, List<SegmentStatistics>> statsBySegment = segmentStatistics
                .stream()
                .collect(groupingBy(SegmentStatistics::getSegmentId));

        statsBySegment.forEach((segmentId, segmentStats) -> {
            Set<SegmentStatsBaseEntity.Id> persistedIds = profilesStatsRepo
                    .findAllById_SegmentId(segmentId)
                    .stream()
                    .map(SegmentStatsBaseEntity::getId)
                    .collect(Collectors.toSet());

            profilesStatsRepo.saveAll(segmentStats
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
                    .collect(Collectors.toList())
            );
        });
    }

    public List<SegmentStatsProfilesEntity> findAllProfileStats() {
        List<SegmentStatsProfilesEntity> result = new ArrayList<>();
        profilesStatsRepo.findAll().forEach(result::add);
        return result;
    }
}
