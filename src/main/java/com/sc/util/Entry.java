package com.sc.util;

public class Entry {
	long timeTolive;
	Object value;
	Object key;
	Entry left;
	Entry right;
	
	
	public long getTimeTolive() {
		return timeTolive;
	}


	public void setTimeTolive(long timeTolive) {
		this.timeTolive = timeTolive;
	}


	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = value;
	}


	public Object getKey() {
		return key;
	}


	public void setKey(Object key) {
		this.key = key;
	}


	public Entry getLeft() {
		return left;
	}


	public void setLeft(Entry left) {
		this.left = left;
	}


	public Entry getRight() {
		return right;
	}


	public void setRight(Entry right) {
		this.right = right;
	}


	boolean isExpired() {
		return System.currentTimeMillis() > timeTolive;
	}	
	
}
