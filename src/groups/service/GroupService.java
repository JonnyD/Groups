package groups.service;

import java.util.Set;

import groups.model.Group;
import groups.storage.DAO;

public class GroupService extends BaseService {
	
	public GroupService(DAO dao) {
		super(dao);
	}
	
	public Set<Group> getAllGroups() {
		return dao.findAllGroups();
	}
	
	
	public Group addGroup(Group group) {
		String groupName = group.getNormalizedName();
		
        if(isGroup(groupName)) {
            return null;
        }
        
		save(group);
        return group;
	}
	
	public void removeGroup(Group group) {
		String groupName = group.getNormalizedName();
		
        if(!isGroup(groupName)) {
            return;
        }
        
		delete(group);
	}
    
    public boolean isGroup(String groupName) {
        return getGroupByName(groupName) != null;
    }
    
    public Group getGroupByName(String groupName) {
    	groupName = groupName.toLowerCase();
        Group group = dao.findGroupByName(groupName);
        
        if(group == null) {
        	group = dao.findGroupByName(groupName);
        	
        	if(group == null) {
        		return null;
        	}
        }
        
        return group;
    }
    
    public Group getGroupById(int id, boolean immutable) {
    	return this.dao.findGroupById(id, immutable);
    }
}
