package se.boalbert.covidtweeter.util;

import org.springframework.stereotype.Component;
import se.boalbert.covidtweeter.model.TestCenter;
import se.boalbert.covidtweeter.service.RestClient;
import se.boalbert.covidtweeter.service.TwitterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MergeData {

	RestClient restClient;
	TwitterService twitterService;

	public MergeData(RestClient restClient, TwitterService twitterService) {
		this.restClient = restClient;
		this.twitterService = twitterService;
	}

	public List<TestCenter> mergeMapsAndReturnUniqueTestCenters(Map<String, TestCenter> restCenters, Map<String, TestCenter> scrapeCenters) {

//		Map<String, TestCenter> restMap =
//				restCenters.stream().collect(Collectors.toMap(TestCenter :: getTitle, testCenter -> testCenter));

//		Map<String, TestCenter> scrapeMap =
//				scrapeCenters.stream().collect(Collectors.toMap(TestCenter :: getTitle, testCenter -> testCenter));


		Map<String, TestCenter> merged = Stream.of(restCenters, scrapeCenters)
				.flatMap(map -> map.entrySet().stream())
				.collect(Collectors.toMap(
						Map.Entry :: getKey,
						Map.Entry :: getValue,
						(v1, v2) -> new TestCenter(v1.getTitle(), v1.getHsaid(), v1.getMunicipalityName(), v1.getMunicipality(), v1.getUrlBooking(), v1.getUrlContactCard(), v1.getUrlContactCardText(), v1.getTesttype(), v1.getTimeslots(), v2.getAgeGroup(), v1.getUpdated())
				));

		return new ArrayList<>(merged.values());

	}


}
