package top.gweic.elec.dao.impl;

import org.springframework.stereotype.Repository;

import top.gweic.elec.dao.IElecCommonMsgDao;
import top.gweic.elec.domain.ElecCommonMsg;
//使用Hibernate模板
@Repository
public class IElecCommonMsgDaoImpl extends BaseDaoImpl<ElecCommonMsg> implements IElecCommonMsgDao {
}
