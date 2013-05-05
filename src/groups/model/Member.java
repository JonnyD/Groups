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
import javax.persistence.UniqueConstraint;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name = "groups_member", uniqueConstraints={
	@UniqueConstraint(columnNames={"group_id", "player_name"})})
public class Member {

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
	@JoinColumn(name = "group_id")
	private Group group;
	
	@Column(name = "player_name", nullable = false, length = 16)
	private String playerName;
	
	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "role", nullable = false, length = 10)
	private Role role = Role.MEMBER;
	
	@CreatedTimestamp
	@Column(name = "create_time", nullable = false)
    Timestamp createTime;
	
	public Member() {}
	
	public Member(Group group, String playerName) {
		this.group = group;
		this.playerName = playerName;
	}
	
	public Member(Integer id, Group group, String playerName, Role role) {
		this.id = id;
		this.group = group;
		this.playerName = playerName;
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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
