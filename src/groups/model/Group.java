package groups.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groups_group")
public class Group {

	@Id
	@GeneratedValue
	@Column(name = "group_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", unique = true, nullable = false, length = 25)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "group")
	private List<Member> members = new ArrayList<Member>();
	
	@Column(name = "personal", nullable = false)
	private Boolean personal = false;
	
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

	public void addMember(Member member) {
		members.add(member);
	}
	
	public List<Member> getMembers() {
		return members;
	}
	
	public void setMembers(List<Member> members) {
		this.members = members;
	}
}
