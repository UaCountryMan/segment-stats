package com.zemlyak.web.segments.persistance;

import org.springframework.data.repository.CrudRepository;

public interface SegmentStatsThirdPartyRepository extends CrudRepository<SegmentStatsThirdPartyEntity, SegmentStatsBaseEntity.Id> {
}
