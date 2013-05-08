package groups.command.commands;

import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;

public class SetPasswordCommand extends PlayerCommand {

	public SetPasswordCommand() {
		super("Set Password");
		setDescription("Set the password of a group");
		setUsage("/gsetpassword <group> <password>");
		setArgumentRange(1,1);
		setIdentifier("gsetpassword");
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
			sender.sendMessage("Can't modify a Personal Group");
			return true;
		}

		GroupMember groupMember = group.getGroupMember(username);		
		if(groupMember == null || groupMember.getRole() != Role.ADMIN) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		String password = args[1];
		group.setPassword(password);
		
		return true;
	}

}
