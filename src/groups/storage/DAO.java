package groups.storage;

import groups.Groups;
import groups.manager.ConfigManager;
import groups.model.Group;
import groups.model.Member;
import groups.model.Membership;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DAO extends EbeanDatabase {

	Groups plugin;
	ConfigManager configManager;
	
	public DAO() {
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
			true
		);
		
		generateTables();
	}
	
	@Override
	protected List<Class<?>> getDatabaseClasses() {
		return Arrays.asList(
			Membership.class,
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
	
	public Set<Group> findAllGroups() {
		return (Set<Group>) getDatabase().find(Group.class)
			    .setMapKey("name")  
			    .findMap();  
	}
	
	public Set<Member> findAllMembers() {
		return (Set<Member>) getDatabase().find(Member.class)
				.setMapKey("name")
				.findMap();
	}
	
	public Group findGroupByName(String name) {
		String normalizedName = name.toLowerCase();
		return getDatabase().find(Group.class)
				.where()
				.eq("name", normalizedName)
				.findUnique();
    }
	
	public Member findMemberByName(String name) {
		String normalizedName = name.toLowerCase();
		return getDatabase().find(Member.class)
				.where()
				.eq("name", normalizedName)
				.findUnique();
	}
	
	public Membership findMembershipByMemberAndGroup(Member member, Group group) {
		return getDatabase().find(Membership.class)
				.where()
				.eq("member_id", member.getId())
				.eq("group_id", group.getId())
				.findUnique();
	}
	
	public Membership findMembershipById(int id, boolean immutable) {
		return getDatabase().find(Membership.class)
				.setReadOnly(immutable)
				.setId(id)
				.findUnique();
	}
	
	public Group findGroupById(int id, boolean immutable) {
		return getDatabase().find(Group.class)
				.setReadOnly(immutable)
				.setId(id)
				.findUnique();
	}
}
