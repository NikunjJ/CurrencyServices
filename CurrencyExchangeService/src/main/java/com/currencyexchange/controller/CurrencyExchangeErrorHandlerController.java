package com.currencyexchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.domain.exchangerate.ExchangeRate;
import com.common.domain.framework.ApiResponse;
import com.currencyexchange.service.CurrencyExchangeErrorHandlerService;

@RestController
@RequestMapping("ErrorHandler")
public class CurrencyExchangeErrorHandlerController {

	@Autowired
	private CurrencyExchangeErrorHandlerService errorHandlerService;
	
	@GetMapping("/getAllExchangeRate")
	public ApiResponse getAllExchangeRate(){
		
		return errorHandlerService.getAllExchangeRate();
	}

	@GetMapping("/getExchangeRate/{fromCcy}/{toCcy}")
	public ApiResponse getExchangeRate(@PathVariable("fromCcy") String fromCurrency,@PathVariable("toCcy") String toCurrency)
	{
		return errorHandlerService.getExchangeRate(fromCurrency, toCurrency);
	}
	
}
