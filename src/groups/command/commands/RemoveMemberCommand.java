package groups.command.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Member;
import groups.model.Member.Role;

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

		Map<String, Member> members = group.getMembers();
		
		String username = sender.getName();
		Member senderFound = members.get(username);
		
		Role senderRole = senderFound.getRole();
		if(senderFound == null || (senderRole != Role.ADMIN && senderRole != Role.MODERATOR)) {
			sender.sendMessage("You don't have permission to perform this command");
			return true;
		}

		String targetPlayer = args[1];
		Member foundTarget = members.get(targetPlayer);
		
		if(foundTarget == null) {
			sender.sendMessage(targetPlayer + " is not a member");
			return true;
		}
		
		groupManager.removeMember(group, foundTarget);
		
		return true;
	}

}
