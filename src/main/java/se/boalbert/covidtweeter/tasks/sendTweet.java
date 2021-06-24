package se.boalbert.covidtweeter.tasks;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.boalbert.covidtweeter.model.TestCenter;
import se.boalbert.covidtweeter.scraper.AvailableTimeSlotScraper;
import se.boalbert.covidtweeter.service.RestClient;
import se.boalbert.covidtweeter.service.TwitterService;
import se.boalbert.covidtweeter.util.MergeData;

import java.util.List;
import java.util.Map;

import static se.boalbert.covidtweeter.scraper.AvailableTimeSlotScraper.ageGroup;

@Configuration
@EnableScheduling
public class sendTweet {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AvailableTimeSlotScraper.class);

	private final TwitterService twitterService;
	private final AvailableTimeSlotScraper availableTimeSlotScraper;
	private final RestClient restClient;
	private final MergeData mergeData;

	public sendTweet(TwitterService twitterService, AvailableTimeSlotScraper availableTimeSlotScraper, RestClient restClient, MergeData mergeData) {
		this.twitterService = twitterService;
		this.availableTimeSlotScraper = availableTimeSlotScraper;
		this.restClient = restClient;
		this.mergeData = mergeData;
	}

	@Scheduled(fixedRate = 900000, initialDelay = 5000) // 15 min
	private void tweet() {

		log.info(">>> Running scheduled job: sendTweet -> tweet()");

		Map<String, TestCenter> scrapedCenters = availableTimeSlotScraper.scrapeData();
		Map<String, TestCenter> restCenters = restClient.findOpenTimeSlots(restClient.extractAllCenters());

		List<String> tweets = twitterService.createTweets(mergeData.mergeMapsAndReturnUniqueTestCenters(restCenters, scrapedCenters), ageGroup);

		twitterService.sendTweet(tweets);

	}
}
