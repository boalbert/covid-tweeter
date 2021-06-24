package se.boalbert.covidtweeter.model;

public class TestCenter {
	public String title; // "Närhälsan Tjörn vårdcentral",
	public String hsaid; // "SE2321000131-E000000005514",
	public String municipalityName; // "Tjörn"
	public String municipality; // 1419
	public String urlBooking; // ""https://formular.1177.se/etjanst/ad7ed879-13...",
	public String urlContactCard; // "urlContactCard": "https://www.1177.se/Vastra-Gotaland/hitta-vard/kontaktkort/Narhalsan-Tjorn-vardcentral-Kallekarr/",
	public String urlContactCardText; // "1"
	public String testtype;
	public Long timeslots; // "5"
	public String ageGroup; // Född 1981 eller tidigare.
	public String updated; // "2021-06-23 22:46:41"

	public TestCenter(String title, String hsaid, String municipalityName, String municipality, String urlBooking, String urlContactCard, String urlContactCardText, String testtype, Long timeslots, String ageGroup, String updated) {
		this.title = title;
		this.hsaid = hsaid;
		this.municipalityName = municipalityName;
		this.municipality = municipality;
		this.urlBooking = urlBooking;
		this.urlContactCard = urlContactCard;
		this.urlContactCardText = urlContactCardText;
		this.testtype = testtype;
		this.timeslots = timeslots;
		this.ageGroup = ageGroup;
		this.updated = updated;
	}

	public String getUrlContactCardText() {
		return urlContactCardText;
	}

	public void setUrlContactCardText(String urlContactCardText) {
		this.urlContactCardText = urlContactCardText;
	}

	public String getTesttype() {
		return testtype;
	}

	public void setTesttype(String testtype) {
		this.testtype = testtype;
	}

	public Long getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(Long timeslots) {
		this.timeslots = timeslots;
	}

	public String getHsaid() {
		return hsaid;
	}

	public void setHsaid(String hsaid) {
		this.hsaid = hsaid;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getUrlContactCard() {
		return urlContactCard;
	}

	public void setUrlContactCard(String urlContactCard) {
		this.urlContactCard = urlContactCard;
	}

	public TestCenter() {
	}

	public String getMunicipalityName() {
		return municipalityName;
	}

	public void setMunicipalityName(String municipalityName) {
		this.municipalityName = municipalityName;
	}

	@Override
	public String toString() {
		return "TestCenter{" +
				"title='" + title + '\'' +
				", hsaid='" + hsaid + '\'' +
				", municipalityName='" + municipalityName + '\'' +
				", municipality='" + municipality + '\'' +
				", urlBooking='" + urlBooking + '\'' +
				", urlContactCard='" + urlContactCard + '\'' +
				", urlContactCardText='" + urlContactCardText + '\'' +
				", testtype='" + testtype + '\'' +
				", timeslots=" + timeslots +
				", ageGroup='" + ageGroup + '\'' +
				", updated='" + updated + '\'' +
				'}';
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlBooking() {
		return urlBooking;
	}

	public void setUrlBooking(String urlBooking) {
		this.urlBooking = urlBooking;
	}

	public Long getTimeSlots() {
		return timeslots;
	}

	public void setTimeSlots(Long timeSlots) {
		this.timeslots = timeSlots;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
}


