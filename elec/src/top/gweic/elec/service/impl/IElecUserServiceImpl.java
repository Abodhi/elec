package top.gweic.elec.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import top.gweic.elec.dao.IElecSystemDDLDao;
import top.gweic.elec.dao.IElecUserDao;
import top.gweic.elec.domain.ElecUser;
import top.gweic.elec.service.IElecUserService;
import top.gweic.elec.utils.MD5keyBean;
@Service
@Transactional(readOnly=true)
public class IElecUserServiceImpl implements IElecUserService {

	@Autowired
	private IElecUserDao elecUserDao; 
	
	@Autowired
	private IElecSystemDDLDao elecSystemDDLDao;
	
	@Override
	public List<ElecUser> findUserListByCondition(ElecUser model) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ElecUser.class);
		//构造查询语句
		if(StringUtils.isNotBlank(model.getUserName())){
			//姓名
			criteria.add(Restrictions.like("userName", "%"+model.getUserName()+"%"));
		}
		if(StringUtils.isNotBlank(model.getJctID())){
			//所属单位
			criteria.add(Restrictions.eq("jctID", model.getJctID()));
		}
		if(model.getOnDutyDateBegin()!=null){
			//入职时间开始
			criteria.add(Restrictions.ge("onDutyDate", model.getOnDutyDateBegin()));
		}
		if(model.getOnDutyDateEnd()!=null){
			//入职时间结束
			criteria.add(Restrictions.le("onDutyDate", model.getOnDutyDateEnd()));
		}
		//排序
		Order order=Order.desc("onDutyDate");
		criteria.addOrder(order);
		List<ElecUser> list = elecUserDao.findByCriteria(criteria);
		//将查询的用户信息转成数据项
		this.userPOListToVOList(list);
		return list;
	}

	private void userPOListToVOList(List<ElecUser> list) {

		if(list!=null&&list.size()>0){
			for (ElecUser elecUser : list) {
				//性别
				elecUser.setSexID(StringUtils.isNotBlank(elecUser.getSexID())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("性别",elecUser.getSexID()):"");
				//所属单位
				elecUser.setJctID(StringUtils.isNotBlank(elecUser.getJctID())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("所属单位",elecUser.getJctID()):"");
				//是否在职
				elecUser.setIsDuty(StringUtils.isNotBlank(elecUser.getIsDuty())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("是否在职",elecUser.getIsDuty()):"");
				//职位
				elecUser.setPostID(StringUtils.isNotBlank(elecUser.getPostID())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("职位",elecUser.getPostID()):"");
			}
		}
	}

	@Override
	public String checkUser(String logonName) {
		
		String message = "";
		if(StringUtils.isNotBlank(logonName)){
			String condition = " and o.logonName = ?";
			Object [] params = {logonName};
			/**
			 * 2：以登录名作为条件，查询用户表，返回List<ElecUser>
			 *   如果登录名为空，返回message=1
				  如果list不为空，说明数据库中已经存在值，返回message=2
				  如果list为空，说明数据库中没有值，返回message=3
			 */
			List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, null);
			if(list!=null && list.size()>0){
				message = "2";
			}
			else{
				message = "3";
			}
		}
		else{
			message = "1";
		}
		return message;
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void saveUser(ElecUser elecUser) {
		//获取用户ID
				String userID = elecUser.getUserID();
				//添加md5的密码加密
				this.md5Password(elecUser);
				//1：如果userID==null，直接获取保存的PO对象，执行save()
				if(StringUtils.isBlank(userID)){
					elecUserDao.save(elecUser);
				}
				//2：如果userID!=null，获取更新的PO对象，执行update()
				else{
					elecUserDao.update(elecUser);
				}
	}

	/**添加md5的密码加密，对登录名的密码进行安全的控制*/
	private void md5Password(ElecUser elecUser) {
		//获取页面输入的密码
		String logonPwd = elecUser.getLogonPwd();
		//加密后的密码
		String md5LogonPwd = "";
		//如果密码没有填写，给出初始密码123
		if(StringUtils.isBlank(logonPwd)){
			logonPwd = "123";
		}
		//判断是否对密码进行了修改，获取password
		String password = elecUser.getPassword();
		//表示没有修改密码，此时不需要进行加密
		if(password!=null && password.equals(logonPwd)){
			md5LogonPwd = logonPwd;
		}
		else{
			//md5密码加密
			MD5keyBean md5keyBean = new MD5keyBean();
			md5LogonPwd = md5keyBean.getkeyBeanofStr(logonPwd);
		}
		//最后讲加密后的密码放置到ElecUser中
		elecUser.setLogonPwd(md5LogonPwd);
	}

	@Override
	public ElecUser findElecUserByID(ElecUser elecUser) {
		//获取用户ID
				String userID = elecUser.getUserID();
				ElecUser user = elecUserDao.findObjectById(userID);
				return user;
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void deleteUserByIds(ElecUser elecUser) {
		//获取ID
				String userID = elecUser.getUserID();
				String [] ids = userID.split(", ");
				elecUserDao.delete(ids);
	}

}
