package groups.manager;

import groups.Groups;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;
import groups.model.Member;
import groups.storage.Dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupManager {

	private Dao dao;
	private Map<String, Group> groups = new HashMap<String, Group>();
	private Map<String, Member> members = new HashMap<String, Member>();
	
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
    
	public void createPersonalGroup(String name, String username) {
		createGroup(name, username, true);
	}
	
	public void createGroup(String name, String username, Boolean isPersonal) {
		Group group = new Group(name);
		group.setPersonal(isPersonal);
		
		Member member = getOrCreateMember(username);
		GroupMember groupMember = createGroupMember(group, member, Role.ADMIN);
		
		group.addGroupMember(groupMember);
		member.addGroupMember(groupMember);
        
		addGroup(group);
	}
	
	public Collection<Group> getAllGroups() {
		return groups.values();
	}
	
	public Group addGroup(Group group) {
		String groupName = group.getNormalizedName();
		
        if(isGroup(groupName)) {
            return null;
        }
        
		groups.put(groupName, group);
		saveGroup(group);
        
        return group;
	}
	
	public void removeGroup(Group group) {
		String groupName = group.getNormalizedName();
		
        if(!isGroup(groupName)) {
            return;
        }
        
		groups.remove(groupName);
        members.remove(groupName);
        
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
    
    public boolean isGroup(String groupName) {
        return getGroupByName(groupName) != null;
    }
    
    public Group getGroupByName(String groupName) {
        Group group = groups.get(groupName);
        
        if(group == null) {
        	group = dao.findGroupByName(groupName);
        	
        	if(group == null) {
        		return null;
        	}
        	
    		groups.put(group.getNormalizedName(), group);
        }
        
        return group;
    }
    
    public GroupMember createGroupMember(Group group, Member member, Role role) {        
		GroupMember groupMember = new GroupMember();
		groupMember.setMember(member);
		groupMember.setGroup(group);
		groupMember.setRole(role);
        
        return groupMember;
    }
	
	public void addMemberToGroup(Group group, String username, Role role) {
        Member member = getOrCreateMember(username);
		GroupMember groupMember = createGroupMember(group, member, role);
		
		group.addGroupMember(groupMember);
		member.addGroupMember(groupMember);
        
		saveGroup(group);
	}
	
	public void removeMemberFromGroup(Group group, GroupMember groupMember) {
		Member member = getOrCreateMember(groupMember.getMemberName());
        
		member.removeGroupMember(groupMember);
		group.removeGroupMemmber(groupMember);		
        
		saveGroup(group);
	}
	
	public Member getOrCreateMember(String username) {
		Member member = members.get(username);
        
		if(member == null) {
			member = new Member(username);
			addMember(member);
		}
        
		return member;
	}
	
	public Member getMember(String username) {
		return members.get(username);
	}
	
	public Collection<Member> getAllMembers() {
		return members.values();
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
	
	public List<Group> getMembersGroups(String username) {
		Member member = getMember(username);
		List<Group> groups = new ArrayList<Group>();
		for(GroupMember gm : member.getGroupMembers().values()) {
			Group group = gm.getGroup();
			groups.add(group);
		}
		return groups;
	}
	
	public GroupMember getGroupMemberByUsernameGroup(String username, String groupName) {
		Member member = getMember(username);
		Map<String, GroupMember> groupMembers = member.getGroupMembers();
		GroupMember groupMember = groupMembers.get(groupName);
		return groupMember;
	}
}
