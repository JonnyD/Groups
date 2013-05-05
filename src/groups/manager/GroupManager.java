package groups.manager;

import groups.Groups;
import groups.model.Group;
import groups.model.Member;
import groups.storage.Dao;

public class GroupManager {

	private Dao dao;
	
	public GroupManager() {
		this.dao = Groups.getInstance().getDao();
	}
	
	public void createGroup(String name, String username) {
		Group group = new Group(name);
		Member member = new Member(group, username);
		member.setRole("admin");
		group.addMember(member);
		saveGroup(group);
	}
	
	public void saveGroup(Group group) {
		dao.save(group);
	}
}
