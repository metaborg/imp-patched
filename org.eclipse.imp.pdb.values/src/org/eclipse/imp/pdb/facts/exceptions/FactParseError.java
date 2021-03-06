package org.eclipse.imp.pdb.facts.exceptions;

public class FactParseError extends RuntimeException {
	private static final long serialVersionUID = 8208492896666238438L;
	private int offset = -1;
	private Throwable cause;

	public FactParseError(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}
	
	public FactParseError(String message, int offset) {
		super(message);
		this.offset = offset;
	}
	
	public FactParseError(String message, int offset, Throwable cause) {
		super(message + " at offset " + offset, cause);
		this.offset = offset;
		this.cause = cause;
	}

	public boolean hasCause() {
		return cause != null;
	}
	
	public Throwable getCause() {
		return cause;
	}
	
	public boolean hasOffset() {
		return offset != -1;
	}
	public int getOffset() {
		return offset;
	}
}
