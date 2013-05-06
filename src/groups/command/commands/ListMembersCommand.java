package groups.command.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Member;
import groups.model.Member.Role;

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

		Map<String, Member> members = group.getMembers();
		Member foundMember = members.get(username);
		
		boolean hasPermission = foundMember.getRole() == Role.ADMIN || foundMember.getRole() == Role.MODERATOR;
		if(foundMember == null || !hasPermission) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		for(Member member : members.values()) {
			sender.sendMessage(member.getPlayerName() + " " + member.getRole());
		}
		
		return true;
	}

}
