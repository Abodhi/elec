package top.gweic.elec.dao;

import java.util.List;

import top.gweic.elec.domain.ElecSystemDDL;

public interface IElecSystemDDLDao extends BaseDao<ElecSystemDDL> {

	List<ElecSystemDDL> findDistinctKeywod();

	String findDdlNameByKeywordAndDdlCode(String string, String sexID);

}
