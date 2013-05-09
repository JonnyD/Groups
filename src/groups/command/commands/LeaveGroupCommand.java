package groups.command.commands;

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
		
		GroupMember groupMember = group.getGroupMember(username);
		if(groupMember == null || groupMember.getRole() == Role.BANNED) {
			sender.sendMessage("You are not a member of this group");
			return true;
		}
		
		group.removeGroupMemmber(groupMember);
		return true;
	}

}
