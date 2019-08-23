package com.jxgyl.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jxgyl.message.Message;
import com.jxgyl.message.service.domain.Message_DB;

public class Message2DB {

	public static List<Message_DB> msg2DB(Message... msgs) {
		List<Message_DB> dbs = null;
		if (msgs != null && msgs.length > 0) {
			dbs = new ArrayList<Message_DB>(msgs.length);
			for (int i = 0; i < msgs.length; i++) {
				dbs.add(toDB(msgs[i]));
			}
		}
		return dbs;
	}

	private static Message_DB toDB(Message msg) {
		Message_DB db = null;
		if (msg != null) {
			db = new Message_DB();
			db.setSender(msg.getFrom());
			db.setReceiver(resolveTos(msg.getTo()));
			db.setSubject(msg.getSubject());
			db.setTemplate(msg.getBusiness().getTemplate());
			db.setText(msg.getText());
			db.setType(msg.getType().name());
			db.setAddTime(new Date());
		}
		return db;
	}

	private static String resolveTos(String[] tos) {
		String result = null;
		if (tos != null) {
			final StringBuilder receiver = new StringBuilder();
			for (int i = 0; i < tos.length; i++) {
				receiver.append(tos[i]).append(",");
			}
			result = receiver.deleteCharAt(receiver.length() - 1).toString();
		}
		return result;
	}

}
