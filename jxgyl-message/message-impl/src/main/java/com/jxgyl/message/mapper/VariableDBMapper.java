package com.jxgyl.message.mapper;

import java.util.List;

import com.jxgyl.message.service.domain.Variable_DB;

public interface VariableDBMapper {

	void batchInsert(List<Variable_DB> list);

	void batchUpdate(List<Integer> list);

}
