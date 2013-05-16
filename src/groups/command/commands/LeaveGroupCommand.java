package groups.command.commands;

import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;

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
		
		if(group.isPersonal()) {
			sender.sendMessage("You can't leave a Personal Group");
			return true;
		}
		
		GroupMember groupMember = group.getGroupMember(username);
		if(groupMember == null || groupMember.getRole() == Role.BANNED) {
			sender.sendMessage("You are not a member of this group");
			return true;
		}
		
		Collection<GroupMember> groupMembers = group.getGroupMembers().values();
		if(groupMember.getRole() == Role.ADMIN) {
			int countAdmins = 0;
			
			for(GroupMember gm : groupMembers) {
				if(gm.getRole() == Role.ADMIN) {
					countAdmins++;
				}
			}
			
			if(countAdmins < 2) {
				sender.sendMessage("You are the last admin so you can't leave just yet. " +
						"Either delete the group entirely or assign someone else as an admin.");
				return true;
			}
		}
		
		group.removeGroupMemmber(groupMember);
		return true;
	}

}
