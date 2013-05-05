package groups.command.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Member;
import groups.model.Member.Role;

public class AddMemberCommand extends PlayerCommand {

	public AddMemberCommand() {
		super("Add Member");
		setDescription("Adds a member to group");
		setUsage("/gadd <group> <player> <type>");
		setArgumentRange(3,3);
		setIdentifier("gadd");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		String groupName = args[0];
		Group group = groupManager.getGroupByName(groupName);
		
		if(group == null) {
			sender.sendMessage("Group doesn't exist");
			return true;
		}
		
		if(group.getPersonal()) {
			sender.sendMessage("Can't add a member to a Personal Group");
			return true;
		}

		String username = args[1];
		Member found = null;
		List<Member> members = group.getMembers();
		for(Member member : members) {
			if(member.getPlayerName().equals(username)) {
				found = member;
				break;
			}
		}
		
		if(found != null) {
			sender.sendMessage(username + " is already a member");
			return true;
		}
		
		String roleName = args[2];
		Role role = null;
		for(Role r : Role.values()) {
			System.out.println(r.toString());
			if(r.toString().equalsIgnoreCase(roleName)) {
				role = Role.valueOf(roleName.toUpperCase());
				break;
			}
		}
		
		if(role == null) {
			sender.sendMessage("Role " + roleName + " doesn't exist");
			return true;
		}
		
		groupManager.addMember(group, username, role);
		
		return true;
	}

}
