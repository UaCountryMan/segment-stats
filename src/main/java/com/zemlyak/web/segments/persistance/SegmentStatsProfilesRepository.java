package com.zemlyak.web.segments.persistance;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SegmentStatsProfilesRepository extends CrudRepository<SegmentStatsProfilesEntity, SegmentStatsBaseEntity.Id> {
    List<SegmentStatsProfilesEntity> findAllById_SegmentId(Integer segmentId);
}
