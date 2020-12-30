package com.example.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.currencyconversionservice.bean.CurrencyConversionBean;
/**
 * 
 * 
 * 
 * @author Faizan
 * Used for calling other microservices 
 *
 */
//@FeignClient(name="currency-exchange-service",url="localhost:8000")
//@FeignClient(name="currency-exchange-service") no need to add url just add it in application.properties file all instance url call
@FeignClient(name="netflix-zuul-api-gateway-server") //used to bypass request through zuul api gateway not direct call ----used for distributing load balancing if one service is down call to others if all working then switch from one to other.
@RibbonClient(name="currency-exchange-service") // load balancing to call different instance of exchange services
public interface CurrencyExchangeServiceProxy {
	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@GetMapping("currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from,@PathVariable("to") String to);
}
