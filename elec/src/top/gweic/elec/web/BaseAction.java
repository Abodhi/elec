package top.gweic.elec.web;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,ServletRequestAware,ServletResponseAware{
	private T entity;
	@Override
	public T getModel() {
		return entity;
	}
	
	//受保护类型，子类可以使用
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	public BaseAction() {
		//拿到正在执行的action的父类，即提取出来的baseaction
		 ParameterizedType type=(ParameterizedType) this.getClass().getGenericSuperclass();
		 //拿到父类class对象的泛型数组中的第一个
		 Class c = (Class) type.getActualTypeArguments()[0];
		try {
			entity=(T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse res) {
		this.response=res;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.request=req;
	}

}
