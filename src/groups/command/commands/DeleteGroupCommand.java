package groups.command.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Member;
import groups.model.Member.Role;

public class DeleteGroupCommand extends PlayerCommand {

	public DeleteGroupCommand() {
		super("Delete Group");
		setDescription("Deletes a group");
		setUsage("/gdelete <group>");
		setArgumentRange(1,1);
		setIdentifier("gdelete");
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
			sender.sendMessage("Can't delete a Personal Group");
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
		
		if(found == null || found.getRole() != Role.ADMIN) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		groupManager.removeGroup(group);
		
		return true;
	}

}
