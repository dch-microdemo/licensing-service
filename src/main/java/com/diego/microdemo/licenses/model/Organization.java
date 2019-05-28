package com.diego.microdemo.licenses.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Organization implements Serializable {

	private static final long serialVersionUID = 1L;
	String id;
	String name;
	String contactName;
	String contactEmail;
	String contactPhone;

}