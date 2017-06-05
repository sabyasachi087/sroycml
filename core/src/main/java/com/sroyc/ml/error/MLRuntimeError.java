package com.sroyc.ml.error;

public class MLRuntimeError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7655439323539682562L;

	private ErrorCode error;
	private String details;

	public MLRuntimeError(ErrorCode code) {
		super(code.name() + " : " + code.value());
	}

	public MLRuntimeError(ErrorCode code, String details) {
		this(code);
		this.details = details;
	}

	@Override
	public String getMessage() {
		return this.error.value();
	}

	public String getDetails() {
		return this.details;
	}
}
