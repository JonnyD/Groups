package groups;

import groups.manager.ConfigManager;
import groups.storage.Dao;

import org.bukkit.plugin.java.JavaPlugin;

public class Groups extends JavaPlugin {

	private static Groups instance;
	private ConfigManager configManager;
	private Dao dao;
	
	public void onEnable() {
		instance = this;
		configManager = new ConfigManager();
		dao = new Dao();
	}
	
	public void onDisable() {
		
	}
	
	public static Groups getInstance() {
		return instance;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public Dao getDao() {
		return dao;
	}
}
