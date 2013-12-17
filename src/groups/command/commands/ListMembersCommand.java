package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;
import groups.model.Member;
import groups.model.Membership;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListMembersCommand extends PlayerCommand {

	public ListMembersCommand() {
		super("List Members");
		setDescription("Lists Members of a Group");
		setUsage("/glistmembers <group>");
		setArgumentRange(1,1);
		setIdentifier("glistmembers");
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

		Membership senderMembership = group.getMembership(username);
		boolean hasPermission = senderMembership != null 
				&& (senderMembership.isAdmin() 
						|| senderMembership.isModerator()
						|| senderMembership.isMember());
		if(!hasPermission) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		for(Membership membership : group.getMemberships().values()) {
			Member member = membership.getMember();
			sender.sendMessage(member.getName() + " " + membership.getRole());
		}
		
		return true;
	}

}
