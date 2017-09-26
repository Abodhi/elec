package top.gweic.elec.service;

import java.util.List;

import top.gweic.elec.domain.ElecSystemDDL;

public interface IElecSystemDDLService {

	List<ElecSystemDDL> findDistinctKeywod();

	List<ElecSystemDDL> findElecSystemDDLListByKeyword(String keyword);

	void saveElecSystemDDL(ElecSystemDDL model);

	String findDdlNameByKeywordAndDdlCode(String string, String jctID);

}
