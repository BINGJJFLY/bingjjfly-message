package com.jxgyl.message;

import java.io.Serializable;

/**
 * 模板域值对
 * 
 * @author iss002
 *
 */
public class Variable implements Serializable {

	private static final long serialVersionUID = 5866608075076019915L;
	
	private String name;
	private String value;

	private Variable(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 创建Thymeleaf模板域填充对象
	 * 
	 * @param name
	 *            域名
	 * @param value
	 *            填充值
	 * @return
	 */
	public static Variable createVar(String name, String value) {
		return new Variable(name, value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Variable [name=" + name + ", value=" + value + "]";
	}

}
