package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.GroupMember;
import groups.model.Member;
import groups.model.Membership;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		for(Membership membership : member.getMemberships().values()) {
			Group group = membership.getGroup();
			String message = group.getName() + " " + membership.getRole();
			if(group.getPersonal()) {
				message += " " + " (Personal Group)";
			}
			sender.sendMessage(message);
		}
		
		return true;
	}

}
