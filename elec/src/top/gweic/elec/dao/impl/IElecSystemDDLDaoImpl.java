package top.gweic.elec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import top.gweic.elec.dao.IElecSystemDDLDao;
import top.gweic.elec.domain.ElecSystemDDL;
@Repository
public class IElecSystemDDLDaoImpl extends BaseDaoImpl<ElecSystemDDL> implements IElecSystemDDLDao {

	@Override
	public List<ElecSystemDDL> findDistinctKeywod() {
		String hql="SELECT DISTINCT keyword FROM ElecSystemDDL ";
		this.getHibernateTemplate().setCacheQueries(true);
		List list = this.getHibernateTemplate().find(hql);
		List<ElecSystemDDL> arrayList=new ArrayList<ElecSystemDDL>();
		if (list != null && list.size() > 0) {

			for (Object object : list) {
				String keyword = object.toString();
				ElecSystemDDL e = new ElecSystemDDL();
				e.setKeyword(keyword);
				arrayList.add(e);
			}
		}
		return arrayList;
	}

	//通过数据类型，和数据编码，查询数据项名
	@Override
	public String findDdlNameByKeywordAndDdlCode(String keyword, String ddlCode) {
		String hql="select o.ddlName from ElecSystemDDL o where o.keyword=? and o.ddlCode=? ";
		this.getHibernateTemplate().setCacheQueries(true);
		List list = this.getHibernateTemplate().find(hql,keyword,Integer.parseInt(ddlCode));
		String ddlName="";
		if(list!=null&&list.size()>0){
			Object o = list.get(0);
			ddlName=o.toString();
		}
		return ddlName;
	}

}
