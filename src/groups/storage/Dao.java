package groups.storage;

import groups.Groups;
import groups.manager.ConfigManager;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.Member;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dao extends MyDatabase {

	Groups plugin;
	ConfigManager configManager;
	
	public Dao() {
		super(Groups.getInstance());
		
		plugin = Groups.getInstance();
		configManager = plugin.getConfigManager();
		
		initializeDatabase(
			configManager.getDriver(),
			configManager.getUrl(),
			configManager.getUsername(),
			configManager.getPassword(),
			configManager.getIsolation(),
			configManager.isLogging(),
			false
		);
		
		generateTables();
	}
	
	@Override
	protected List<Class<?>> getDatabaseClasses() {
		return Arrays.asList(
			GroupMember.class,
			Group.class,
			Member.class
		);
	}
	
	private void generateTables() {
		//System.out.println(getDatabase().find(Group.class));
	}
	
	public void save(Object object) {
		getDatabase().save(object);
	}
	
	public void update(Object object) {
		getDatabase().update(object);
	}
	
	public void delete(Object object) {
		getDatabase().delete(object);
	}
	
	public Map<String, Group> findAllGroups() {
		return (Map<String, Group>) getDatabase().find(Group.class)  
			    .setMapKey("name")  
			    .findMap();  
	}
	
	public Map<String, Member> findAllMembers() {
		return (Map<String, Member>) getDatabase().find(Member.class)
				.setMapKey("name")
				.findMap();
	}
}
