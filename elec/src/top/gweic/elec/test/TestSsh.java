package top.gweic.elec.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import top.gweic.elec.dao.IElecTextDao;
import top.gweic.elec.domain.ElecText;
import top.gweic.elec.service.IElecTextService;

public class TestSsh {

	@Test
	public void test1(){
		//加载hibernate配置文件
		Configuration configuration=new Configuration();
		Configuration configure = configuration.configure();
		SessionFactory sessionFactory = configure.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		//开启事物
		Transaction transaction = session.beginTransaction();
		ElecText elecText=new ElecText();
		elecText.setTextID(UUID.randomUUID().toString());
		elecText.setTextDate(new Date());
		elecText.setTextName("张三");
		elecText.setTextRemark("是个诺");
		session.save(elecText);
		//事物提交
		transaction.commit();
		session.close();
	}
	
	
	@Test
	public void test2(){
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IElecTextDao dao = applicationContext.getBean(IElecTextDao.class);
		ElecText elecText=new ElecText();
		elecText.setTextID(UUID.randomUUID().toString());
		elecText.setTextDate(new Date());
		elecText.setTextName("张三");
		elecText.setTextRemark("是个诺");
		dao.save(elecText);
	}
	
	@Test
	public void test3(){
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IElecTextService service = applicationContext.getBean(IElecTextService.class);
		ElecText elecText=new ElecText();
		elecText.setTextID(UUID.randomUUID().toString());
		elecText.setTextDate(new Date());
		elecText.setTextName("张三");
		elecText.setTextRemark("是个诺");
		service.save(elecText);
	}
	

	@Test
	public void test4(){
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IElecTextDao dao = applicationContext.getBean(IElecTextDao.class);
//		ElecText text = dao.findObjectById("8a6f80865dcba73b015dcba73d400000");
//		System.out.println(text.getTextName());
		
//		dao.delete("8a6f80865dcba73b015dcba73d400000","8a6f80865dcba8a5015dcba8a6be0000");
//		Collection<ElecText> c=new ArrayList<ElecText>();
//		ElecText elecText=new ElecText();
//		elecText.setTextID("8a6f80865dcbc51a015dcbc51c490000");
//		c.add(elecText);
//		dao.deleteCollection(c);
		String condition="and o.textName like ? and o.textRemark like ?";
		List param=new ArrayList<>();
		param.add("%张%");
		param.add("%是个诺%");
		Object[] params=param.toArray();
		Map<String, String> orderby=new LinkedHashMap<>();
		orderby.put("o.textName", "asc");
		List<ElecText> list = dao.findCollectionByConditionNoPage(condition, params, orderby);
		for (ElecText elecText2 : list) {
			System.out.println(elecText2.getTextName());
		}
		
	}
}
