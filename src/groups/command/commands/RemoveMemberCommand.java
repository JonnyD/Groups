package groups.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;

public class RemoveMemberCommand extends PlayerCommand {

	public RemoveMemberCommand() {
		super("Remove Member");
		setDescription("Removes Member from Group");
		setUsage("/gremove <group> <player>");
		setArgumentRange(2,2);
		setIdentifier("gremove");
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
			sender.sendMessage("Can't remove a member from a Personal Group");
			return true;
		}
		
		String username = sender.getName();
		GroupMember senderMember = group.getGroupMember(username);		
		Role senderRole = senderMember.getRole();
		boolean hasPermission = senderMember != null && (senderRole == Role.ADMIN || senderRole == Role.MODERATOR);
		if(!hasPermission) {
			sender.sendMessage("You don't have permission to perform this command");
			return true;
		}

		String targetPlayer = args[1];
		GroupMember targetMember = group.getGroupMember(targetPlayer);		
		if(targetMember == null) {
			sender.sendMessage(targetPlayer + " is not a member");
			return true;
		}
		
		if(targetPlayer.equalsIgnoreCase(username)) {
			sender.sendMessage("You can't remove yourself. Use the Leave Command,");
			return true;
		}
		
		Role targetRole = targetMember.getRole();
		if(senderRole == Role.MODERATOR && 
				(targetRole == Role.MODERATOR || targetRole == Role.ADMIN)) {
			sender.sendMessage("You don't have permission to remove this member");
			return true;
		}
		
		groupManager.removeMemberFromGroup(group, targetMember);
		return true;
	}

}
