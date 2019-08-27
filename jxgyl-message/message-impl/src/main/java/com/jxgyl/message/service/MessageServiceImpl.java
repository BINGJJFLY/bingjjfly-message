package com.jxgyl.message.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxgyl.message.Message;
import com.jxgyl.message.service.api.MessageDBService;
import com.jxgyl.message.service.converter.Message2DB;

@Transactional
@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDBService messageDBService;

	@Override
	public void batchInsert(Message... msgs) {
		messageDBService.batchInsert(Message2DB.msg2DB(msgs));
	}

	@Override
	public void markAbnormal(Message... msgs) {
		if (msgs != null) {
			final List<String> identifyIds = new ArrayList<String>(msgs.length);
			Arrays.stream(msgs).forEach(msg -> identifyIds.add(msg.getIdentifyId()));
			messageDBService.markAbnormal(identifyIds);
		}
	}

}
