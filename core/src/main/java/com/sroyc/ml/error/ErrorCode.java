package com.sroyc.ml.error;

public enum ErrorCode {

	ML001("Machine Learning Global Exception");

	private String value;

	ErrorCode(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
