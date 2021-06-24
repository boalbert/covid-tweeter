package se.boalbert.covidtweeter.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import se.boalbert.covidtweeter.model.TestCenter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AvailableTimeSlotScraper {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AvailableTimeSlotScraper.class);
	public static String lastUpdated = "";
	public static String ageGroup;

	public Map<String, TestCenter> scrapeData() {

		Map<String, TestCenter> allSlotsMap = new LinkedHashMap<>();

		try {
			// Whole HTML-document
			Document htmlDocument = Jsoup.connect("https://www.vgregion.se/ov/vaccinationstider/bokningsbara-tider/").get();

			// TestCenter divs
			Elements testCenters = htmlDocument.getElementsByClass("media-body");

			// "Här finns mottagningar som har lediga tider för webbokning just nu. Sedan 8:e juni kan du som är född 1976 eller tidigare samt du i riskgrupp över 18 år boka tid."
			String agesText = htmlDocument.getElementsContainingOwnText("född").text();

			ageGroup = extractAgeGroup(agesText);
			// Senast uppdaterad: 2021-06-18 13:07
			String updatedText = htmlDocument.getElementsByTag("hr").next().text();

			log.info(">>> Page updated: " + updatedText);

			if (!lastUpdated.equals(updatedText)) {
				lastUpdated = updatedText;

//				Loop over all testcenter-divs and insert data into testCenter-object
				for (Element testCenter : testCenters) {
					// Göteborg: Drive In Nötkärnan Slottskogen
					String heading = testCenter.select("h3").text();

					// https://formular.1177.se/etjanst/ad7ed879-138d-4cfd-ac94-83c0af422e44?externalApplication=COVID_SE2321000131-E000000016315
					String linkHref = testCenter.select("a").first().attr("href");

					// (Mer än 500 lediga tider kommande 2 veckor)
					String openSlots = testCenter.select("span").first().text();

					// Chop up text and prepare it for creation of object
					String title = extractTestCenterTitle(heading);
					String municipalityName = extractMunicipalityName(heading);
					Long timeSlots = extractOpenTimeslots(openSlots);
					String updated = extractOnlyDate(updatedText);

					// Populate object
					TestCenter newSlot = new TestCenter();
					newSlot.setTitle(title);
					newSlot.setMunicipalityName(municipalityName);
					newSlot.setUrlBooking(linkHref);
					newSlot.setTimeSlots(timeSlots);
					newSlot.setUpdated(updated);
					newSlot.setAgeGroup(ageGroup);

					allSlotsMap.put(extractTestCenterTitle(heading), newSlot);
				}

				log.info(">>> Scraped {} slots", allSlotsMap.size());
				return allSlotsMap;

			} else {
				log.info(">>> Scraper: no updates");
			}
		} catch (IOException ex) {
			log.error(">>> Error parsing document when scraping...");
			ex.printStackTrace();
		}
		return allSlotsMap;
	}

	public String extractAgeGroup(String ageGroup) {
		String[] splitAtFodd = ageGroup.split("född");
		String[] splitAtSamt = splitAtFodd[1].split("samt");

		return "Född " + splitAtSamt[0].trim() + ".";
	}

	public String extractTestCenterTitle(String heading) {
		String[] splitHeading = heading.split(":");
		return splitHeading[1].trim();
	}

	public String extractMunicipalityName(String heading) {
		String[] splitHeading = heading.split(":");
		return splitHeading[0].trim();
	}

	public Long extractOpenTimeslots(String openSlotsText) {
		// Split string to:
		// - (Mer än 500
		// - lediga tider kommande 2 veckor)"
		String[] splitAtWord = openSlotsText.split("lediga");
		// Take first part of the String, "(Mer än 500"
		// Replace everything that is not a number with "", i.e nothing
		// Returns "500"
		return Long.valueOf(splitAtWord[0].replaceAll("[^\\d]", ""));
	}

	public String extractOnlyDate(String updated) {
		String[] splitAtColon = updated.split(":");
		return splitAtColon[1].trim() + ":" + splitAtColon[2].trim();
	}

}
