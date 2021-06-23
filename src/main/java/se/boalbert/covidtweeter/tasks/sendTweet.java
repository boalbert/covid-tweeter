package se.boalbert.covidtweeter.tasks;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.boalbert.covidtweeter.model.TestCenter;
import se.boalbert.covidtweeter.scraper.AvailableTimeSlotScraper;
import se.boalbert.covidtweeter.service.RestClient;
import se.boalbert.covidtweeter.service.TwitterService;

import java.util.List;

@Configuration
@EnableScheduling
public class sendTweet {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AvailableTimeSlotScraper.class);

	private final TwitterService twitterService;
	private final AvailableTimeSlotScraper availableTimeSlotScraper;
	private final RestClient restClient;

	public sendTweet(TwitterService twitterService, AvailableTimeSlotScraper availableTimeSlotScraper, RestClient restClient) {
		this.twitterService = twitterService;
		this.availableTimeSlotScraper = availableTimeSlotScraper;
		this.restClient = restClient;
	}

	@Scheduled(fixedRate = 900000, initialDelay = 5000) // 15 min
	private void tweet() {

		log.info(">>> Running scheduled job: sendTweet -> tweet()");

		List<TestCenter> testCenters = availableTimeSlotScraper.scrapeData();
		List<String> tweets = twitterService.createTweets(testCenters);
		List<TestCenter> restCenters = restClient.extractAllCenters();

		log.info("Heres the tweets!");
		log.info(String.valueOf(tweets));

		log.info("Heres the slots!");
		log.info(String.valueOf(restCenters));
//		twitterService.sendTweet(tweets);
	}

}
