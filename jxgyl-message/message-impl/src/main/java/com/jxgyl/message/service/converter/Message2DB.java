package com.jxgyl.message.service.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.jxgyl.message.Message;
import com.jxgyl.message.service.domain.Message_DB;

/**
 * 信息转数据库类型
 *
 * @author iss002
 *
 */
public class Message2DB {

	public static List<Message_DB> msg2DB(Message... msgs) {
		if (msgs != null) {
			final List<Message_DB> dbs = new ArrayList<Message_DB>(msgs.length);
			Arrays.stream(msgs).forEach(msg -> dbs.add(toDB(msg)));
			return dbs;
		}
		return null;
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
			db.setAddTime(Calendar.getInstance().getTime());
			db.setStatus(StatusEnum.SUCCESS.status);
			db.setIdentifyId(msg.getIdentifyId());
			db.setAttachs(Attachment2DB.attach2DB(null, msg.getAttachments()));
			db.setVars(Variable2DB.var2DB(null, msg.getVars()));
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

	public static enum StatusEnum {
		SUCCESS(1), ERROR(0);
		public Integer status;

		private StatusEnum(Integer status) {
			this.status = status;
		}
	}
}
