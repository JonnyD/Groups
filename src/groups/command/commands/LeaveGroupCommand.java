package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Membership;

import java.util.Collection;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveGroupCommand extends PlayerCommand {

	public LeaveGroupCommand() {
		super("Leave Group");
		setDescription("Leave a group");
		setUsage("/gleave <group>");
		setArgumentRange(1,1);
		setIdentifier("gleave");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		String username = sender.getName();
		String name = args[0];
		
		Group group = groupManager.getGroupByName(name);
		if(group == null) {
			sender.sendMessage("Group doesn't exist");
			return true;
		}
		
		if(group.getPersonal()) {
			sender.sendMessage("You can't leave a Personal Group");
			return true;
		}
		
		Membership senderMembership = group.getMembership(username);
		if(senderMembership == null || senderMembership.isAdmin()) {
			sender.sendMessage("You are not a member of this group");
			return true;
		}
		
		Collection<Membership> memberships = group.getMemberships();
		if(senderMembership.isAdmin()) {
			int countAdmins = 0;
			
			for(Membership membership : memberships) {
				if(!membership.equals(senderMembership) && membership.isAdmin()) {
					countAdmins++;
				}
			}
			
			if(countAdmins < 2) {
				sender.sendMessage("You are the last admin so you can't leave just yet. " +
						"Either delete the group entirely or assign someone else as an admin.");
				return true;
			}
		}
		
		group.removeMembership(senderMembership);
		return true;
	}

}
