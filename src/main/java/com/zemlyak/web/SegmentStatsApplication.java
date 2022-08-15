package com.zemlyak.web;

import com.neovisionaries.i18n.CountryCode;
import com.zemlyak.web.segments.SegmentStatisticService;
import com.zemlyak.web.segments.SegmentStatistics;
import com.zemlyak.web.segments.persistance.SegmentStatsProfilesEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@SpringBootApplication
public class SegmentStatsApplication {
	private static final List<CountryCode> SUPPORTED_COUNTRIES = new ArrayList<>(Arrays
			.asList(null, CountryCode.US, CountryCode.AZ));


	@Autowired
	private SegmentStatisticService statisticService;

	@GetMapping("/segments/stats")
	public List<SegmentStatsProfilesEntity> findAllStats() {
		return statisticService.findAllProfileStats();
	}

	@GetMapping("/segments/stats/batch-load")
	public StandartResp batchLoad() {
		Random rand = new Random();
		Date now = new Date();
		List<SegmentStatistics> segmentStatistics = IntStream
				.range(1, 100)
				.boxed()
				.flatMap(segmentId -> SUPPORTED_COUNTRIES
						.stream()
						.map(country -> new SegmentStatistics()
								.setSegmentId(segmentId)
								.setCountryCode(country)
								.setTotalActiveProfiles(100L + rand.nextLong(10))
								.setTotalSleepingProfiles(10 + rand.nextLong(5))
								.setTotalProfiles(115L + rand.nextLong(5))
								.setUpdateDate(now)
						)
				)
				.collect(Collectors.toList());
		statisticService.updateProfilesStatisticsV2(segmentStatistics);
		return StandartResp.ok();
	}

	public static void main(String[] args) {
		SpringApplication.run(SegmentStatsApplication.class, args);
	}

	@Data
	static class StandartResp {
		private final String status;

		static StandartResp ok() {
			return new StandartResp("ok");
		}
	}

}
