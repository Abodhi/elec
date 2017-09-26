package top.gweic.elec.utils;

import org.apache.struts2.ServletActionContext;

public class ValueStackUtils {

	public static void push(Object arg1) {
		//set 放集合，push 放对象
		ServletActionContext.getContext().getValueStack().push(arg1);
	}
}
