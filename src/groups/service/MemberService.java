package groups.service;

import java.util.Set;

import groups.model.Member;
import groups.storage.DAO;

public class MemberService extends BaseService {

	public MemberService(DAO dao) {
		super(dao);
	}
	
	public Member getMemberByName(String name) {
		return this.dao.findMemberByName(name);
	}
	
	public Member createMember(String name) {
		return new Member(name);
	}
	
	public Member getOrCreateMember(String name) {
		Member member = this.getMemberByName(name);
		if (member == null) {
			member = this.createMember(name);
			this.save(member);
		}
		return member;
	}

	public Set<Member> getAllMembers() {
		return dao.findAllMembers();
	}
}
