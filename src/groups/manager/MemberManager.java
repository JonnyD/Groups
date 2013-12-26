package groups.manager;

import groups.model.Member;
import groups.service.MemberService;

import java.util.Set;

public class MemberManager {
	
	private MemberService memberService;
	
	public MemberManager(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public Member getMemberByName(String memberName) {
		return memberService.getMemberByName(memberName);
	}

	public Set<Member> getAllMembers() {
		return memberService.getAllMembers();
	}
	
	public Member getOrCreateMember(String name) {
		return memberService.getOrCreateMember(name);
	}
	
	
}
