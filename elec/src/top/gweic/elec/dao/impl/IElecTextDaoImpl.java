package top.gweic.elec.dao.impl;

import org.springframework.stereotype.Repository;

import top.gweic.elec.dao.IElecTextDao;
import top.gweic.elec.domain.ElecText;
//使用Hibernate模板
@Repository
public class IElecTextDaoImpl extends BaseDaoImpl<ElecText> implements IElecTextDao {
}
