package io.github.naumnaum.BlockPlaceLimiter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.plugin.java.JavaPlugin;

public class DataHandler {
	JavaPlugin plugin;

	public DataHandler(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public <T> void saveFlatData(T t, String name) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(plugin.getDataFolder()
							+ File.separator + name));
			oos.writeObject(t);
			oos.flush();
			plugin.getLogger().info("File "+name+" saved.");
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T loadFlatData(String name) {
		T t = null;

		try {
			if (checkFile(name)) {
				plugin.getLogger().info("File " + name + " loaded.");
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
						plugin.getDataFolder() + File.separator + name));
				Object result = ois.readObject();
				ois.close();
				t = (T) result;
			} else {
				plugin.getLogger().info("File " + name + " created.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public boolean checkFile(String name) {
		File f = new File(plugin.getDataFolder() + File.separator + name);
		if (f.exists())
			return true;
		else
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
	}

}
