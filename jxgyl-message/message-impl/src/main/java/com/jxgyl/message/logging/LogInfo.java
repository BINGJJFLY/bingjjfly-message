package com.jxgyl.message.logging;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志信息
 * 
 * @author iss002
 *
 */
public class LogInfo implements Serializable {

	private static final long serialVersionUID = 4160916120695201868L;

	private LogInfo() {
	}

	private String msg;
	private Date time;
	private String userId;

	public static class Builder implements Serializable {

		private static final long serialVersionUID = -3262811505104692044L;

		final LogInfo info = new LogInfo();

		public Builder msg(String msg) {
			info.msg = msg;
			return this;
		}

		public Builder time(Date time) {
			info.time = time;
			return this;
		}

		public Builder userId(String userId) {
			info.userId = userId;
			return this;
		}

		public LogInfo build() {
			return info;
		}

	}

	public String getMsg() {
		return msg;
	}

	public Date getTime() {
		return time;
	}

	public String getUserId() {
		return userId;
	}

}
