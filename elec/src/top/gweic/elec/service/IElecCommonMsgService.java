package top.gweic.elec.service;

import top.gweic.elec.domain.ElecCommonMsg;

public interface IElecCommonMsgService {

	ElecCommonMsg findElecCommonMsg();
	void saveElecCommonMsg(ElecCommonMsg commonMsg);
}
