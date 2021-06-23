package se.boalbert.covidtweeter.scraper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AvailableTestCenterScraperTest {

	@Test
	void TestExtractOpenTimeslots() {

		AvailableTimeSlotScraper availableTimeSlotScraper = new AvailableTimeSlotScraper();

		String freeSlotsText1 = "(Mer än 600 lediga tider kommande 2)";
		String freeSlotsText2 = "(Mer än 100 lediga tider kommande 2 veckor)";
		String freeSlotsText3 = "(Mer än 1 lediga)";
		String freeSlotsText4 = "(Mer än 1 ledig tid kommande veckor)";
		String freeSlotsText5 = "Mer än 200 lediga tider kommandor";
		String freeSlotsText6 = "200 lediga tider kommandor";
		String freeSlotsText7 = "200";
		String freeSlotsText8 = "";

		assertEquals("600", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText1));
		assertEquals("100", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText2));
		assertEquals("1", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText3));
		assertEquals("1", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText4));
		assertEquals("200", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText5));
		assertEquals("200", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText6));

		assertEquals("200", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText7));
		assertNotEquals("200", availableTimeSlotScraper.extractOpenTimeslots(freeSlotsText8));

	}

	@Test
	void removeUpdatedText() {

		AvailableTimeSlotScraper availableTimeSlotScraper = new AvailableTimeSlotScraper();

		String updatedText = "Senast uppdaterad: 2021-07-01 13:07";
		String updatedText2 = "Senast uppdaterad: 2021-06-31 12:50";
		String updatedText3 = "uppdaterad: 2021-06-18 00:00";

		assertEquals("2021-07-01 13:07", availableTimeSlotScraper.removeUpdatedText(updatedText));
		assertEquals("2021-06-31 12:50", availableTimeSlotScraper.removeUpdatedText(updatedText2));
		assertEquals("2021-06-18 00:00", availableTimeSlotScraper.removeUpdatedText(updatedText3));

	}

	@Test
	void extractAgeGroup() {

		AvailableTimeSlotScraper availableTimeSlotScraper = new AvailableTimeSlotScraper();

		String extractedAge = availableTimeSlotScraper.extractAgeGroup("Här finns mottagningar som har lediga tider för webbokning just nu. Från den  22 juni kan du som är född 1981 eller tidigare samt du i riskgrupp över 18 år boka tid.");

		assertEquals("Född 1981 eller tidigare.", extractedAge);
		System.out.println(">>> " + extractedAge);

	}

	@Test
	void extractTestCenter() {

		AvailableTimeSlotScraper availableTimeSlotScraper = new AvailableTimeSlotScraper();

		String testCenterText = "Mark: Kronans Apotek / MediCheck Covid-19 Skene";
		String testCenterText2 = "Mariestad: Närhälsan Mariestad Sjukhus";
		String testCenterText3 = "Munkedal: Närhälsan Dingle Lantbruksskolan";
		String testCenterText4 = "Strömstad: Capio VC Strömstad";
		String testCenterText5 = "Göteborg: Närhälsan Scandinavium";

		assertEquals("Kronans Apotek / MediCheck Covid-19 Skene", availableTimeSlotScraper.extractTestCenterTitle(testCenterText));
		assertEquals("Närhälsan Mariestad Sjukhus", availableTimeSlotScraper.extractTestCenterTitle(testCenterText2));
		assertEquals("Närhälsan Dingle Lantbruksskolan", availableTimeSlotScraper.extractTestCenterTitle(testCenterText3));
		assertEquals("Capio VC Strömstad", availableTimeSlotScraper.extractTestCenterTitle(testCenterText4));
		assertEquals("Närhälsan Scandinavium", availableTimeSlotScraper.extractTestCenterTitle(testCenterText5));


	}

	@Test
	void extractMunicipality() {

		AvailableTimeSlotScraper availableTimeSlotScraper = new AvailableTimeSlotScraper();

		String testCenterText = "Mark: Kronans Apotek / MediCheck Covid-19 Skene";
		String testCenterText2 = "Mariestad: Närhälsan Mariestad Sjukhus";
		String testCenterText3 = "Munkedal: Närhälsan Dingle Lantbruksskolan";
		String testCenterText4 = "Strömstad: Capio VC Strömstad";
		String testCenterText5 = "Göteborg: Närhälsan Scandinavium";

		assertEquals("Mark", availableTimeSlotScraper.extractMunicipalityName(testCenterText));
		assertEquals("Mariestad", availableTimeSlotScraper.extractMunicipalityName(testCenterText2));
		assertEquals("Munkedal", availableTimeSlotScraper.extractMunicipalityName(testCenterText3));
		assertEquals("Strömstad", availableTimeSlotScraper.extractMunicipalityName(testCenterText4));
		assertEquals("Göteborg", availableTimeSlotScraper.extractMunicipalityName(testCenterText5));

	}
}