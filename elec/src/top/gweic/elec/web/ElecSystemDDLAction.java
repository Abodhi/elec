package top.gweic.elec.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import top.gweic.elec.domain.ElecSystemDDL;
import top.gweic.elec.service.IElecSystemDDLService;
@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class ElecSystemDDLAction extends BaseAction<ElecSystemDDL> {

	@Resource
	private IElecSystemDDLService ddlService;
	
	/**  
	* @Name: home
	* @Description: 数据字典的首页显示
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-18（创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/dictionaryIndex.jsp
	*/
	public String home(){
		
		//查询数据字典中的数据类型
		List<ElecSystemDDL> list = ddlService.findDistinctKeywod();
		request.setAttribute("list", list);
		return "home";
	}
	

	/**  
	* @Name: edit
	* @Description: 跳转到数据字典编辑页面
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-18（创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/dictionaryEdit.jsp
	*/
	public String edit(){
		//根据数据类型查询对应的数据项
		String keyword = this.getModel().getKeyword();
		List<ElecSystemDDL> list = ddlService.findElecSystemDDLListByKeyword(keyword);
		request.setAttribute("list", list);
		return "edit";
	}
	
	/**
	 * 保存数据字典
	 * 重定向到数据字典主页面
	 */
	
	public String save(){
		ddlService.saveElecSystemDDL(this.getModel());
		return "save";
	}
}
