package top.gweic.elec.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.gweic.elec.dao.IElecSystemDDLDao;
import top.gweic.elec.domain.ElecSystemDDL;
import top.gweic.elec.service.IElecSystemDDLService;
@Service
@Transactional
public class IElecSystemDDLServiceImpl implements IElecSystemDDLService {

	@Autowired
	private IElecSystemDDLDao elecSystemDDLDao;
	
	/**  
	* @Name: findDistinctKeywod
	* @Description: 获取去掉重复值的数据类型
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-18（创建日期）
	* @Parameters: 无
	* @Return: List<ElecSystemDDL>：封装数据类型的集合
	*/
	@Override
	public List findDistinctKeywod() {
		List<ElecSystemDDL> list = elecSystemDDLDao.findDistinctKeywod();
		return list;
	}


	/**  
	* @return 
	 * @Name: findElecSystemDDLListByKeyword
	* @Description: 获取数据类型对应的数据项
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-18（创建日期）
	* @Parameters: keyword
	* @Return: List<ElecSystemDDL>：封装数据类型的集合
	*/
	@Override
	public List<ElecSystemDDL> findElecSystemDDLListByKeyword(String keyword) {
		
		String condition="";
		List<String> paramList=new ArrayList<String>();
		if(StringUtils.isNotBlank(keyword)){
			condition="and o.keyword=?";
			paramList.add(keyword);
		}
		Object[] params=paramList.toArray();
		Map<String, String> orderby=new HashMap<>();
		orderby.put("o.ddlCode", "asc");
		List<ElecSystemDDL> list = elecSystemDDLDao.findCollectionByConditionNoPage(condition, params, orderby);
		return list;
	}


	/**
	 * 保存数据字典信息
	 */
	@Override
	public void saveElecSystemDDL(ElecSystemDDL model) {
		//判断信息是否是新的
		String typeflag = model.getTypeflag();
		//数据类型名字
		String keywordname = model.getKeywordname();
		//所有数据项
		String[] itemname = model.getItemname();
		if(typeflag.equals("new")){
			//新数据插入
			if(itemname!=null&&itemname.length>0){
				int i=1;
				for (String ddlName : itemname) {
					ElecSystemDDL ddl=new ElecSystemDDL();
					ddl.setDdlCode(i++);
					ddl.setKeyword(keywordname);
					ddl.setDdlName(ddlName);
					elecSystemDDLDao.save(ddl);
					
				}
				
			}
		}else{
			//已有数据类型，执行更新
			//删除数据类型对应的原有数据
			List<ElecSystemDDL> listByKeyword = findElecSystemDDLListByKeyword(keywordname);
			elecSystemDDLDao.deleteCollection(listByKeyword);
			//重新插入
			if(itemname!=null&&itemname.length>0){
				int i=1;
				for (String ddlName : itemname) {
					ElecSystemDDL ddl=new ElecSystemDDL();
					ddl.setDdlCode(i++);
					ddl.setKeyword(keywordname);
					ddl.setDdlName(ddlName);
					elecSystemDDLDao.save(ddl);
					
				}
				
			}
			
		}
	}


	public String findDdlNameByKeywordAndDdlCode(String keyword, String ddlCode) {
		String ddlName = elecSystemDDLDao.findDdlNameByKeywordAndDdlCode(keyword, ddlCode);
		return ddlName;
	}
}
