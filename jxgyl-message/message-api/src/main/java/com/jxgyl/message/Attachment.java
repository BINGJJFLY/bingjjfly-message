package com.jxgyl.message;

import java.io.Serializable;

/**
 * 邮件内附件信息
 * 
 * @author iss002
 *
 */
public class Attachment implements Serializable {

	private static final long serialVersionUID = -5433713216325104214L;

	private String name;
	private String path;

	private Attachment(String name, String path) {
		this.name = name;
		this.path = path;
	}

	/**
	 * 创建附件
	 * 
	 * @param name
	 *            名称
	 * @param path
	 *            地址
	 * @return
	 */
	public static Attachment createAttach(String name, String path) {
		return new Attachment(name, path);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		Attachment other = (Attachment) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attachment [name=" + name + ", path=" + path + "]";
	}

}
