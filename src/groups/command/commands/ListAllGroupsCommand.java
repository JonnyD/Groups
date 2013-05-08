package groups.command.commands;

import java.util.Collection;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;

public class ListAllGroupsCommand extends PlayerCommand {

	public ListAllGroupsCommand() {
		super("List All Groups");
		setDescription("Lists all Groups");
		setUsage("/glistall");
		setArgumentRange(0,0);
		setIdentifier("glistall");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof CommandSender)
				|| (sender instanceof Player && !sender.isOp())) {
			sender.sendMessage("You don't have permission to perform this command");
			return true;
		}
		
		Collection<Group> groups = groupManager.getAllGroups();
		if(groups.size() == 0) {
			sender.sendMessage("No data");
			return true;
		}
		
		for(Group group : groups) {
			sender.sendMessage("Name: " + group.getName() + ", Members: " + group.getGroupMembers().size() + ", Personal: " + group.getPersonal());
		}
		
		return true;
	}

}
