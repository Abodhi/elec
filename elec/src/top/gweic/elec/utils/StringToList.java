package top.gweic.elec.utils;

import java.util.ArrayList;
import java.util.List;

public class StringToList {
	
	public static List<String> getList(String obj,String reg){
		List<String> list = new ArrayList<String>();
		if (obj != null && !"".equals(obj)) {
			String[] split = obj.split(reg);
			for (String string : split) {
				list.add(string);
			}
			return list;
		}
		list.add(obj);
		return list;
	}

}
