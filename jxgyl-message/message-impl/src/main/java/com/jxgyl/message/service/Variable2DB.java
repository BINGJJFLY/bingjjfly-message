package com.jxgyl.message.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.jxgyl.message.Variable;
import com.jxgyl.message.service.Message2DB.StatusEnum;
import com.jxgyl.message.service.domain.Variable_DB;

/**
 * 域值对转数据库类型
 *
 * @author iss002
 *
 */
public class Variable2DB {

	public static List<Variable_DB> var2DB(final Integer msgId, List<Variable> vars) {
		if (!CollectionUtils.isEmpty(vars)) {
			final List<Variable_DB> dbs = new ArrayList<Variable_DB>(vars.size());
			vars.forEach(var -> dbs.add(toDB(msgId, var)));
			return dbs;
		}
		return null;
	}

	private static Variable_DB toDB(Integer msgId, Variable var) {
		Variable_DB db = null;
		if (var != null) {
			db = new Variable_DB();
			db.setMsgId(msgId);
			db.setName(var.getName());
			db.setVal(var.getValue());
			db.setAddTime(Calendar.getInstance().getTime());
			db.setStatus(StatusEnum.SUCCESS.status);
		}
		return db;
	}

}
