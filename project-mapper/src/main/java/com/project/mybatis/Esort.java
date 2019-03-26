package com.project.mybatis;

public enum Esort {
	ASC("asc"),DESC("desc");
	
	private String s;
	
	private Esort(String s) {
		this.s = s;
	}
	@Override
	public String toString() {
		return s;
	}
}
