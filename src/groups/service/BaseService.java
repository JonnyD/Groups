package groups.service;

import groups.storage.DAO;

public class BaseService {

	protected DAO dao;
	
	public BaseService(DAO dao) {
		this.dao = dao;
	}
	
	public void save(Object object) {
		this.dao.save(object);
	}
	
	public void update(Object object) {
		this.dao.update(object);
	}
	
	public void delete(Object object) {
		this.dao.delete(object);
	}
}
