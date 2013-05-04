package groups.storage;

import java.util.List;

import groups.Groups;
import groups.manager.ConfigManager;

public class Dao extends MyDatabase {

	Groups plugin = Groups.getInstance();
	ConfigManager configManager = plugin.getConfigManager();
	
	public Dao() {
		super(Groups.getInstance());
		
		initializeDatabase(
			configManager.getDriver(),
			configManager.getUrl(),
			configManager.getUsername(),
			configManager.getPassword(),
			configManager.getIsolation(),
			configManager.isLogging(),
			configManager.isRebuild()
		);
	}
	
	@Override
	protected List<Class<?>> getDatabaseClasses() {
		return null; //TODO
	}

}
