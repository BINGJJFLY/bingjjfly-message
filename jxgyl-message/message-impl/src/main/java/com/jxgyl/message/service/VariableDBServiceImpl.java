package com.jxgyl.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jxgyl.message.MessageException;
import com.jxgyl.message.mapper.VariableDBMapper;
import com.jxgyl.message.service.domain.Variable_DB;

@Transactional
@Service("variableDBService")
public class VariableDBServiceImpl implements VariableDBService {

	@Autowired
	private VariableDBMapper mapper;

	@Override
	public void batchInsert(List<Variable_DB> vars) throws MessageException {
		try {
			if (!CollectionUtils.isEmpty(vars)) {
				mapper.batchInsert(vars);
			}
		} catch (Exception e) {
			throw new MessageException(e);
		}
	}

	@Override
	public void markAbnormal(List<Integer> msgIds) throws MessageException {
		try {
			if (!CollectionUtils.isEmpty(msgIds)) {
				mapper.batchUpdate(msgIds);
			}
		} catch (Exception e) {
			throw new MessageException(e);
		}
	}

}
