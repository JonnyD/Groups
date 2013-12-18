package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Member;
import groups.model.Membership;
import groups.model.Membership.Role;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		
		int minNameLength = 3;
		int maxNameLength = 16;
		boolean greaterThanEqualMin = groupName.length() >= minNameLength;
		boolean lessThanEqualMax = groupName.length() <= maxNameLength;
		if(!greaterThanEqualMin || !lessThanEqualMax) {
			sender.sendMessage("Username can't be less than " + minNameLength +
					" characters or greater than " + maxNameLength + 
					" characters");
			return true;
		}
		
		String senderUsername = sender.getName();
		Membership senderMembership = group.getMembership(senderUsername);	
		boolean hasPermission = senderMembership != null 
				&& (senderMembership.isAdmin() || senderMembership.isModerator());
		if(!hasPermission) {
			sender.sendMessage("You don't have permission to perform this command");
			return true;
		}
		
		if(group.getPersonal()) {
			sender.sendMessage("Can't add a member to a Personal Group");
			return true;
		}

		String targetUsername = args[1];
		Membership targetMembership = group.getMembership(targetUsername);
		if(targetMembership != null) {
			sender.sendMessage(targetUsername + " is already a member");
			return true;
		}
		
		String roleName = args[2];
		Role role = groupManager.getRoleByName(roleName);		
		if(role == null) {
			sender.sendMessage("Role " + roleName + " doesn't exist");
			return true;
		}
		
		groupManager.addMemberToGroup(group, targetUsername, role);
		return true;
	}

}
