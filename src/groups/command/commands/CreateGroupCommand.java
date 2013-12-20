package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateGroupCommand extends PlayerCommand {

	public CreateGroupCommand() {
		super("Create Group");
		setDescription("Creates a group");
		setUsage("/gcreate <name>");
		setArgumentRange(1,1);
		setIdentifier("gcreate");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		String name = args[0];
		
		boolean validName = groupManager.isGroupNameValid(name);
		if (!validName) {
			sender.sendMessage("Invalid group name length");
		}
		
		Group group = groupManager.getGroupByName(name);
		if(group != null) {
			sender.sendMessage("Group already exists");
			return true;
		}
		
		groupManager.createGroup(name, sender.getName());
		return true;
	}

}
