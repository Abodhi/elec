package top.gweic.elec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.gweic.elec.dao.IElecTextDao;
import top.gweic.elec.domain.ElecText;
import top.gweic.elec.service.IElecTextService;
@Service
@Transactional
public class IElecTextServiceImpl implements IElecTextService{

	@Autowired
	private IElecTextDao elecTextDao;
	@Override
	public void save(ElecText elecText) {
		elecTextDao.save(elecText);
	}

}
