package groups.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.Member;

public class ListGroupsCommand extends PlayerCommand {

	public ListGroupsCommand() {
		super("List Groups");
		setDescription("Lists all of your groups");
		setUsage("/glistgroups");
		setArgumentRange(0,0);
		setIdentifier("glistgroups");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		String username = sender.getName();
		Member member = groupManager.getMember(username);
		for(GroupMember gm : member.getGroupMembers().values()) {
			Group group = gm.getGroup();
			String message = group.getName() + " " + gm.getRole();
			if(group.isPersonal()) {
				message += " " + " (Personal Group)";
			}
			sender.sendMessage(message);
		}
		
		return true;
	}

}
