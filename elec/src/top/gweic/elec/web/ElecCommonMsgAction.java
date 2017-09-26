package top.gweic.elec.web;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import top.gweic.elec.domain.ElecCommonMsg;
import top.gweic.elec.service.IElecCommonMsgService;
import top.gweic.elec.utils.ValueStackUtils;
@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class ElecCommonMsgAction extends BaseAction<ElecCommonMsg> {

	@Resource
	private IElecCommonMsgService commonMsgService;
	
	/**  
	* @Name: home
	* @Description: 运行监控的首页显示
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-17（创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/actingIndex.jsp
	*/
	public String home(){
		//查询运行监控信息
		ElecCommonMsg commonMsg = commonMsgService.findElecCommonMsg();
		ValueStackUtils.push(commonMsg);
		return "home";
	}
	
	/**  
	* @Name: save
	* @Description: 保存运行监控
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-17（创建日期）
	* @Parameters: 无
	* @Return: 重定向到system/actingIndex.jsp
	*/
	public String save(){
		//保存监控信息，存在就更新，第一次就插入
		commonMsgService.saveElecCommonMsg(this.getModel());
		return "save";
	}
	
}
