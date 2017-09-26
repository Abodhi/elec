package top.gweic.elec.service;

import top.gweic.elec.domain.ElecExportFields;

public interface IElecExportFieldsService {

	void saveSetExportExcel(ElecExportFields elecExportFields);

	ElecExportFields findElecExportFields(String belongTo);

}
