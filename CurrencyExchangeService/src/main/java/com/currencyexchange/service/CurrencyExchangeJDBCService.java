package com.currencyexchange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.common.domain.auditlog.AuditLog;
import com.common.domain.exchangerate.ExchangeRate;
import com.currencyexchange.exceptions.DataNotFoundException;
import com.currencyexchange.repository.AuditLogRepository;
import com.currencyexchange.repository.CurrencyExchangeJDBCRepository;

@Service
public class CurrencyExchangeJDBCService {

	@Autowired
	private CurrencyExchangeJDBCRepository repository;
	
	@Autowired
	private AuditLogRepository auditLogRepo;

	@Autowired
	private PlatformTransactionManager platformTransactionManager;
	
	public List<ExchangeRate> getAllExchangeRate(){
		
		 List<ExchangeRate> allExchangeRate = repository.getAllExchangeRate();
		 
		 return allExchangeRate;
	}
	
	public ExchangeRate getExchangeRate(ExchangeRate rate){
		
		ExchangeRate exchangeRate = null;
		
		try
		{
			exchangeRate = repository.getExchangeRate(rate);
		}
		catch(Exception e)
		{
			//throw new RuntimeException("Exchange rate not found for " + rate.getFromCurrency()+ " -> " + rate.getToCurrency());
			throw new DataNotFoundException("DATANOTFOUND", "Exchange rate not found for " + rate.getFromCurrency()+ " -> " + rate.getToCurrency());
		}
		
		return exchangeRate;
	}
	
	public int save(ExchangeRate rate){
		
		int recordCount = repository.save(rate);
		
		return recordCount;
	}
	
	public ExchangeRate saveExchangeRate(ExchangeRate rate){
		
		ExchangeRate exchRate = repository.saveExchangeRate(rate);
		
		return exchRate;
	}
	
	public List<ExchangeRate> saveAllExchangeRate(List<ExchangeRate> rates){
		
		List<ExchangeRate> exchRate = repository.saveAllExchangeRate(rates);
		
		return exchRate;
	}
	
	//Declarative Transaction Handling
	@Transactional
	public ExchangeRate saveExchangeRateWithDCTX(ExchangeRate rate){
		
		ExchangeRate exchRate = null;
		
		try
		{
			exchRate = repository.saveExchangeRate(rate);
			System.out.println("Saved exchange rate master....");
		}
		catch(Exception e)
		{
			System.out.println("Exception in save ... "+ e.getMessage());
		}

		//save audit log
		try
		{
			AuditLog log = new AuditLog("EXCHANGE_RATE_MASTER","ADD", rate.toString());
			auditLogRepo.save(log);
			System.out.println("Saved audit log....");
		}
		catch(Exception e)
		{
			System.out.println("Exception in auditlog ... "+ e.getMessage());
		}
		
		if(exchRate==null) {
			exchRate = rate;
		}
		
		return exchRate;
	}
	
	//Programatic Transaction Handling
	public ExchangeRate saveExchangeRateWithPRTX(ExchangeRate rate){
		
		//Transaction Handling
		TransactionDefinition def = new DefaultTransactionDefinition();

		TransactionStatus tx = null;
		
		ExchangeRate exchRate =  null;
		
		try
		{
			tx = platformTransactionManager.getTransaction(def);

			//save audit log
			AuditLog log = new AuditLog("EXCHANGE_RATE_MASTER","ADD", rate.toString());
			auditLogRepo.save(log);
			
			exchRate =  repository.saveExchangeRate(rate);

			//commit
			platformTransactionManager.commit(tx);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//commit
			platformTransactionManager.rollback(tx);
		}
		
		return exchRate;
	}
		
	
}
