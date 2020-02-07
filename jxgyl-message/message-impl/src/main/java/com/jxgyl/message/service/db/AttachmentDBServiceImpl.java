package com.jxgyl.message.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jxgyl.message.MessageException;
import com.jxgyl.message.mapper.AttachmentDBMapper;
import com.jxgyl.message.service.api.AttachmentDBService;
import com.jxgyl.message.service.domain.Attachment_DB;

@Transactional
@Service("attachmentDBService")
public class AttachmentDBServiceImpl implements AttachmentDBService {

	@Autowired
	private AttachmentDBMapper mapper;

	@Override
	public void batchInsert(List<Attachment_DB> attachs) throws MessageException {
		try {
			if (!CollectionUtils.isEmpty(attachs)) {
				mapper.batchInsert(attachs);
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

	@Override
	public void markNormal(List<Integer> msgIds) throws MessageException {
		try {
			if (!CollectionUtils.isEmpty(msgIds)) {
				mapper.batchUpdateNormal(msgIds);
			}
		} catch (Exception e) {
			throw new MessageException(e);
		}
	}

}
