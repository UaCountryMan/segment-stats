package com.zemlyak.web.segments.persistance;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = SegmentStatsBaseEntity.TABLE_NAME)

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SegmentStatsProfilesEntity extends SegmentStatsBaseEntity {

    @Embedded
    private ProfilesStats profilesStats;
}
