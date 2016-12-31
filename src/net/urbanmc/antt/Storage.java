package net.urbanmc.antt;

public class Storage {

	private static Storage instance;

	static {
		if (instance == null) {
			instance = new Storage();
		}
	}

	public static Storage getStorage() {
		return instance;
	}

	private Storage() {
		
	}
}
