package com.currencyexchange.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.common.domain.exchangerate.ExchangeRate;
import com.common.domain.framework.ApiError;
import com.common.domain.framework.ApiResponse;
import com.currencyexchange.repository.CurrencyExchangeJDBCRepository;

@Service
public class CurrencyExchangeErrorHandlerService {

	@Autowired
	private CurrencyExchangeJDBCRepository repository;

	@Autowired
    private MessageSource messageSource;
    
	public ApiResponse getAllExchangeRate(){
		
		 List<ExchangeRate> allExchangeRate = null;
		
		 List<ApiError> errorList = new LinkedList<ApiError>();
		 
		 try
		 {
			 allExchangeRate = repository.getAllExchangeRate();
			 
			 if(allExchangeRate==null || allExchangeRate.isEmpty())
			 {
//				 ApiError er = new ApiError("BUSSERROR01",false,"No record found for Exchange Rates");
				 
				 String errorMessage = messageSource.getMessage("BUSSERROR01", null, LocaleContextHolder.getLocale());
				 
				 ApiError er = new ApiError("BUSSERROR01",false,errorMessage);
				 errorList.add(er);
			 }
		 }
		 catch(EmptyResultDataAccessException e)
		 {
//				 ApiError er = new ApiError("BUSSERROR01",false,"No record found for Exchange Rates");
			 
			 	 String errorMessage = messageSource.getMessage("BUSSERROR01", null, LocaleContextHolder.getLocale());
			 	 ApiError er = new ApiError("BUSSERROR01",false,errorMessage);
			 	
				 errorList.add(er);
		 }
		 catch(Exception e)
		 {
//			 ApiError er = new ApiError("TECHERROR01",true,"Error while fetching data for Exchange Rates");
			 
			 String errorMessage = messageSource.getMessage("TECHERROR01", null, LocaleContextHolder.getLocale());
			 ApiError er = new ApiError("TECHERROR01",false,errorMessage);
			 errorList.add(er);		
		 }
		 
		 //create Api Response Object
		 ApiResponse response = new ApiResponse(allExchangeRate, errorList);
		 		 
		 return response;
	}

	public ApiResponse getExchangeRate(String fromCurrency, String toCurrency)
	{

		List<ApiError> errorList = new LinkedList<ApiError>();
		 
		ExchangeRate exchangeRate = null;
		
		try
		{
			exchangeRate = repository.getExchangeRate(fromCurrency, toCurrency);
			
			if(exchangeRate==null)
			 {
//				 ApiError er = new ApiError("BUSSERROR02",false,"No record found for Exchange Rate for From Currency: "+ fromCurrency + " and To Currency: " + toCurrency);
				
				 String errorMessage = messageSource.getMessage("BUSSERROR02", new Object[] {fromCurrency, toCurrency}, LocaleContextHolder.getLocale());
				 ApiError er = new ApiError("BUSSERROR02",false,errorMessage);

				 errorList.add(er);
			 }
		}
		catch(EmptyResultDataAccessException e)
		{
//			 ApiError er = new ApiError("BUSSERROR02",false,"No record found for Exchange Rate for From Currency: "+ fromCurrency + " and To Currency: " + toCurrency);
			
			 String errorMessage = messageSource.getMessage("BUSSERROR02", new Object[] {fromCurrency, toCurrency}, LocaleContextHolder.getLocale());
			 ApiError er = new ApiError("BUSSERROR02",false,errorMessage);
			 errorList.add(er);
		}
		catch(Exception e)
		{
			 e.printStackTrace();
//			 ApiError er = new ApiError("TECHERROR02",true,"Error while fetching exchange rate for From Currency: "+ fromCurrency + " and To Currency: " + toCurrency);
			 
			 String errorMessage = messageSource.getMessage("TECHERROR02", new Object[] {fromCurrency, toCurrency}, LocaleContextHolder.getLocale());
			 ApiError er = new ApiError("TECHERROR02",false,errorMessage);
			 
			 errorList.add(er);
		}

		 //create Api Response Object
		 ApiResponse response = new ApiResponse(exchangeRate, errorList);
		
		return response;
	}
}
