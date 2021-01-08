package com.common.domain.exchangerate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "fromCurrency",
    "toCurrency",
    "amountToConvert",
    "exchangeRate",
    "finalAmount"
})
public class ExchangeRate {

	@JsonProperty("id")
    private Integer id;
    @JsonProperty("fromCurrency")
    private String fromCurrency;
    @JsonProperty("toCurrency")
    private String toCurrency;
    @JsonProperty("amountToConvert")
    private Double amountToConvert;
    @JsonProperty("exchangeRate")
    private Double exchangeRate;
    @JsonProperty("finalAmount")
    private Double finalAmount;

    
    public ExchangeRate() {
		super();
		// TODO Auto-generated constructor stub
	}

    public ExchangeRate(String fromCurrency, String toCurrency, Double amountToConvert, Double exchangeRate,
			Double finalAmount) {
		super();
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.amountToConvert = amountToConvert;
		this.exchangeRate = exchangeRate;
		this.finalAmount = finalAmount;
	}


	public ExchangeRate(String fromCurrency, String toCurrency, Double exchangeRate) {
		super();
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.exchangeRate = exchangeRate;
	}

	@JsonProperty("id")
    public Integer getId() {
		return id;
	}

	@JsonProperty("id")
    public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("fromCurrency")
    public String getFromCurrency() {
        return fromCurrency;
    }

    @JsonProperty("fromCurrency")
    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    @JsonProperty("toCurrency")
    public String getToCurrency() {
        return toCurrency;
    }

    @JsonProperty("toCurrency")
    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    @JsonProperty("amountToConvert")
    public Double getAmountToConvert() {
        return amountToConvert;
    }

    @JsonProperty("amountToConvert")
    public void setAmountToConvert(Double amountToConvert) {
        this.amountToConvert = amountToConvert;
    }

    @JsonProperty("exchangeRate")
    public Double getExchangeRate() {
        return exchangeRate;
    }

    @JsonProperty("exchangeRate")
    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @JsonProperty("finalAmount")
    public Double getFinalAmount() {
        return finalAmount;
    }

    @JsonProperty("finalAmount")
    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

	@Override
	public String toString() {
		return String.format(
				"ExchangeRate [fromCurrency=%s, toCurrency=%s, amountToConvert=%s, exchangeRate=%s, finalAmount=%s]",
				fromCurrency, toCurrency, amountToConvert, exchangeRate, finalAmount);
	}

}
