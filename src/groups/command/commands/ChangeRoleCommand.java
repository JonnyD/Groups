package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeRoleCommand extends PlayerCommand {

	public ChangeRoleCommand() {
		super("Change Role");
		setDescription("Change the role of a member");
		setUsage("/gchangerole <group> <member>");
		setArgumentRange(2,2);
		setIdentifier("gchangerole");
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
		
		if(group.isPersonal()) {
			sender.sendMessage("Can't modify a Personal Group");
			return true;
		}

		String targetUsername = args[1];
		GroupMember groupMember = group.getGroupMember(targetUsername);
		if(groupMember == null) {
			sender.sendMessage(targetUsername + " is not a member");
			return true;
		}
		
		String senderUsername = sender.getName();
		GroupMember senderMember = group.getGroupMember(senderUsername);		
		Role senderRole = senderMember.getRole();
		boolean hasPermission = senderMember != null && senderRole == Role.ADMIN;
		if(!hasPermission) {
			sender.sendMessage("You don't have permission to perform this command");
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
		
		Role currentRole = groupMember.getRole();
		if(role == currentRole) {
			sender.sendMessage(targetUsername + " is already a " + currentRole);
			return true;
		}
		
		groupMember.setRole(role);
		return true;
	}

}
