package groups.command.commands;

import java.util.Collection;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Member;

public class ListAllMembersCommand extends PlayerCommand {

	public ListAllMembersCommand() {
		super("List All Members");
		setDescription("Lists all Members");
		setUsage("/glistallmembers");
		setArgumentRange(0,0);
		setIdentifier("glistallmembers");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof CommandSender)
				|| (sender instanceof Player && !sender.isOp())) {
			sender.sendMessage("You don't have permission to perform this command");
		}
		
		Collection<Member> members = groupManager.getAllMembers();
		if(members.size() == 0) {
			sender.sendMessage("No data");
			return true;
		}
		
		for(Member member : members) {
			sender.sendMessage(member.toString());
		}
		
		return true;
	}

}
