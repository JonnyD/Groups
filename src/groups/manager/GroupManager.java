package groups.manager;

import java.util.Collection;
import java.util.Map;

import groups.Groups;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;
import groups.model.Member;
import groups.storage.Dao;

public class GroupManager {

	private Dao dao;
	private Map<String, Group> groups;
	private Map<String, Member> members;
	
	public GroupManager() {
		this.dao = Groups.getInstance().getDao();
	}
	
	public void loadGroups() {
		groups = dao.findAllGroups();
		loadMembers();
	}
	
	public void loadMembers() {
		members = dao.findAllMembers();
	}
	
	public void createGroup(String name, String username) {
		createGroup(name, username, false);
	}
	
	public void createGroup(String name, String username, Boolean isPersonal) {
		Group group = new Group(name);
		group.setPersonal(isPersonal);
		
		Member member = members.get(username);
		if(member == null) {
			member = new Member(username);
			addMember(member);
		}
		
		GroupMember groupMember = new GroupMember();
		groupMember.setMember(member);
		groupMember.setGroup(group);
		groupMember.setRole(Role.ADMIN);
		
		group.addGroupMember(groupMember);
		member.addGroupMember(groupMember);
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
	
	public void updateGroup(Group group) {
		dao.update(group);
	}
	
	public void saveGroup(Group group) {
		dao.save(group);
	}
	
	public void deleteGroup(Group group) {
		dao.delete(group);
	}
	
	public void addMemberToGroup(Group group, String username, Role role) {
		Member member = members.get(username);
		if(member == null) {
			member = new Member(username);
			addMember(member);
		}
		
		GroupMember groupMember = new GroupMember();
		groupMember.setMember(member);
		groupMember.setGroup(group);
		groupMember.setRole(Role.ADMIN);
		
		group.addGroupMember(groupMember);
		member.addGroupMember(groupMember);
		saveGroup(group);
	}
	
	public void removeMemberFromGroup(Group group, GroupMember groupMember) {
		group.removeGroupMemmber(groupMember);
		saveGroup(group);
	}
	
	public Member getMember(String username) {
		return members.get(username);
	}
	
	public void addMember(Member member) {
		members.put(member.getName(), member);
		saveMember(member);
	}
	
	public void saveMember(Member member) {
		dao.save(member);
	}
	
	public void deleteMember(GroupMember member) {
		dao.delete(member);
	}
	
}
