package groups.command.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;
import groups.model.Member;

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

		GroupMember groupMember = group.getGroupMember(username);
		Role role = groupMember.getRole();
		boolean hasPermission = groupMember != null && (role == Role.ADMIN || role == Role.MODERATOR);
		if(!hasPermission) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		for(GroupMember gm : group.getGroupMembers().values()) {
			Member member = gm.getMember();
			sender.sendMessage(member.getName() + " " + gm.getRole());
		}
		
		return true;
	}

}
