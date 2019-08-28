package com.jxgyl.message.mapper;

import java.util.List;

import com.jxgyl.message.service.domain.Message_DB;

public interface MessageDBMapper {

	void batchInsert(List<Message_DB> list);

	void batchUpdate(List<Integer> list);
	
	void batchUpdateNormal(List<Integer> list);

	List<Message_DB> selectAbnormal();

	List<Integer> selectPrimarykeysByIdentifyIdsAbnormal(List<String> list);

	List<Integer> selectPrimarykeysByIdentifyIdsNormal(List<String> list);

}
