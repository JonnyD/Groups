package groups.service;

import groups.model.Group;
import groups.model.Member;
import groups.model.Membership;
import groups.model.Membership.Role;
import groups.storage.DAO;

public class MembershipService extends BaseService {

	public MembershipService(DAO dao) {
		super(dao);
	}
	
	public Membership createMembership(Group group, Member member, Role role) {
		Membership membership = new Membership();
    	membership.setMember(member);
    	membership.setGroup(group);
    	membership.setRole(role);
    	
    	return membership;
	}
	
	public Membership getMembershipByMemberAndGroup(Member member, Group group) {
		return this.dao.findMembershipByMemberAndGroup(member, group);
	}

	public Membership getOrCreateMembership(Member member, Group group, Role role) {
		Membership membership = this.getMembershipByMemberAndGroup(member, group);
    	
    	if (membership == null) {
    		membership = this.createMembership(group, member, role);
    	}
        
        return membership;
	}
	
	public Membership getMembershipById(int id, boolean immutable) {
		return this.dao.findMembershipById(id, immutable);
	}
}
