package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Membership;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		Membership senderMembership = group.getMembership(username);	
		boolean hasPermission = senderMembership != null && (senderMembership.isAdmin() || senderMembership.isModerator());
		if(!hasPermission) {
			sender.sendMessage("You don't have permission to perform this command");
			return true;
		}

		String targetPlayer = args[1];
		Membership targetMembership = group.getMembership(targetPlayer);		
		if(targetMembership == null) {
			sender.sendMessage(targetPlayer + " is not a member");
			return true;
		}
		
		if(targetPlayer.equalsIgnoreCase(username)) {
			sender.sendMessage("You can't remove yourself. Use the Leave Command,");
			return true;
		}
		
		if(senderMembership.isModerator() && 
				(targetMembership.isModerator() || targetMembership.isAdmin())) {
			sender.sendMessage("You don't have permission to remove this member");
			return true;
		}
		
		membershipManager.removeMembership(targetMembership);
		return true;
	}

}
