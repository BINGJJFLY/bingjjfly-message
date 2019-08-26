package com.jxgyl.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxgyl.message.mapper.AttachmentDBMapper;
import com.jxgyl.message.service.domain.Attachment_DB;

@Service("attachmentDBService")
public class AttachmentDBServiceImpl implements AttachmentDBService {
	
	@Autowired
	private AttachmentDBMapper mapper;

	@Override
	public void batchInsert(List<Attachment_DB> attachs) {
		mapper.batchInsert(attachs);
	}

}
