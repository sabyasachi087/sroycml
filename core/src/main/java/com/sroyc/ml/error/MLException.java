package com.sroyc.ml.error;

/** ML Base Exception class */
public class MLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3634764899329685284L;
	private ErrorCode code;
	private String details;

	public MLException(ErrorCode code) {
		super(code.name() + " : " + code.value());
	}

	public MLException(ErrorCode code, String details) {
		this(code);
		this.details = details;
	}

	@Override
	public String getMessage() {
		return this.code.value();
	}

	public String getDetails() {
		return this.details;
	}

}
