package net.urbanmc.antt;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.gson.Gson;

import net.urbanmc.antt.gson.ToggledNation;
import net.urbanmc.antt.gson.ToggledNationList;

public class Storage {

	private static Storage instance;

	private final File file = new File("plugins/ANTT", "storage.json");

	private List<ToggledNation> toggled;

	static {
		if (instance == null) {
			instance = new Storage();
		}
	}

	public static Storage getInstance() {
		return instance;
	}

	private Storage() {
		setupFile();
		loadGson();
	}

	private void setupFile() {
		if (!file.getParentFile().isDirectory()) {
			file.getParentFile().mkdir();
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void loadGson() {
		try {
			Scanner scanner = new Scanner(file);

			toggled = new Gson().fromJson(scanner.nextLine(), ToggledNationList.class).getToggledNations();

			scanner.close();
		} catch (Exception ex) {
			if (!(ex instanceof NoSuchElementException)) {
				ex.printStackTrace();
			}

			toggled = new ArrayList<ToggledNation>();
		}
	}

	public void saveGson() {
		try {
			PrintWriter writer = new PrintWriter(file);

			writer.write(new Gson().toJson(new ToggledNationList(toggled)));

			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public List<ToggledNation> getToggled() {
		return this.toggled;
	}
}
