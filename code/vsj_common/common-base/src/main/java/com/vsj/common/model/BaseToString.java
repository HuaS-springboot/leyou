 package com.vsj.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseToString {
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
