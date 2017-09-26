package top.gweic.elec.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import top.gweic.elec.dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{
	private Class entityClass;
	//通过构造方方法获得子类设置的泛型信息
	public BaseDaoImpl() {
		//获得父类的参数化类型对象，即含有泛型信息
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得泛型数组,转换得到子类的class对象
		entityClass = (Class) type.getActualTypeArguments()[0];
	}

	//通过spring方法注入sessionFactory，调用父类的方法创建Hibernate模板对象
	@Resource
	public void setSessionFactoryI(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 插入
	 * @param entity
	 */
	
	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	/**
	 * 更新
	 * @param entity
	 */
	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */

	@Override
	public T findObjectById(Serializable id) {
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}
	
	

	/**
	 * 根据条件查询
	 */
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 根据id批量删除
	 */
	@Override
	public void delete(Serializable... ids) {
		if(ids!=null&&ids.length>0){
			for (Serializable id : ids) {
				T obj = findObjectById(id);
				this.getHibernateTemplate().delete(obj);
			}
		}
	}

	/**
	 * 根据集合批量删除
	 */
	@Override
	public void deleteCollection(Collection<T> c) {
		this.getHibernateTemplate().deleteAll(c);
	}

	/**
	 * 根据条件排序查询，无分页
	 */
	@Override
	public List<T> findCollectionByConditionNoPage(String condition, final Object[] params, Map<String, String> orderby) {
		String hql="select o from "+entityClass.getSimpleName()+" o where 1=1 ";
		String orderbyhql = this.orderby(orderby);
		final String finalHql = hql + condition + orderbyhql;
		//方式一
		this.getHibernateTemplate().setCacheQueries(true);
		List list = this.getHibernateTemplate().find(finalHql,params);
		//方式二，使用hibernate模板提供的回调函数，回调Session
	/*	 List<T> list =  this.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				int i=0;
				for (Object object : params) {
					query.setParameter(i++, object);
				}
				return  query.list();
			}
		});*/
		
		return list;
	}

	//解析map集合，获取排序的语句，ORDER BY o.textDate ASC,o.textRemark DESC
	private String orderby(Map<String, String> orderby) {
		StringBuffer buffer = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			buffer.append(" order by ");
			for(Map.Entry<String, String> map:orderby.entrySet()){
				buffer.append(map.getKey()+" "+map.getValue()+",");
			}
			//删除最后一个逗号
			buffer.deleteCharAt(buffer.length()-1);
		}
		return buffer.toString();
	}
	
	
	

}
