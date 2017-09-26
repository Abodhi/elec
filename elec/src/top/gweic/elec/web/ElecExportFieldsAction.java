package top.gweic.elec.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import top.gweic.elec.domain.ElecExportFields;
import top.gweic.elec.service.IElecExportFieldsService;
import top.gweic.elec.utils.StringToList;
@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class ElecExportFieldsAction extends BaseAction<ElecExportFields> {

	@Resource
	private IElecExportFieldsService elecExportFieldsService;
	
	/**  
	* @Name: setExportExcel
	* @Description: 跳转导出设置的页面（左移右移）
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-17（创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/exportExcel.jsp
	*/
	public String setExportExcel(){

		//根据页面传来的id即belongto查询导出设置表中，要导出的和未导出的数据，回显到页面
		String belongTo = this.getModel().getBelongTo();
		ElecExportFields exportFields = elecExportFieldsService.findElecExportFields(belongTo);
		//将数据拆分成map,分为导出数据，未导出数据，map的key未英文数据，value为中文数据，对应页面回显中select框中option的name和value
		//导出的英文数据
		String expFieldName = exportFields.getExpFieldName();
		//导出的中文数据
		String expNameList = exportFields.getExpNameList();
		//未导出的中文数据
		String noExpNameList = exportFields.getNoExpNameList();
		//未导出的英文数据
		String noExpFieldName = exportFields.getNoExpFieldName();
		//拆分构造集合
		List<String> expFieldlist = StringToList.getList(expFieldName,"#");
		List<String> expNamelist = StringToList.getList(expNameList,"#");
		Map<String,String> map=new HashMap<String,String>();
		if(expFieldlist!=null){
			int i=0;
			for (String fieldName : expFieldlist) {
				map.put(fieldName, expNamelist.get(i++));
			}
		}
		
		
		List<String> noexpFieldlist = StringToList.getList(noExpFieldName,"#");
		List<String> noexpNamelist = StringToList.getList(noExpNameList,"#");
		
		Map<String,String> noMap=new HashMap<String,String>();
		if(noexpFieldlist!=null){
			int j=0;
			for (String nofieldName : noexpFieldlist) {
				noMap.put(nofieldName, noexpNamelist.get(j++));
			}
		}
		request.setAttribute("map", map);
		request.setAttribute("noMap", noMap);
		return "setExportExcel";
	}
	
	/**  
	* @Name: saveSetExportExcel
	* @Description: 保存导出设置
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-11-17（创建日期）
	* @Parameters: 无
	* @Return: 跳转到close.jsp(关闭页面)
	*/
	public String saveSetExportExcel(){
		elecExportFieldsService.saveSetExportExcel(this.getModel());
		return "close";
	}
}
