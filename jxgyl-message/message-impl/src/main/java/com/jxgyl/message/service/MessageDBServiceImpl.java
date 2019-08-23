package com.jxgyl.message.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxgyl.message.mapper.MessageDBMapper;
import com.jxgyl.message.service.domain.Message_DB;

/**
 * 消息入库实现组件
 *
 * @author iss002
 *
 */
@Transactional
@Service("messageDBService")
public class MessageDBServiceImpl implements MessageDBService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageDBServiceImpl.class);

	@Autowired
	private MessageDBMapper mapper;

	@Override
	public void batchInsert(List<Message_DB> msgs) {
		try {
			mapper.batchInsert(msgs);
			LOGGER.info("【消息入库】\r\n{}", msgs);
		} catch (Exception e) {
			LOGGER.error("【消息入库时异常】\r\n{}\r\n{}", msgs, e);
		}
	}

}
