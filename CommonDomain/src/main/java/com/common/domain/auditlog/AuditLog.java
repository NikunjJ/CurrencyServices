package com.common.domain.auditlog;

public class AuditLog {

	private Integer id;
	
	private String tableName;
	
	private String operation;
	
	private String data;
	
	public AuditLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuditLog(String tableName, String operation, String data) {
		super();
		this.tableName = tableName;
		this.operation = operation;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
