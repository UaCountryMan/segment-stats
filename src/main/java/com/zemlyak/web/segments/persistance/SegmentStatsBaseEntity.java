package com.zemlyak.web.segments.persistance;

import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@Accessors(chain = true)
public abstract class SegmentStatsBaseEntity implements Persistable<SegmentStatsBaseEntity.Id> {
    public static final String EMPTY_COUNTRY_CODE = "";
    static final String TABLE_NAME = "segment_stats";

    @EmbeddedId
    private Id id;

    @Column(name = "update_date")
    private Date updateDate;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Embeddable
    @Data
    @Accessors(chain = true)
    public static class Id implements Serializable {

        @Column(name = "segment_id")
        private Integer segmentId;

        @Column(name = "country_code", length = 2)
        private String countryCode;


        public static Id build(int segmentId, CountryCode countryCode) {
            return new Id()
                    .setSegmentId(segmentId)
                    .setCountryCode(countryCode == null ? EMPTY_COUNTRY_CODE : countryCode.getAlpha2());
        }
    }

    @Embeddable
    @Data
    @Accessors(chain = true)
    public static class ProfilesStats {

        @Column(name = "total_profiles_amount")
        private Long totalProfiles;

        @Column(name = "active_profiles_amount")
        private Long totalActiveProfiles;

        @Column(name = "sleeping_profiles_amount")
        private Long totalSleepingProfiles;
    }

    @Embeddable
    @Data
    @Accessors(chain = true)
    public static class ThirdPartyStats {

        @Column(name = "total_third_party_profiles")
        private Long totalThirdPartyProfiles;

    }
}