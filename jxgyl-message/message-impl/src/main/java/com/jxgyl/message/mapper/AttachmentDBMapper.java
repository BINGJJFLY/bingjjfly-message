package com.jxgyl.message.mapper;

import java.util.List;

import com.jxgyl.message.service.domain.Attachment_DB;

public interface AttachmentDBMapper {

	void batchInsert(List<Attachment_DB> list);

	void batchUpdate(List<Integer> list);

}
