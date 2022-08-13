package com.zemlyak.web.segments.persistance;

import org.springframework.data.repository.CrudRepository;

public interface SegmentStatsAllRepository extends CrudRepository<SegmentStatsAllEntity, SegmentStatsBaseEntity.Id> {
}
