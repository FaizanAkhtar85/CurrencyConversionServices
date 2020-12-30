package com.example.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.limitsservice.bean.Configuration;
import com.example.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.HystrixEventType.ThreadPool;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class LimitsConfigurationController {
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")	
	public LimitConfiguration retrieveLimitsFromConfiguration() {
		//return new LimitConfiguration(1000, 1);
		return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration",
	threadPoolKey = "limitInfoPool",
	threadPoolProperties = {
			@HystrixProperty(name="coreSize",value="20"),
			@HystrixProperty(name = "maxQueueSize",value="10")
	})
	public LimitConfiguration retrieveConfiguration() {
		throw new RuntimeException("NOt Available");
	}
	
	public LimitConfiguration fallbackRetrieveConfiguration() {
		return new LimitConfiguration(99999, 99);
	}
}
