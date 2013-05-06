package groups.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name = "groups_group")
public class Group {

	public enum Type {
		Include,
		Exclude
	}
	
	@Id
	@GeneratedValue
	@Column(name = "group_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", unique = true, nullable = false, length = 25)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "group")
	@MapKey(name = "playerName")
	private Map<String, Member> members = new HashMap<String, Member>();
	
	@Column(name = "personal", nullable = false)
	private Boolean personal = false;
	
	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "type", nullable = false, length = 2)
	private Type type = Type.Include;
	
	@Version
	@Column(name = "update_time", nullable = false)
    Timestamp updatetime;
	
	@CreatedTimestamp
	@Column(name = "create_time", nullable = false)
    Timestamp createTime;

	public Group() {}
	
	public Group(String name) {
		this.name = name;
	}
	
	public Group(Integer id, String name, Boolean personal) {
		this.id = id;
		this.name = name;
		this.personal = personal;
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
	
	public Boolean getPersonal() {
		return personal;
	}

	public void setPersonal(Boolean personal) {
		this.personal = personal;
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
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void addMember(Member member) {
		members.put(member.getPlayerName(), member);
	}
	
	public void removeMember(Member member) {
		members.remove(member);
	}

	public Map<String, Member> getMembers() {
		return members;
	}
	
	public void setMembers(Map<String, Member> members) {
		this.members = members;
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
		Group other = (Group) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", members=" + members
				+ ", personal=" + personal + ", type=" + type + ", createTime="
				+ createTime + "]";
	}
}
