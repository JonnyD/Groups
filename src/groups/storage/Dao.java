package groups.storage;

import groups.Groups;
import groups.manager.ConfigManager;
import groups.model.Group;
import groups.model.Member;

import java.util.Arrays;
import java.util.List;

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
		
		generateTables();
	}
	
	@Override
	protected List<Class<?>> getDatabaseClasses() {
		return Arrays.asList(
				Group.class
		);
	}
	
	private void generateTables() {
		//System.out.println(getDatabase().find(Group.class));
	}

}
