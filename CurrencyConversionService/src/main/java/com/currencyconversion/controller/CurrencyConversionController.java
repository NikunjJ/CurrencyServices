package com.currencyconversion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import com.common.domain.exchangerate.ExchangeRate;

@RestController
@RequestMapping("CurrencyConversionController")
public class CurrencyConversionController {

	@Autowired
	private Environment env;
	
	@GetMapping("/getCurrencyConversionAmount/{fromCcy}/{toCcy}/{amountToConvert}")
	public Double getCurrencyConversionAmount(@PathVariable String fromCcy,@PathVariable String toCcy,@PathVariable Double amountToConvert)
	{
		Double convertedAmount = 0.0;
		
		//String url = "http://localhost:8081/CurrencyExchangeService/CurrencyExchangeController/getExchangeRate/"+fromCcy+"/"+toCcy;
		String url = env.getProperty("currency.exchange.url")+ "/getExchangeRate/"+fromCcy+"/"+toCcy;
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<Double> response = rt.getForEntity(url, Double.class);
		
		HttpStatus statusCode = response.getStatusCode();
		int statusCodeValue = response.getStatusCodeValue();
		Double exchangeRate = response.getBody();
		
		convertedAmount = amountToConvert * exchangeRate;
		
		System.out.println(statusCode.name()+ " "+statusCode.value()+" "+statusCode.isError());
		
		return convertedAmount;
	}

	
	@GetMapping("/getCurrencyConversionAmount")
	public ExchangeRate getConvertedAmount(@RequestBody ExchangeRate rate)
	{
		ExchangeRate responseExchangeRate = new ExchangeRate();
		
		//String url = "http://localhost:8081/CurrencyExchangeService/CurrencyExchangeController/getExchangeRateValue";
		String url = env.getProperty("currency.exchange.url")+ "/getExchangeRateValue";
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<ExchangeRate> response = rt.postForEntity(url, rate, ExchangeRate.class);
		
		HttpStatus statusCode = response.getStatusCode();
		int statusCodeValue = response.getStatusCodeValue();
		ExchangeRate exr = response.getBody();
		
		System.out.println(statusCode.name()+ " "+statusCode.value()+" "+statusCode.isError());
		
		responseExchangeRate.setFromCurrency(exr.getFromCurrency());
		responseExchangeRate.setToCurrency(exr.getToCurrency());
		responseExchangeRate.setExchangeRate(exr.getExchangeRate());
		responseExchangeRate.setAmountToConvert(rate.getAmountToConvert());
		responseExchangeRate.setFinalAmount(rate.getAmountToConvert() * exr.getExchangeRate());
		
		return responseExchangeRate;
	}
	
	@GetMapping("/getCurrencyConversionAmountErrorHandling")
	public ExchangeRate getConvertedAmountErrorHandling(@RequestBody ExchangeRate rate)
	{
		ExchangeRate responseExchangeRate = new ExchangeRate();
		
		String url = "http://localhost:8081/CurrencyExchangeService/CurrencyExchangeJDBCController/getExchangeRate";
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<ExchangeRate> response = null;
		
		try
		{
			response = rt.postForEntity(url, rate, ExchangeRate.class);
			
			ExchangeRate exr = response.getBody();
			responseExchangeRate.setFromCurrency(exr.getFromCurrency());
			responseExchangeRate.setToCurrency(exr.getToCurrency());
			responseExchangeRate.setExchangeRate(exr.getExchangeRate());
			responseExchangeRate.setAmountToConvert(rate.getAmountToConvert());
			responseExchangeRate.setFinalAmount(rate.getAmountToConvert() * exr.getExchangeRate());
		}
		catch(HttpClientErrorException e)
		{
			System.out.println("In HttpClientErrorException catch --------" + e.getStatusCode()+ " " + e.getMessage());
		
			System.out.println("Local : "+e.getLocalizedMessage());
			System.out.println("Message :"+e.getMessage());
			System.out.println("Response :"+e.getResponseBodyAsString());
			System.out.println("Status Text:"+e.getStatusText());
						
			throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
		}
		catch(HttpServerErrorException e)
		{
			System.out.println("In HttpServerErrorException catch --------" + e.getStatusCode()+ " " + e.getMessage());
			throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
		}
		catch(Exception e)
		{
			System.out.println("In Generic Exception Handler...");
			throw e;
		}
		
		return responseExchangeRate;
	}

}
