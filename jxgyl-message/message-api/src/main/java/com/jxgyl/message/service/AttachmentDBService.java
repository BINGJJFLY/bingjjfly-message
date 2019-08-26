package com.jxgyl.message.service;

import java.util.List;

import com.jxgyl.message.MessageException;
import com.jxgyl.message.service.domain.Attachment_DB;

/**
 * 消息附件入库
 *
 * @author iss002
 *
 */
public interface AttachmentDBService {

	/**
	 * 入库
	 * 
	 * @param attachs
	 * @throws MessageException
	 */
	void batchInsert(List<Attachment_DB> attachs) throws MessageException;

	/**
	 * 标记异常
	 * 
	 * @param msgIds
	 *            Message_DB的主键
	 * @throws MessageException
	 */
	void markAbnormal(List<Integer> msgIds) throws MessageException;

}
