package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		
		if(group.isPersonal()) {
			sender.sendMessage("Can't delete a Personal Group");
			return true;
		}

		GroupMember groupMember = group.getGroupMember(username);		
		if(groupMember == null || groupMember.getRole() != Role.ADMIN) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		groupManager.removeGroup(group);
		
		return true;
	}

}
