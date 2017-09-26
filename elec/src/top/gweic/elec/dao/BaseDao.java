package top.gweic.elec.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T> {

	void save(T entity);
	void update(T entity);
	T findObjectById(Serializable id);
	void delete(Serializable... ids);
	void deleteCollection(Collection<T> c);
	List<T> findCollectionByConditionNoPage(String condition,
			final Object[] params, Map<String, String> orderby);
	
	
	List<T> findByCriteria(DetachedCriteria criteria);
}
