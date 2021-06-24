package se.boalbert.covidtweeter.model;

import java.util.List;

public class ListTestCenter {
	List<TestCenter> testCenters;
	Long numberWeeks;

	public ListTestCenter(List<TestCenter> testCenters, Long numberWeeks) {
		this.testCenters = testCenters;
		this.numberWeeks = numberWeeks;
	}

	public ListTestCenter() {
	}

	public String toString() {
		return "ListTestCenter(testcenters=" + this.getTestcenters() + ", numberWeeks=" + this.getNumberWeeks() + ")";
	}

	public List<TestCenter> getTestcenters() {
		return this.testCenters;
	}

	public Long getNumberWeeks() {
		return this.numberWeeks;
	}

	public void setNumberWeeks(Long numberWeeks) {
		this.numberWeeks = numberWeeks;
	}

	public void setTestcenters(List<TestCenter> testcenters) {
		this.testCenters = testcenters;
	}
}
