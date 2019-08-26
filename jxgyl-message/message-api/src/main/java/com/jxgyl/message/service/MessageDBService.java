package com.jxgyl.message.service;

import java.util.List;

import com.jxgyl.message.service.domain.Message_DB;

/**
 * 消息入库
 *
 * @author iss002
 *
 */
public interface MessageDBService {

	/**
	 * 入库
	 * 
	 * @param msgs
	 */
	void batchInsert(List<Message_DB> msgs);

	/**
	 * 标记异常
	 * 
	 * @param list
	 *            Message的UUID集合
	 */
	void markAbnormal(List<String> list);

	/**
	 * 查询异常信息
	 * 
	 * @return
	 */
	List<Message_DB> selectAbnormal();
}
