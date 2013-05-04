package groups;

import org.bukkit.plugin.java.JavaPlugin;

public class Groups extends JavaPlugin {

	private static Groups instance;
	
	public void onEnable() {
		instance = this;
	}
	
	public void onDisable() {
		
	}
	
	public static Groups getInstance() {
		return instance;
	}
}
