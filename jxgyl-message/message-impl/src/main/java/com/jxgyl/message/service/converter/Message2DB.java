package com.jxgyl.message.service.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.jxgyl.message.Message;
import com.jxgyl.message.Message.MessageTypeEnum;
import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.service.domain.Message_DB;

/**
 * 信息转数据库类型
 *
 * @author iss002
 *
 */
public class Message2DB {

	public static List<Message_DB> msg2DB(StatusEnum statusE, Message... msgs) {
		if (msgs != null) {
			final List<Message_DB> dbs = new ArrayList<Message_DB>(msgs.length);
			Arrays.stream(msgs).forEach(msg -> dbs.add(toDB(statusE, msg)));
			return dbs;
		}
		return null;
	}

	public static Message[] db2Msg(List<Message_DB> dbs) {
		if (dbs != null) {
			final List<Message> msgs = new ArrayList<Message>(dbs.size());
			dbs.forEach(db -> msgs.add(toMsg(db)));
			return msgs.toArray(new Message[] {});
		}
		return null;
	}

	private static Message toMsg(Message_DB db) {
		Message msg = null;
		if (db != null) {
			if (MessageTypeEnum.EMAIL.name().equals(db.getType())) {
				msg = Message.createEmail(db.getSender(), resolveTos(db.getReceiver()), db.getSubject(), db.getText(),
						Variable2DB.db2Var(db.getVars()), MessageTemplateEnum.mte(db.getTemplate()),
						Attachment2DB.db2Attach(db.getAttachs())).identifyId(db.getIdentifyId());
			}
		}
		return msg;
	}

	private static Message_DB toDB(StatusEnum statusE, Message msg) {
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
			db.setStatus(statusE.status);
			db.setIdentifyId(msg.getIdentifyId());
			db.setAttachs(Attachment2DB.attach2DB(null, statusE, msg.getAttachments()));
			db.setVars(Variable2DB.var2DB(null, statusE, msg.getVars()));
		}
		return db;
	}

	private static String[] resolveTos(String tos) {
		if (tos != null) {
			return tos.split(",");
		}
		return null;
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
		SUCCESS(1), ERROR(0), QUEUE_UP(2), CEASE(-1);
		public Integer status;

		private StatusEnum(Integer status) {
			this.status = status;
		}
	}
}
