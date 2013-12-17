package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.Membership;

import org.bukkit.command.CommandSender;

public class GroupInfoCommand extends PlayerCommand {

	public GroupInfoCommand() {
		super("Group Info");
		setDescription("Displays info of a group");
		setUsage("/ginfo <group>");
		setArgumentRange(1,1);
		setIdentifier("ginfo");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		String username = sender.getName();
		String name = args[0];
		
		Group group = groupManager.getGroupByName(name);
		if(group == null) {
			sender.sendMessage("Group doesn't exist");
			return true;
		}

		Membership senderMembership = group.getMembership(username);
		if(senderMembership == null) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		sender.sendMessage(group.getName());
		return true;
	}

}
