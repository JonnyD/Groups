package groups.command.commands;

import java.util.List;

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

		Member found = null;
		List<Member> members = group.getMembers();
		for(Member member : members) {
			if(member.getPlayerName().equals(username)) {
				found = member;
				break;
			}
		}
		
		if(found == null || (found.getRole() != Role.ADMIN && found.getRole() != Role.MODERATOR)) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		for(Member member : members) {
			sender.sendMessage(member.getPlayerName() + " " + member.getRole());
		}
		
		return true;
	}

}
