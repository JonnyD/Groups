package groups.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name = "groups_member")
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", unique = true, nullable = false, length = 16)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "member")
    @MapKey(name = "groupName")
    private Map<String, Membership> membershipMap = new HashMap<String, Membership>();

	@Transient
	private Group personalGroup;

	@Version
	@Column(name = "update_time", nullable = false)
	Timestamp updatetime;

	@CreatedTimestamp
	@Column(name = "create_time", nullable = false)
	Timestamp createTime;

	public Member() {
	}

	public Member(String username) {
		this.name = username;
	}

	public Member(Integer id, String username) {
		this.id = id;
		this.name = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNormalizedName() {
		return name.toLowerCase();
	}

	public void addMembership(Membership membership) {
		membershipMap.put(membership.getGroupName(), membership);
	}

	public void removeMembership(Membership membership) {
		membershipMap.remove(membership);
	}

	public Map<String, Membership> getMembershipMap() {
		return membershipMap;
	}

	public void setMembershipMap(Map<String, Membership> memberships) {
		this.membershipMap = memberships;
	}
	
	public Membership getMembership(String memberName) {
		return membershipMap.get(memberName);
	}
	
	public Collection<Membership> getMemberships() {
		return this.membershipMap.values();
	}

	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		for (Membership membership : getMemberships()) {
			groups.add(membership.getGroup());
		}
		return groups;
	}
	
	public Group getGroup(String groupName) {
		Membership membership = membershipMap.get(groupName);
		return membership.getGroup();
	}

	public Group getPersonalGroup() {
		if (personalGroup == null) {
			for (Membership m : membershipMap.values()) {
				Group group = m.getGroup();
				if (group.getPersonal()) {
					personalGroup = group;
					break;
				}
			}
		}
		return personalGroup;
	}

	public void setPersonalGroup(Group personalGroup) {
		this.personalGroup = personalGroup;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Member other = (Member) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", memberships="
				+ membershipMap + ", personalGroup=" + personalGroup
				+ ", updatetime=" + updatetime + ", createTime=" + createTime
				+ "]";
	}
}
