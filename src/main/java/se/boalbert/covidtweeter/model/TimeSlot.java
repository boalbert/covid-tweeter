package se.boalbert.covidtweeter.model;

public class TimeSlot {
	public String heading;
	public String linkText;
	public String linkHref;
	public String openSlots;
	public String ageGroup;
	public String updated;

	@Override
	public String toString() {
		return "TimeSlot{" +
				"heading='" + heading + '\'' +
				", linkText='" + linkText + '\'' +
				", linkHref='" + linkHref + '\'' +
				", openSlots='" + openSlots + '\'' +
				", updated='" + updated + '\'' +
				'}';
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getLinkHref() {
		return linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}

	public String getOpenSlots() {
		return openSlots;
	}

	public void setOpenSlots(String openSlots) {
		this.openSlots = openSlots;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public TimeSlot() {
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public TimeSlot(String heading, String linkText, String linkHref, String openSlots, String ageGroup, String updated) {
		this.heading = heading;
		this.linkText = linkText;
		this.linkHref = linkHref;
		this.openSlots = openSlots;
		this.ageGroup = ageGroup;
		this.updated = updated;
	}
}


