package se.boalbert.covidtweeter.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import se.boalbert.covidtweeter.model.TimeSlot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AvailableTimeSlotScraper {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AvailableTimeSlotScraper.class);
	public static String lastUpdated = "";
	public List<TimeSlot> allSlots = new ArrayList<>();

	public List<TimeSlot> scrapeData() {

		allSlots.clear();

		try {
			// Whole HTML-document
			Document doc = Jsoup.connect("https://www.vgregion.se/ov/vaccinationstider/bokningsbara-tider/").get();

			// Timeslot list
			Elements timeslots = doc.getElementsByClass("media-body");

			// "Här finns mottagningar som har lediga tider för webbokning just nu. Sedan 8:e juni kan du som är född 1976 eller tidigare samt du i riskgrupp över 18 år boka tid."
			String ageGroup = doc.getElementsContainingOwnText("född").text();

			// Senast uppdaterad: 2021-06-18 13:07
			String updated = doc.getElementsByTag("hr").next().text();

			log.info(">>> Page updated: " + lastUpdated);

			if (!lastUpdated.equals(updated)) {

				lastUpdated = updated;

				for (Element timeslot : timeslots) {

					TimeSlot newSlot = new TimeSlot();

					// Göteborg: Drive In Nötkärnan Slottskogen
					String heading = timeslot.select("h3").text();
					Element link = timeslot.select("a").first();

					// Boka tid via webben hos Drive In Nötkärnan Slottskogen
					String linkText = link.text();

					// https://formular.1177.se/etjanst/ad7ed879-138d-4cfd-ac94-83c0af422e44?externalApplication=COVID_SE2321000131-E000000016315
					String linkHref = link.attr("href");

					// (Mer än 500 lediga tider kommande 2 veckor)
					Element spanText = timeslot.select("span").first();
					String openSlots = spanText.text();

					newSlot.setHeading(heading);
					newSlot.setLinkText(linkText);
					newSlot.setLinkHref(linkHref);
					newSlot.setOpenSlots(extractOpenTimeslots(openSlots));
					newSlot.setUpdated(removeUpdatedText(updated));
					newSlot.setAgeGroup(extractAgeGroup(ageGroup));

					if (heading.contains("Göteborg")) {
						allSlots.add(newSlot);
					}
				}

				log.info(">>> Scraped {} slots", allSlots.size());
				return allSlots;

			} else {
				log.info(">>> Scraper: no updates");
			}
		} catch (IOException ex) {
			log.error(">>> Error parsing document when scraping...");
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

	public String extractOpenTimeslots(String openSlotsText) {
		// Split string to:
		// - (Mer än 500
		// - lediga tider kommande 2 veckor)"
		String[] splitAtWord = openSlotsText.split("lediga");
		// Take first part of the String, "(Mer än 500"
		// Replace everything that is not a number with "", i.e nothing
		// Returns "500"
		return splitAtWord[0].replaceAll("[^\\d]", "");
	}

	public String removeUpdatedText(String updated) {
		String[] splitAtColon = updated.split(":");
		return splitAtColon[1].trim() + ":" + splitAtColon[2].trim();
	}

	public String extractAgeGroup(String ageGroup) {
		String[] splitString = ageGroup.split("född");
		String[] splitString2 = splitString[1].split("samt");

		return "Född " + splitString2[0].trim() + ".";
	}
}
