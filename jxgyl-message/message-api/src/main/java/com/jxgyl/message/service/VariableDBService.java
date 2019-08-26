package com.jxgyl.message.service;

import java.util.List;

import com.jxgyl.message.MessageException;
import com.jxgyl.message.service.domain.Variable_DB;

/**
 * 域值对入库
 *
 * @author iss002
 *
 */
public interface VariableDBService {

	/**
	 * 入库
	 * 
	 * @param vars
	 * @throws MessageException
	 */
	void batchInsert(List<Variable_DB> vars) throws MessageException;

	/**
	 * 标记异常
	 * 
	 * @param msgIds
	 *            Message_DB的主键
	 * @throws MessageException
	 */
	void markAbnormal(List<Integer> msgIds) throws MessageException;

}
