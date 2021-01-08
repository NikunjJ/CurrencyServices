package com.currencyexchange.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.domain.auditlog.AuditLog;

@Repository
public class AuditLogRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int save(AuditLog log) {

		String query = "insert into AUDITLOG(TABLENAME, OPERATION,DATA) values(?,?,?);";

		Object[] params = { log.getTableName(), log.getOperation(), log.getData() };

		int recordsCount = jdbcTemplate.update(query, params);

		return recordsCount;

	}
}
