package com.currencyexchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.domain.exchangerate.ExchangeRate;
import com.currencyexchange.service.CurrencyExchangeJDBCService;

@RestController
@RequestMapping("CurrencyExchangeJDBCController")
public class CurrencyExchangeJDBCController {

	@Autowired
	private CurrencyExchangeJDBCService service;
	
	@GetMapping("/getAllExchangeRate")
	public List<ExchangeRate> getAllExchangeRate(){
		
		return service.getAllExchangeRate();
	}
	
	@PostMapping("/getExchangeRate")
	public ExchangeRate getExchangeRate(@RequestBody ExchangeRate rate){
		
		return service.getExchangeRate(rate);
	}
	
	@PostMapping("/save")
	public int save(@RequestBody ExchangeRate rate){
		
		return service.save(rate);
	}
	
	@PostMapping("/saveExchangeRate")
	public ExchangeRate saveExchangeRate(@RequestBody ExchangeRate rate){
		
		return service.saveExchangeRate(rate);
	}
	
	@PostMapping("/saveAllExchangeRate")
	public List<ExchangeRate> saveAllExchangeRate(@RequestBody List<ExchangeRate> rates){
		
		return service.saveAllExchangeRate(rates);
	}
	
	@PostMapping("/saveExchangeRateWithDCTX")
	public ExchangeRate saveExchangeRateWithDCTX(@RequestBody ExchangeRate rate){
	
			return service.saveExchangeRateWithDCTX(rate);
	}
	
	@PostMapping("/saveExchangeRateWithPRTX")
	public ExchangeRate saveExchangeRateWithPRTX(@RequestBody ExchangeRate rate){
	
			return service.saveExchangeRateWithPRTX(rate);
	}
	
}
