package se.boalbert.covidtweeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.boalbert.covidtweeter.model.TimeSlot;
import se.boalbert.covidtweeter.scraper.AvailableTimeSlotScraper;
import se.boalbert.covidtweeter.service.TwitterService;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.List;

@SpringBootApplication
public class CovidTweeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidTweeterApplication.class, args);
	}
}
