package com.jxgyl.message.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jxgyl.message.mapper.MessageDBMapper;
import com.jxgyl.message.service.domain.Attachment_DB;
import com.jxgyl.message.service.domain.Message_DB;
import com.jxgyl.message.service.domain.Variable_DB;

@Transactional
@Service("messageDBService")
public class MessageDBServiceImpl implements MessageDBService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageDBServiceImpl.class);

	@Autowired
	private MessageDBMapper mapper;
	@Autowired
	private AttachmentDBService attachmentDBService;
	@Autowired
	private VariableDBService variableDBService;

	@Override
	public void batchInsert(List<Message_DB> msgs) {
		try {
			if (!CollectionUtils.isEmpty(msgs)) {
				mapper.batchInsert(msgs);
				final List<Attachment_DB> attachs = new ArrayList<>();
				final List<Variable_DB> vars = new ArrayList<>();
				msgs.forEach(msg -> {
					final List<Attachment_DB> adbs = msg.getAttachs();
					final List<Variable_DB> vdbs = msg.getVars();
					if (!CollectionUtils.isEmpty(adbs)) {
						adbs.forEach(adb -> adb.setMsgId(msg.getId()));
					}
					if (!CollectionUtils.isEmpty(vdbs)) {
						vdbs.forEach(vdb -> vdb.setMsgId(msg.getId()));
					}
					attachs.addAll(adbs);
					vars.addAll(vdbs);
				});
				attachmentDBService.batchInsert(attachs);
				variableDBService.batchInsert(vars);
				LOGGER.info("【消息入库】\r\n{}", msgs);
			}
		} catch (Exception e) {
			LOGGER.error("【消息入库时异常】\r\n{}", msgs, e);
		}
	}

	@Override
	public void markAbnormal(List<String> list) {
		try {
			if (!CollectionUtils.isEmpty(list)) {
				List<Integer> msgIds = selectPrimarykeysByIdentifyIds(list);
				mapper.batchUpdate(msgIds);
				attachmentDBService.markAbnormal(msgIds);
				variableDBService.markAbnormal(msgIds);
				LOGGER.info("【标记异常消息】\r\n{}", list);
			}
		} catch (Exception e) {
			LOGGER.error("【标记异常消息时异常】\r\n{}", list, e);
		}
	}

	private List<Integer> selectPrimarykeysByIdentifyIds(List<String> identities) {
		return mapper.selectPrimarykeysByIdentifyIds(identities);
	}

	@Override
	public List<Message_DB> selectAbnormal() {
		return mapper.selectAbnormal();
	}

}
