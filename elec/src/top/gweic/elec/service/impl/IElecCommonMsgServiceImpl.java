package top.gweic.elec.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.gweic.elec.dao.IElecCommonMsgDao;
import top.gweic.elec.domain.ElecCommonMsg;
import top.gweic.elec.service.IElecCommonMsgService;

@Service
@Transactional
public class IElecCommonMsgServiceImpl implements IElecCommonMsgService{

	@Autowired
	private IElecCommonMsgDao elecCommonMsgDao;
	@Override
	public ElecCommonMsg findElecCommonMsg() {
		//通过调节查询所有，取一个
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		ElecCommonMsg commonMsg=null;
		if(list!=null&&list.size()>0){
			commonMsg=list.get(0);
		}
		return commonMsg;
	}
	@Override
	public void saveElecCommonMsg(ElecCommonMsg commonMsg) {

		//查询运行信息，判断是否存在信息
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		if(list!=null&&list.size()>0){
			//执行更新操作
			//msg 为持久状态，不用重新创建对象进行插入，可以对持久对象进行设值，而session中不能存在主键id相同的对象
			ElecCommonMsg msg = list.get(0);
			msg.setCreateDate(new Date());
			msg.setDevRun(commonMsg.getDevRun());
			msg.setStationRun(commonMsg.getStationRun());
			elecCommonMsgDao.update(msg);
			return;
		}
		commonMsg.setComID(UUID.randomUUID().toString());
		commonMsg.setCreateDate(new Date());
		elecCommonMsgDao.save(commonMsg);
		return;
		
	}

}
