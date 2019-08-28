package com.jxgyl.message.service.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jxgyl.message.Variable;
import com.jxgyl.message.service.converter.Message2DB.StatusEnum;
import com.jxgyl.message.service.domain.Variable_DB;

/**
 * 域值对转数据库类型
 *
 * @author iss002
 *
 */
public class Variable2DB {

	public static List<Variable_DB> var2DB(final Integer msgId, List<Variable> vars) {
		if (vars != null) {
			final List<Variable_DB> dbs = new ArrayList<Variable_DB>(vars.size());
			vars.forEach(var -> dbs.add(toDB(msgId, var)));
			return dbs;
		}
		return null;
	}

	public static List<Variable> db2Var(List<Variable_DB> dbs) {
		if (dbs != null) {
			final List<Variable> vars = new ArrayList<>(dbs.size());
			dbs.forEach(db -> vars.add(toVar(db)));
			return vars;
		}
		return null;
	}

	private static Variable toVar(Variable_DB db) {
		Variable var = null;
		if (db != null) {
			var = Variable.createVar(db.getName(), db.getVal());
		}
		return var;
	}

	private static Variable_DB toDB(Integer msgId, Variable var) {
		Variable_DB db = null;
		if (var != null) {
			db = new Variable_DB();
			db.setMsgId(msgId);
			db.setName(var.getName());
			db.setVal(var.getValue());
			db.setAddTime(Calendar.getInstance().getTime());
			db.setStatus(StatusEnum.ERROR.status);
		}
		return db;
	}

}
