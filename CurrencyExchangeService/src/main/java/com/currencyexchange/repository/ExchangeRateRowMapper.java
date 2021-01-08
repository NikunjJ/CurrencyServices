package com.currencyexchange.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.domain.exchangerate.ExchangeRate;

public class ExchangeRateRowMapper implements RowMapper<ExchangeRate>{

	@Override
	public ExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		ExchangeRate rate = new ExchangeRate();
		
		rate.setId(rs.getInt("id"));
		rate.setFromCurrency(rs.getString("from_currency"));
		rate.setToCurrency(rs.getString("to_currency"));
		rate.setExchangeRate(rs.getDouble("exchange_rate"));
		
		return rate;
	}

	
}
