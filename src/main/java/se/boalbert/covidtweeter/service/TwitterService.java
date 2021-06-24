package se.boalbert.covidtweeter.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import se.boalbert.covidtweeter.model.TestCenter;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.List;

@Component
public class TwitterService {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TwitterService.class);

	public void sendTweet(List<String> tweets) {

		Twitter twitter = TwitterFactory.getSingleton();
		Status status;

		try {
			for (String tweet : tweets) {
				// Randomized timout to avoid getting blocked by twitter API
				long timeOut = (long) (Math.random() * (25000 - 15000 + 1) + 15000);

				status = twitter.updateStatus(tweet);
				log.info("Sending tweet:\n{} - {}", status.getCreatedAt(), status.getText());

				log.info("Waiting {} s...", (timeOut / 1000));
				Thread.sleep(timeOut);
			}
		} catch (TwitterException | InterruptedException ex) {
			log.error(">>> Error when sending tweets...");
			ex.printStackTrace();
		} finally {
			tweets.clear();
		}
	}

	public List<String> createTweets(List<TestCenter> testCenters, String ageGroup) {

		List<String> tweets = new ArrayList<>();

		for (TestCenter slot : testCenters) {

			String tweet = """
					%s: %s
					Lediga tider: %s
					Boka: %s
											
					Åldergrupp: %s
					Uppdaterad: %s
					""".formatted(slot.getMunicipalityName(), slot.getTitle(), slot.getTimeSlots(), slot.getUrlBooking(), ageGroup, slot.getUpdated());
			tweets.add(tweet);
		}

		log.info(">>> Generated {} tweets.", tweets.size());
		return tweets;
	}
}
