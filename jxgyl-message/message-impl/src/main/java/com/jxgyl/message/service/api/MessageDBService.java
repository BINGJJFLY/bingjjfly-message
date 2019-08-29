package com.jxgyl.message.service.api;

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
	 */
	void markAbnormal(List<Integer> list);

	/**
	 * 标记正常
	 * 
	 * @param identifyIds
	 *            Message的UUID集合
	 */
	void markNormal(List<String> identifyIds);

	/**
	 * 查询异常信息
	 * 
	 * @return
	 */
	List<Message_DB> selectAbnormal();
	
	/**
	 * 根据UUID查询排队中的信息
	 * 
	 * @param identities
	 * @return
	 */
	List<Integer> selectPrimarykeysByIdentifyIdsQueueUp(List<String> identities);
	
	List<Integer> selectPrimarykeysByIdentifyIds(List<String> identities);

}
