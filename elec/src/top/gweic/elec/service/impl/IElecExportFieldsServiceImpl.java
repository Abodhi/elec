package top.gweic.elec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.gweic.elec.dao.IElecExportFieldsDao;
import top.gweic.elec.domain.ElecExportFields;
import top.gweic.elec.service.IElecExportFieldsService;
@Service
@Transactional
public class IElecExportFieldsServiceImpl implements IElecExportFieldsService {

	@Autowired
	private IElecExportFieldsDao elecExportFieldsDao;
	@Override
	public void saveSetExportExcel(ElecExportFields elecExportFields) {

		//保存导出设置
		elecExportFieldsDao.update(elecExportFields);
	}

	@Override
	public ElecExportFields findElecExportFields(String belongTo) {
		
		//根据id查询导出设置数据
		ElecExportFields elecExportFields = elecExportFieldsDao.findObjectById(belongTo);
		return elecExportFields;
	}

}
