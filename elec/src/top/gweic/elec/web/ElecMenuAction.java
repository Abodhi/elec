package top.gweic.elec.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import top.gweic.elec.domain.ElecCommonMsg;
import top.gweic.elec.service.IElecCommonMsgService;
import top.gweic.elec.utils.ValueStackUtils;

@Controller
@Scope("prototype")
@SuppressWarnings(value="serial")
public class ElecMenuAction extends BaseAction<MenuForm> {


	@Resource
	private IElecCommonMsgService commonMsgService;
	
	
	/**系统登录*/
	public String menuHome(){
		return "menuHome";
	}

	/**系统首页标题*/
	public String title(){
		return "title";
	}
	
	/**系统首页左侧菜单*/
	public String left(){
		return "left";
	}
	
	/**系统首页显示框架改变*/
	public String change(){
		return "change";
	}
	
	/**系统首页功能区域显示*/
	public String loading(){
		return "loading";
	}
	
	/**重新登录*/
	public String logout(){
		//清空Session
		//指定某个Session清空
//		request.getSession().removeAttribute(arg0);
		//清空所有的Session
		request.getSession().invalidate();
		return "logout";
	}
	

	/**  
	* @Name: alermStation
	* @Description: 显示站点运行情况
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-17（创建日期）
	* @Parameters: 无
	* @Return: 跳转到menu/alermStation.jsp
	*/
	public String alermStation(){
		//1：查询运行监控表，获取运行监控表中的数据，返回ElecCommonMsg对象
		ElecCommonMsg commonMsg = commonMsgService.findElecCommonMsg();
		//2：将ElecCommonMsg对象压入到栈顶，用于表单回显，将所有数据显示到文本框中
		ValueStackUtils.push(commonMsg);
		return "alermStation";
	}
	
	/**  
	* @Name: alermDevice
	* @Description: 显示设备运行情况
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-17（创建日期）
	* @Parameters: 无
	* @Return: 跳转到menu/alermDevice.jsp
	*/
	public String alermDevice(){
		//1：查询运行监控表，获取运行监控表中的数据，返回ElecCommonMsg对象
		ElecCommonMsg commonMsg = commonMsgService.findElecCommonMsg();
		//2：将ElecCommonMsg对象压入到栈顶，用于表单回显，将所有数据显示到文本框中
		ValueStackUtils.push(commonMsg);
		return "alermDevice";
	}
}
