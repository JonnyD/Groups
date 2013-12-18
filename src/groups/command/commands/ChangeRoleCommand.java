package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;
import groups.model.Membership;

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
		
		if(group.getPersonal()) {
			sender.sendMessage("Can't modify a Personal Group");
			return true;
		}

		String targetUsername = args[1];
		Membership targetMembership = group.getMembership(targetUsername);
		if(targetMembership == null) {
			sender.sendMessage(targetUsername + " is not a member");
			return true;
		}
		
		String senderUsername = sender.getName();
		Membership senderMembership = group.getMembership(senderUsername);
		boolean hasPermission = senderMembership != null && senderMembership.isAdmin();
		if(!hasPermission) {
			sender.sendMessage("You don't have permission to perform this command");
			return true;
		}
		
		String roleName = args[2];
		Role role = groupManager.getRoleByName(roleName);	
		if(role == null) {
			sender.sendMessage("Role " + roleName + " doesn't exist");
			return true;
		}
		
		Role currentRole = targetMembership.getRole();
		if(role == currentRole) {
			sender.sendMessage(targetUsername + " is already a " + currentRole);
			return true;
		}
		
		groupMember.setRole(role);
		return true;
	}

}
