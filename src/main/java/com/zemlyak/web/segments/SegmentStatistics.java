package com.zemlyak.web.segments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

import static java.lang.Long.max;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SegmentStatistics {

    @JsonProperty("segmentId")
    private Integer segmentId;

    @JsonProperty("countryCode")
    private CountryCode countryCode;

    @JsonProperty("totalProfiles")
    private Long totalProfiles;

    @JsonProperty("totalActiveProfiles")
    private Long totalActiveProfiles;

    @JsonProperty("totalSleepingProfiles")
    private Long totalSleepingProfiles;

    @JsonProperty("updateDate")
    private Date updateDate;

    @JsonProperty("totalThirdPartyProfiles")
    private Long totalThirdPartyProfiles;
}
