package com.jxgyl.message.service;

import java.util.List;

import com.jxgyl.message.service.domain.Attachment_DB;

/**
 * 消息附件入库
 *
 * @author iss002
 *
 */
public interface AttachmentDBService {
	
	void batchInsert(List<Attachment_DB> attachs);

}
