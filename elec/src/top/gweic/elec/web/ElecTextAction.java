package top.gweic.elec.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.sun.xml.internal.stream.Entity;

import top.gweic.elec.domain.ElecText;
import top.gweic.elec.service.IElecTextService;

@Controller
public class ElecTextAction extends BaseAction<ElecText>{

	@Resource
	private IElecTextService elecTextService;
	
	public String save(){
		elecTextService.save(this.getModel());
		return "save";
	}
}
