package groups.listener;

import groups.Groups;
import groups.manager.GroupManager;
import groups.model.Group;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener implements Listener {
	
	GroupManager groupManager = Groups.getInstance().getGroupManager();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void login(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		String username = player.getName();
		String name = "personal_" + username;
		Group group = groupManager.getGroupByName(name);
		if(group == null) {
			groupManager.createPersonalGroup(name, username);
		}
	}
}
