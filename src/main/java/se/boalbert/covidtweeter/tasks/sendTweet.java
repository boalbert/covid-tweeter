package se.boalbert.covidtweeter.tasks;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.boalbert.covidtweeter.model.TimeSlot;
import se.boalbert.covidtweeter.scraper.AvailableTimeSlotScraper;
import se.boalbert.covidtweeter.service.TwitterService;

import java.util.List;

@Configuration
@EnableScheduling
public class sendTweet {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AvailableTimeSlotScraper.class);

	private final TwitterService twitterService;
	private final AvailableTimeSlotScraper availableTimeSlotScraper;

	public sendTweet(TwitterService twitterService, AvailableTimeSlotScraper availableTimeSlotScraper) {
		this.twitterService = twitterService;
		this.availableTimeSlotScraper = availableTimeSlotScraper;
	}

	@Scheduled(fixedRate = 900000, initialDelay = 5000) // 15 min
	private void tweet() {

		log.info(">>> Running scheduled job: sendTweet -> tweet()");

		List<TimeSlot> timeSlots = availableTimeSlotScraper.scrapeData();
		List<String> tweets = twitterService.createTweets(timeSlots);

		twitterService.sendTweet(tweets);
	}

}
