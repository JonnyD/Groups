package groups.manager;

import java.util.Collection;
import java.util.Map;

import groups.Groups;
import groups.model.Group;
import groups.model.Member;
import groups.model.Member.Role;
import groups.storage.Dao;

public class GroupManager {

	private Dao dao;
	private Map<String, Group> groups;
	
	public GroupManager() {
		this.dao = Groups.getInstance().getDao();
	}
	
	public void loadGroups() {
		groups = dao.findAllGroups();
	}
	
	public void createGroup(String name, String username) {
		createGroup(name, username, false);
	}
	
	public void createGroup(String name, String username, Boolean isPersonal) {
		Group group = new Group(name);
		group.setPersonal(isPersonal);
		Member member = new Member(group, username);
		member.setRole(Role.ADMIN);
		group.addMember(member);
		addGroup(group);
	}
	
	public void createPersonalGroup(String name, String username) {
		createGroup(name, username, true);
	}
	
	public Collection<Group> getAllGroups() {
		return groups.values();
	}
	
	public Group getGroupByName(String name) {
		return groups.get(name);
	}
	
	public void addGroup(Group group) {
		groups.put(group.getName(), group);
		saveGroup(group);
	}
	
	public void removeGroup(Group group) {
		groups.remove(group);
		deleteGroup(group);
	}
	
	public void addMember(Group group, String username, Role role) {
		Member member = new Member(group, username);
		member.setRole(role);
		group.addMember(member);
		saveGroup(group);
	}
	
	public void removeMember(Group group, Member member) {
		group.removeMember(member);
		deleteMember(member);
	}
	
	public void updateGroup(Group group) {
		dao.update(group);
	}
	
	public void saveGroup(Group group) {
		dao.save(group);
	}
	
	public void deleteGroup(Group group) {
		dao.delete(group);
	}
	
	public void deleteMember(Member member) {
		dao.delete(member);
	}
	
}
