package com.jxgyl.message.mapper;

import java.util.List;

import com.jxgyl.message.service.domain.Message_DB;

public interface MessageDBMapper {

	void batchInsert(List<Message_DB> list);

}
