package top.gweic.elec.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Servlet;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import top.gweic.elec.domain.ElecSystemDDL;
import top.gweic.elec.domain.ElecUser;
import top.gweic.elec.service.IElecSystemDDLService;
import top.gweic.elec.service.IElecUserService;
import top.gweic.elec.utils.ValueStackUtils;
@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class ElecUserAction extends BaseAction<ElecUser> {

	@Resource
	private IElecUserService elecUserService; 
	@Resource
	private IElecSystemDDLService elecSystemDDLService;
	public String home(){
		//1查询所属单位
		List<ElecSystemDDL> jctList = elecSystemDDLService.findElecSystemDDLListByKeyword("所属单位");
		request.setAttribute("jctList", jctList);
		//2根据条件查询用户信息
		List<ElecUser> userList=elecUserService.findUserListByCondition(this.getModel());
		request.setAttribute("userList", userList);
		return "home";
	}
	
	public String add(){
		//1：查询性别，职位，所属单位，是否在职的下拉菜单
		this.initSystemDDL();
		return "add";
	}
	
	
	public String findJctUnit() throws Exception{
		//将数据字典的值转换成json数据
		//1：使用jquery的ajax获取到所属单位的中文名称（北京）
		String keyword = this.getModel().getJctID();//北京
		//2：以选择的数据类型作为条件，查询对应数据类型的集合，返回List<ElecSystemDDL>
		List<ElecSystemDDL> list = elecSystemDDLService.findElecSystemDDLListByKeyword(keyword);
		//3：将list放置到栈顶，将list使用struts2的方式转换成json数据
//		ValueStackUtils.push(list);
//		return "findJctUnit";
		JsonConfig config=new JsonConfig();
		//设置那些属性排除，不进行json转化
		config.setExcludes(null);
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		String json = jsonArray.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	
	public String checkUser() throws Exception{
		//获取登录名
		String logonName = this.getModel().getLogonName();
		//以登录名作为条件，查询用户表
		String message = elecUserService.checkUser(logonName);
//		//将message放置到栈顶
//		this.getModel().setMessage(message);
		//把message转成json,传给页面
		String toString = JSONUtils.valueToString(message);
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(toString);
		return "none";
	}

	
	public String save(){
		elecUserService.saveUser(this.getModel());
		return "close";
	}
	
	public String edit(){
		//1：使用userID，查询对应用户的详细信息，返回ElecUser对象，放置到栈顶，用于struts2支持的表单回显
		ElecUser user = elecUserService.findElecUserByID(this.getModel());
		//将新对象压入到栈顶之前，从新设置栈顶的值
		user.setViewflag(this.getModel().getViewflag());
		ValueStackUtils.push(user);
		//2：查询性别，职位，所属单位，是否在职的下拉菜单
		this.initSystemDDL();
		//3：从栈顶对象ElecUser，获取数据项的编号（ddlCode=2和keyword="所属单位"，获取数据项的值上海）
		String ddlName = elecSystemDDLService.findDdlNameByKeywordAndDdlCode("所属单位",user.getJctID());
		//4：遍历单位名称的集合(依赖所属单位的名称)，返回List<ElecSystemDDL>
		List<ElecSystemDDL> jctUnitList= elecSystemDDLService.findElecSystemDDLListByKeyword(ddlName);
		request.setAttribute("jctUnitList", jctUnitList);
		return "edit";
	}
	
	
	public String delete(){
		elecUserService.deleteUserByIds(this.getModel());
		return "delete";
	}
	
	private void initSystemDDL() {

		List<ElecSystemDDL> jctList = elecSystemDDLService.findElecSystemDDLListByKeyword("所属单位");
		request.setAttribute("jctList", jctList);
		List<ElecSystemDDL> sexList = elecSystemDDLService.findElecSystemDDLListByKeyword("性别");
		request.setAttribute("sexList", sexList);
		List<ElecSystemDDL> isDutyList = elecSystemDDLService.findElecSystemDDLListByKeyword("是否在职");
		request.setAttribute("isDutyList", isDutyList);
		List<ElecSystemDDL> postList = elecSystemDDLService.findElecSystemDDLListByKeyword("职位");
		request.setAttribute("postList", postList);
	}
}
