package com.thirdware.vo.sfh;

public class KeyValueHolder {

	public KeyValueHolder(int parentValue, int key, String value) {
		super();
		this.parentValue = parentValue;
		this.key = key;
		this.value = value;
	}

	private int key;
	
	private String value;
	
	private int parentValue;

	public int getParentValue() {
		return parentValue;
	}

	public void setParentValue(int parentValue) {
		this.parentValue = parentValue;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
