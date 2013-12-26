package groups.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CacheStrategy;
import com.avaje.ebean.annotation.CreatedTimestamp;

@CacheStrategy(readOnly = true)
@Entity
@Table(name = "groups_membership")
public class Membership {

	public enum Role {
		ADMIN,
		MODERATOR,
		MEMBER,
		BANNED
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "role", nullable = false, length = 2)
	private Role role = Role.MEMBER;
	
	@Transient
	private String memberName;
	
	@Transient
	private String groupName;
	
	@Version
	@Column(name = "update_time", nullable = false)
    Timestamp updatetime;
	
	@CreatedTimestamp
	@Column(name = "create_time", nullable = false)
    Timestamp createTime;
	
	public Membership() {}

	public Membership(Group group, Member member, Role role) {
		this.group = group;
		this.member = member;
		this.role = role;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public String getMemberName() {
		return member.getName();
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	public String getGroupName() {
		return group.getName();
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean isAdmin() {
		return this.role == Role.ADMIN;
	}
	
	public boolean isModerator() {
		return this.role == Role.MODERATOR;
	}
	
	public boolean isMember() {
		return this.role == Role.MEMBER;
	}
	
	public boolean isBanned() {
		return this.role == Role.BANNED;
	}
	
	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membership other = (Membership) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		
		return true;
	}
	
	
}
