package com.jxgyl.message.service.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.jxgyl.message.Attachment;
import com.jxgyl.message.service.converter.Message2DB.StatusEnum;
import com.jxgyl.message.service.domain.Attachment_DB;

/**
 * 附件转数据库类型
 *
 * @author iss002
 *
 */
public class Attachment2DB {

	public static List<Attachment_DB> attach2DB(final Integer msgId, Attachment... attachs) {
		if (attachs != null) {
			final List<Attachment_DB> dbs = new ArrayList<Attachment_DB>(attachs.length);
			Arrays.stream(attachs).forEach(attach -> dbs.add(toDB(msgId, attach)));
			return dbs;
		}
		return null;
	}

	private static Attachment_DB toDB(Integer msgId, Attachment attach) {
		Attachment_DB db = null;
		if (attach != null) {
			db = new Attachment_DB();
			db.setMsgId(msgId);
			db.setName(attach.getName());
			db.setPath(attach.getPath());
			db.setAddTime(Calendar.getInstance().getTime());
			db.setStatus(StatusEnum.SUCCESS.status);
		}
		return db;
	}

}
