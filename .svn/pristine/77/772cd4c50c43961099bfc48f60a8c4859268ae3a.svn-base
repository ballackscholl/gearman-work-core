package com.smht.service.core;

import org.gearman.client.GearmanJobResult;

public class ActionJobResult implements GearmanJobResult {

	private long denominator = 0;
    private long numerator = 0;
    private byte[] results;
    private byte[] warnings;
    private byte[] exceptions;
    private byte[] handle;
    boolean succeeded = true;
    
    public ActionJobResult(byte[] results){
    	this.results = this.copyArray(results);
    }
    
    public ActionJobResult(boolean succeeded){
    	this.succeeded = succeeded;
    }
    
    public ActionJobResult(boolean succeeded, byte[] results){
    	this.results = this.copyArray(results);
    	this.succeeded = succeeded;
    }
	
	@Override
	public byte[] getResults() {
		return results;
	}

	@Override
	public byte[] getWarnings() {
		return warnings;
	}

	@Override
	public byte[] getExceptions() {
		return exceptions;
	}

	@Override
	public long getDenominator() {
		return denominator;
	}

	@Override
	public long getNumerator() {
		return numerator;
	}

	@Override
	public byte[] getJobHandle() {
		return handle;
	}

	@Override
	public boolean jobSucceeded() {
		return succeeded;
	}
	
	private byte[] copyArray(byte[] src) {
        if (src == null) {
            return new byte[0];
        }
        byte[] copy = new byte[src.length];
        System.arraycopy(src, 0, copy, 0, src.length);
        return copy;
    }

}
