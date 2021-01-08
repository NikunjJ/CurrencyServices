package com.currencyexchange.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.common.domain.exchangerate.ExchangeRate;

@Repository
public class CurrencyExchangeJDBCRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	public List<ExchangeRate> getAllExchangeRate() {

		String query = "select * from EXCHANGE_RATE_MASTER order by id";

		List<ExchangeRate> exchangeRateList = jdbcTemplate.query(query, new ExchangeRateRowMapper());

		return exchangeRateList;

	}

	public ExchangeRate getExchangeRate(ExchangeRate rate) {

		String query = "select * from EXCHANGE_RATE_MASTER where from_currency=? and to_currency=?";

		/*
		 * Object[] params = new Object[2]; params[0] = rate.getFromCurrency();
		 * params[1] = rate.getToCurrency();
		 */

		Object[] params = { rate.getFromCurrency(), rate.getToCurrency() };

		ExchangeRate exchangeRate = jdbcTemplate.queryForObject(query, params, new ExchangeRateRowMapper());

		return exchangeRate;

	}
	
	public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {

		String query = "select * from EXCHANGE_RATE_MASTER where from_currency=? and to_currency=?";

		Object[] params = {fromCurrency, toCurrency};

		ExchangeRate exchangeRate = jdbcTemplate.queryForObject(query, params, new ExchangeRateRowMapper());

		return exchangeRate;

	}

	public int save(ExchangeRate rate) {

		String query = "insert into EXCHANGE_RATE_MASTER(FROM_CURRENCY, TO_CURRENCY,EXCHANGE_RATE) values(?,?,?);";

		/*
		 * Object[] params = new Object[2]; params[0] = rate.getFromCurrency();
		 * params[1] = rate.getToCurrency();
		 */

		Object[] params = { rate.getFromCurrency(), rate.getToCurrency(), rate.getExchangeRate() };

		int recordsCount = jdbcTemplate.update(query, params);

		return recordsCount;

	}

	// This method will save data but along with it will fetch Primary Key of record getting saved
//	@Transactional(propagation = Propagation.REQUIRED)
	public ExchangeRate saveExchangeRate(ExchangeRate rate) {

		String query = "insert into EXCHANGE_RATE_MASTER(FROM_CURRENCY, TO_CURRENCY,EXCHANGE_RATE) values(?,?,?);";

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				PreparedStatement ps = con.prepareStatement(query, new String[] { "ID" });
				ps.setObject(1, rate.getFromCurrency());
				ps.setObject(2, rate.getToCurrency());
				ps.setObject(3, rate.getExchangeRate());

				return ps;
			}
		}, generatedKeyHolder);

		Integer id = generatedKeyHolder.getKey().intValue();

		rate.setId(id);		
		
		return rate;
	}

	
	// This method will take multiple exchange rate and save to database
	// BATCH UPDATE / INSERT / DELETE
	// Declarative Transaction
	/*@Transactional
	public List<ExchangeRate> saveAllExchangeRate(List<ExchangeRate> rate) {

		String query = "insert into EXCHANGE_RATE_MASTER(FROM_CURRENCY, TO_CURRENCY,EXCHANGE_RATE) values(?,?,?);";

		// This is Batch Insert as more than 1 Customer Details present
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {

				ps.setObject(1, rate.get(i).getFromCurrency());
				ps.setObject(2, rate.get(i).getToCurrency());
				ps.setObject(3, rate.get(i).getExchangeRate());
			}

			public int getBatchSize() {
				return rate.size();
			}
			
		});

		//As batch update don't have provision to get Key Generator Holder so customize code need to write
		List<ExchangeRate> responseExchangeRate = new ArrayList<ExchangeRate>();
		for(ExchangeRate r : rate)
		{
			ExchangeRate exchangeRate = getExchangeRate(r);
			responseExchangeRate.add(exchangeRate);
		}
		
		return responseExchangeRate;
	}*/
	
	//Programmatic Transaction handling
	public List<ExchangeRate> saveAllExchangeRate(List<ExchangeRate> rate) {

		//Transaction Handling
		TransactionDefinition def = new DefaultTransactionDefinition();

		TransactionStatus tx = null;
		
		String query = "insert into EXCHANGE_RATE_MASTER(FROM_CURRENCY, TO_CURRENCY,EXCHANGE_RATE) values(?,?,?);";
		
		List<ExchangeRate> responseExchangeRate = null;
		
		try
		{
			 tx = platformTransactionManager.getTransaction(def);

			// This is Batch Insert as more than 1 Customer Details present
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
	
				public void setValues(PreparedStatement ps, int i) throws SQLException {
	
					ps.setObject(1, rate.get(i).getFromCurrency());
					ps.setObject(2, rate.get(i).getToCurrency());
					ps.setObject(3, rate.get(i).getExchangeRate());
				}
	
				public int getBatchSize() {
					return rate.size();
				}
				
			});
			
			//As batch update don't have provision to get Key Generator Holder so customize code need to write
			responseExchangeRate = new ArrayList<ExchangeRate>();
			for(ExchangeRate r : rate)
			{
				ExchangeRate exchangeRate = getExchangeRate(r);
				responseExchangeRate.add(exchangeRate);
			}

			//@ end - Commit the transaction
			platformTransactionManager.commit(tx);
		}
		catch(Exception e)
		{
			//@ in case of error - Rollback the transaction
			platformTransactionManager.rollback(tx);
			e.printStackTrace();
		}
		
		return responseExchangeRate;
	}
		
}
