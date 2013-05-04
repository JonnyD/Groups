package groups.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "groups_member", uniqueConstraints={
	@UniqueConstraint(columnNames={"group_id", "player_name"})})
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
	
	@Column(name = "player_name", nullable = false, length = 16)
	private String playerName;
	
	@Column(name = "role", nullable = false, length = 10)
	private String role = "member";
	
	@Column(name = "personal", nullable = false)
	private Boolean personal = false;
	
	public Member() {}
	
	public Member(Group group, String playerName) {
		this.group = group;
		this.playerName = playerName;
	}
	
	public Member(Integer id, Group group, String playerName, String role, Boolean personal) {
		this.id = id;
		this.group = group;
		this.playerName = playerName;
		this.role = role;
		this.personal = personal;
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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Boolean isPersonal() {
		return personal;
	}
	
	public void setPersonal(Boolean personal) {
		this.personal = personal;
	}
}
