package groups.manager;

import groups.model.Group;
import groups.model.Member;
import groups.model.Membership;
import groups.model.Membership.Role;
import groups.service.MemberService;
import groups.service.MembershipService;

public class MembershipManager {

	private MembershipService membershipService;
	private MemberService memberService;
	
	public MembershipManager(MembershipService membershipService, MemberService memberService) {
		this.membershipService = membershipService;
		this.memberService = memberService;
	}
	
    public Membership getOrCreateMembership(Group group, Member member, Role role) {
    	return this.membershipService.getOrCreateMembership(member, group, role);
    }
	
	public void deleteMembership(Membership membership) {
		membershipService.delete(membership);
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

	public Membership addMembership(Group group, String targetUsername, Role role) {
		Member member = memberService.getOrCreateMember(targetUsername);
		Membership membership = membershipService.createMembership(group, member, role);
		membershipService.save(membership);
		return membership;
	}
	
	public void removeMembership(Membership membership) {
		membershipService.delete(membership);
	}
	
	public void changeRole(Membership membership, Role role) {
		Membership savedMembership = membershipService.getMembershipById(membership.getId(), false);
		savedMembership.setRole(role);
		membershipService.update(savedMembership);
	}
}
