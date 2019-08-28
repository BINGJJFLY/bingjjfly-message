package com.jxgyl.message;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 信息
 * 
 * @author iss002
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1890898500091403616L;

	private String from;
	private String[] to;
	private String subject;
	private String text;
	private Attachment[] attachments;
	private List<Variable> vars;
	private MessageTypeEnum type;
	private MessageTemplateEnum business;
	private String identifyId;

	private Message(String[] to, String text, List<Variable> vars, MessageTypeEnum type, MessageTemplateEnum business) {
		this.to = to;
		this.text = text;
		this.vars = vars;
		this.type = type;
		this.business = business;
		this.identifyId = UUID.randomUUID().toString();
	}

	private Message(String from, String[] to, String subject, String text, List<Variable> vars, MessageTypeEnum type,
			MessageTemplateEnum business, Attachment... attachments) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.vars = vars;
		this.type = type;
		this.business = business;
		this.attachments = attachments;
		this.identifyId = UUID.randomUUID().toString();
	}

	/**
	 * 创建Email信息
	 * 
	 * @param from
	 *            源地址
	 * @param to
	 *            目标地址
	 * @param subject
	 *            主题
	 * @param text
	 *            正文
	 * @param vars
	 *            域值对集合
	 * @param business
	 *            业务类型
	 * @param attachments
	 *            附件
	 */
	public static Message createEmail(String from, String[] to, String subject, String text, List<Variable> vars,
			MessageTemplateEnum business, Attachment... attachments) {
		return new Message(from, to, subject, text, vars, MessageTypeEnum.EMAIL, business, attachments);
	}

	/**
	 * 创建短信信息
	 * 
	 * @param to
	 *            接收人
	 * @param text
	 *            内容
	 * @param vars
	 *            域值对集合
	 * @param business
	 *            业务类型
	 * @return
	 */
	public static Message createText(String[] to, String text, List<Variable> vars, MessageTemplateEnum business) {
		return new Message(to, text, vars, MessageTypeEnum.TEXT, business);
	}

	/**
	 * 消息类型
	 * 
	 * @author iss002
	 *
	 */
	public static enum MessageTypeEnum {
		EMAIL, TEXT;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Attachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachment[] attachments) {
		this.attachments = attachments;
	}

	public List<Variable> getVars() {
		return vars;
	}

	public void setVars(List<Variable> vars) {
		this.vars = vars;
	}

	public MessageTypeEnum getType() {
		return type;
	}

	public void setType(MessageTypeEnum type) {
		this.type = type;
	}

	public MessageTemplateEnum getBusiness() {
		return business;
	}

	public void setBusiness(MessageTemplateEnum business) {
		this.business = business;
	}

	public String getIdentifyId() {
		return identifyId;
	}

	public Message identifyId(String identifyId) {
		this.identifyId = identifyId;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(attachments);
		result = prime * result + ((business == null) ? 0 : business.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((identifyId == null) ? 0 : identifyId.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + Arrays.hashCode(to);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((vars == null) ? 0 : vars.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (!Arrays.equals(attachments, other.attachments))
			return false;
		if (business != other.business)
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (identifyId == null) {
			if (other.identifyId != null)
				return false;
		} else if (!identifyId.equals(other.identifyId))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (!Arrays.equals(to, other.to))
			return false;
		if (type != other.type)
			return false;
		if (vars == null) {
			if (other.vars != null)
				return false;
		} else if (!vars.equals(other.vars))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", to=" + Arrays.toString(to) + ", subject=" + subject + ", text=" + text
				+ ", attachments=" + Arrays.toString(attachments) + ", vars=" + vars + ", type=" + type + ", business="
				+ business + ", identifyId=" + identifyId + "]";
	}

}
