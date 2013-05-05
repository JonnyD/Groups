package groups.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;

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
		groupManager.createGroup(name, sender.getName());
		return true;
	}

}
