package top.gweic.elec.service;

import java.util.List;

import top.gweic.elec.domain.ElecUser;

public interface IElecUserService {

	List<ElecUser> findUserListByCondition(ElecUser model);

	String checkUser(String logonName);

	void saveUser(ElecUser model);

	ElecUser findElecUserByID(ElecUser model);

	void deleteUserByIds(ElecUser model);

}
