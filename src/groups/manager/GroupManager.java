package groups.manager;

import groups.Groups;
import groups.model.Group;
import groups.model.Group.GroupType;
import groups.model.Membership;
import groups.model.Membership.Role;
import groups.model.Member;
import groups.service.GroupService;
import groups.service.MemberService;
import groups.service.MembershipService;
import groups.storage.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GroupManager {

	private GroupService groupService;
	private MemberService memberService;
	private MembershipService membershipService;
	
	public GroupManager(GroupService groupService, MemberService memberService, MembershipService membershipService) {
		this.groupService = groupService;
		this.memberService = memberService;
		this.membershipService = membershipService;
	}
	
	public void addGroup() {
		
	}
	
	public Set<Group> getAllGroups() {
		return groupService.getAllGroups();
	}
	
	public void addGroup(String groupName, String memberName) {
		this.createGroup(groupName, memberName, false);
	}
	
	public void addPersonalGroup(String groupName, String memberName) {
		this.createGroup(groupName, memberName, true);
	}
	
	public void createGroup(String groupName, String memberName, Boolean isPersonal) {
		Group group = new Group(groupName);
		group.setPersonal(isPersonal);
		
		Member member = memberService.getOrCreateMember(memberName);		
		Membership membership = membershipService.getOrCreateMembership(member, group, Role.ADMIN);
		
		group.addMembership(membership);
		groupService.save(group);
	}
	
	public Group getGroupByName(String groupName) {
		return groupService.getGroupByName(groupName);
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
	
	public void deleteGroup(Group group) {
		groupService.delete(group);
	}
	
	public void updateType(Group group, GroupType groupType) {
		Group savedGroup = groupService.getGroupById(group.getId(), false);
		savedGroup.setType(groupType);
		groupService.update(savedGroup);
	}
	
	public void updatePassword(Group group, String password) {
		Group savedGroup = groupService.getGroupById(group.getId(), false);
		savedGroup.setPassword(password);
		groupService.update(savedGroup);
	}
}
