package se.boalbert.covidtweeter.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import se.boalbert.covidtweeter.model.ListTestCenter;
import se.boalbert.covidtweeter.model.TestCenter;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RestClient {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RestClient.class);

	@Value("${API_URI}")
	private String API_URI;

	@Value("${CLIENT_ID}")
	private String CLIENT_ID;

	@Value("${CLIENT_SECRET}")
	private String CLIENT_SECRET;

	public Map<String, TestCenter> extractAllCenters() {
		ListTestCenter listTestCenter = getFullResponseFromApi();

		return listTestCenter.getTestcenters().stream()
				.collect(Collectors.toMap(TestCenter :: getTitle, testCenter -> testCenter));

	}

	public ListTestCenter getFullResponseFromApi() {
		WebClient webClient = WebClient.create();

		try {
			return webClient.get().uri(API_URI)
					.headers(httpHeaders -> {
						httpHeaders.set("client_id", CLIENT_ID);
						httpHeaders.set("client_secret", CLIENT_SECRET);
					})
					.retrieve()
					.bodyToMono(ListTestCenter.class)
					.block();

		} catch (WebClientResponseException ex) {
			log.error("Error - Response Code: {} ", ex.getRawStatusCode());
			log.error("Error - Response Body: {} ", ex.getResponseBodyAsString());
			log.error("WebClientResponseException in getFullResponseFromApi(): ", ex);
			throw ex;

		} catch (WebClientRequestException ex) {
			log.error("Error - URI: {} ", ex.getUri());
			log.error("WebClientResponseException in retrieveVaccinationData(): ", ex);
			throw ex;
		}

	}

	public Map<String, TestCenter> findOpenTimeSlots(Map<String, TestCenter> allTestCenters) {

		return allTestCenters.entrySet().stream()
				.filter(map -> map.getValue().getTimeslots() != null)
				.filter(map -> map.getValue().getTimeSlots() != 0)
				.collect(Collectors.toMap(Map.Entry :: getKey, Map.Entry :: getValue));
	}
}
