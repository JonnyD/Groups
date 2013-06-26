package groups.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

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
	private Map<String, GroupMember> groupMembers = new HashMap<String, GroupMember>();
	
	@Transient
	private Group personalGroup;
	
	@Version
	@Column(name = "update_time", nullable = false)
    Timestamp updatetime;
	
	@CreatedTimestamp
	@Column(name = "create_time", nullable = false)
    Timestamp createTime;
	
	public Member() {}
	
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
	
	public void addGroupMember(GroupMember groupMember) {
		groupMembers.put(groupMember.getGroupName(), groupMember);
	}
	
	public void removeGroupMember(GroupMember groupMember) {
		groupMembers.remove(groupMember);
	}

	public Map<String, GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(Map<String, GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}
	
	public Group getPersonalGroup() {
		if(personalGroup == null) {
			for(GroupMember gm : groupMembers.values()) {
				Group group = gm.getGroup();
				if(group.isPersonal()) {
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
	
	
}
