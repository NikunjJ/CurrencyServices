package com.currencyexchange.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.domain.exchangerate.ExchangeRate;

@RestController
@RequestMapping("CurrencyExchangeController")
public class CurrencyExchangeController {

	private List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();
	
	@PostConstruct
	public void init() 
	{
		ExchangeRate er1 = new ExchangeRate("INR","USD",1.5);
		ExchangeRate er2 = new ExchangeRate("INR","GBP",10.2);
		ExchangeRate er3 = new ExchangeRate("INR","AUD",60.0);
		
		exchangeRateList.add(er1);
		exchangeRateList.add(er2);
		exchangeRateList.add(er3);
	
		System.out.println(exchangeRateList);
	}
	
	@GetMapping("/getAllExchangeRates")
	public List<ExchangeRate> getAllExchangeRates(){
		
		return exchangeRateList;
	}
	
	@GetMapping("/isConnected")
	public String isConnected() {
		
		return "Currency Exchange Service is up and running..";
	}
	
	//Get - method with parameter as Path Variable
	@GetMapping("/getExchangeRate/{fromCcy}/{toCcy}")
	public Double getExchangeRate(@PathVariable("fromCcy") String fromCurrency,@PathVariable("toCcy") String toCurrency)
	{
		System.out.println(fromCurrency+ " "+ toCurrency);
		
		Double exchangeRateValue = 0.0;
		
		for(ExchangeRate e : exchangeRateList)
		{
			if(e.getFromCurrency().equals(fromCurrency) && e.getToCurrency().equals(toCurrency)) {
				
				exchangeRateValue = e.getExchangeRate();
				
				break;
			}
		}
		
		return exchangeRateValue;
	}
	
	//Get - method with parameter as Path Variable
	@GetMapping("/getExchangeRateRQP")
	public Double getExchangeRateWithRQP(@RequestParam("fromCcy") String fromCurrency,@RequestParam("toCcy") String toCurrency)
	{
		System.out.println(fromCurrency+ " "+ toCurrency);
		
		Double exchangeRateValue = 0.0;
		
		for(ExchangeRate e : exchangeRateList)
		{
			if(e.getFromCurrency().equals(fromCurrency) && e.getToCurrency().equals(toCurrency)) {
				
				exchangeRateValue = e.getExchangeRate();
				
				break;
			}
		}
		
		return exchangeRateValue;
	}
	
	//Get - method with parameter as Path Variable
	@GetMapping("/getExchangeRateRQB")
	public Double getExchangeRateWithRQB(@RequestBody ExchangeRate rate)
	{
		System.out.println(rate);
		
		Double exchangeRateValue = 0.0;
		
		for(ExchangeRate e : exchangeRateList)
		{
			if(e.getFromCurrency().equals(rate.getFromCurrency()) && e.getToCurrency().equals(rate.getToCurrency())) {
				
				exchangeRateValue = e.getExchangeRate();
				
				break;
			}
		}
		
		return exchangeRateValue;
	}
	
	@PostMapping("/getExchangeRateValue")
	public ExchangeRate getExchangeRateValue(@RequestBody ExchangeRate rate) {
	
		ExchangeRate responseRate = null;
		
		for(ExchangeRate e : exchangeRateList)
		{
			if(e.getFromCurrency().equals(rate.getFromCurrency()) && e.getToCurrency().equals(rate.getToCurrency())) {
				
				Double finalAmount = rate.getAmountToConvert() * e.getExchangeRate();
				
				responseRate = e;
				
				responseRate.setFinalAmount(finalAmount);
				
				break;
			}
		}
	
		return responseRate;
	}
	
	//Save
	@PostMapping("/save")
	public ExchangeRate save(@RequestBody ExchangeRate rate) {
		
		exchangeRateList.add(rate);
		
		return rate;
	}
}
