package groups.manager;

import groups.Groups;
import groups.model.Group;
import groups.model.Membership;
import groups.model.Membership.Role;
import groups.model.Member;
import groups.storage.Dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		Membership membership = createMembership(group, member, Role.ADMIN);
		
		group.addMembership(membership);
		member.addMembership(membership);
        
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
		save(group);
        
        return group;
	}
	
	public void removeGroup(Group group) {
		String groupName = group.getNormalizedName();
		
        if(!isGroup(groupName)) {
            return;
        }
        
		groups.remove(groupName);
        members.remove(groupName);
        
		delete(group);
	}
	
	public void update(Object object) {
		dao.update(object);
	}
	
	public void delete(Object object) {
		dao.delete(object);
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
    
    public Membership createMembership(Group group, Member member, Role role) {        
    	Membership membership = new Membership();
    	membership.setMember(member);
    	membership.setGroup(group);
    	membership.setRole(role);
        
        return membership;
    }
	
	public void addMemberToGroup(Group group, String username, Role role) {
        Member member = getOrCreateMember(username);
        Membership membership = createMembership(group, member, role);
		
		group.addMembership(membership);
		member.addMembership(membership);
        
		save(group);
	}
	
	public void removeMemberFromGroup(Group group, Membership membership) {
		Member member = getOrCreateMember(membership.getMemberName());
        
		member.removeMembership(membership);
		group.removeMembership(membership);		
        
		save(group);
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
		save(member);
	}
	
	public void save(Object object) {
		dao.save(object);
	}
	
	public void deleteMembership(Membership membership) {
		dao.delete(membership);
	}
	
	public Role getRoleByName(String roleName) {
		Role role = null;
		for(Role r : Role.values()) {
			if(r.toString().equalsIgnoreCase(roleName)) {
				role = Role.valueOf(roleName.toUpperCase());
				break;
			}
		}
		return role;
	}
	
	public boolean isGroupNameValid(String name) {
		int minNameLength = 3;
		int maxNameLength = 16;
		boolean greaterThanEqualMin = name.length() >= minNameLength;
		boolean lessThanEqualMax = name.length() <= maxNameLength;
		if(!greaterThanEqualMin || !lessThanEqualMax) {
			return false;
		}
		return true;		
	}
}
