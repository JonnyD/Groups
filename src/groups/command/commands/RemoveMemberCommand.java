package groups.command.commands;

import java.util.List;

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

		List<Member> members = group.getMembers();
		
		String username = sender.getName();
		Member senderFound = null;
		for(Member member : members) {
			if(member.getPlayerName().equals(username)) {
				senderFound = member;
				break;
			}
		}
		
		Role senderRole = senderFound.getRole();
		if(senderFound == null || (senderRole != Role.ADMIN && senderRole != Role.MODERATOR)) {
			sender.sendMessage("You don't have permission to perform this command");
			return true;
		}

		String targetPlayer = args[1];
		Member targetFound = null;
		for(Member member : members) {
			if(member.getPlayerName().equals(targetPlayer)) {
				targetFound = member;
				break;
			}
		}
		
		if(targetFound == null) {
			sender.sendMessage(targetPlayer + " is not a member");
			return true;
		}
		
		groupManager.removeMember(group, targetFound);
		
		return true;
	}

}
