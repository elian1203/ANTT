package net.urbanmc.antt.gson;

import java.util.List;

public class ToggledNation {

	private String nation;
	private List<String> toggledTowns;

	public ToggledNation(String nation) {
		this.nation = nation;
	}

	public String getNation() {
		return this.nation;
	}

	public List<String> getToggledTowns() {
		return this.toggledTowns;
	}

	public void addToggledTown(String town) {
		this.toggledTowns.add(town);
	}

	public void removeToggledTown(String town) {
		this.toggledTowns.remove(town);
	}
}
