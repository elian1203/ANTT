package net.urbanmc.antt.gson;

import java.util.List;

public class ToggledNationList {

	private List<ToggledNation> toggled;

	public ToggledNationList(List<ToggledNation> toggled) {
		this.toggled = toggled;
	}

	public List<ToggledNation> getToggledNations() {
		return this.toggled;
	}
}
