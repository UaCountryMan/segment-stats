package com.zemlyak.web.segments.persistance;

import org.springframework.data.repository.CrudRepository;

public interface SegmentStatsProfilesRepository extends CrudRepository<SegmentStatsProfilesEntity, SegmentStatsBaseEntity.Id> {
}
