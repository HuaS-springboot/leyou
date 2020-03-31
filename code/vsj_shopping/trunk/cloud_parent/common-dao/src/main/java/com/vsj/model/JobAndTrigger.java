package com.vsj.model;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;
@Data
public class JobAndTrigger implements Serializable{
	private static final long serialVersionUID = -6789323379820479804L;
	private String JOBNAME;
	private String JOBGROUP;
	private String JOBCLASSNAME;
	private String TRIGGERNAME;
	private String TRIGGERGROUP;
	private BigInteger REPEATINTERVAL;
	private BigInteger TIMESTRIGGERED;
	private String CRONEXPRESSION;
	private String TIMEZONEID;
	private String TRIGGERSTATE;
	private String NAME;
	
}
