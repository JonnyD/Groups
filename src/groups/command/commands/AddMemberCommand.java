package groups.command.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;

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
		GroupMember groupMember = group.getGroupMember(username);
		if(groupMember != null) {
			sender.sendMessage(username + " is already a member");
			return true;
		}
		
		String roleName = args[2];
		Role role = null;
		for(Role r : Role.values()) {
			if(r.toString().equalsIgnoreCase(roleName)) {
				role = Role.valueOf(roleName.toUpperCase());
				break;
			}
		}
		
		if(role == null) {
			sender.sendMessage("Role " + roleName + " doesn't exist");
			return true;
		}
		
		groupManager.addMemberToGroup(group, username, role);
		
		return true;
	}

}
