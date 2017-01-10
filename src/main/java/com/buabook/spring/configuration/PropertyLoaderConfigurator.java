package com.buabook.spring.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.buabook.spring.properties.PropertyLoader;

/**
 * <h3>Spring Property Loader Configurator</h3>
 * <p>This {@link Configuration} allows {@link PropertyLoader} to be autowired into any class to dynamically
 * query properties loaded by Spring.</p>
 * (c) 2017 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 5 Jan 2017
 */
@Configuration
public class PropertyLoaderConfigurator {
	private static final Logger log = LoggerFactory.getLogger(PropertyLoaderConfigurator.class);
	

	@Autowired
	private AbstractBeanFactory abstractBeanFactory;


    @Bean
    @Scope("singleton")
	public PropertyLoader propertyLoader() {
    	log.debug("Initialising new dynamic property loader with bean factory: " + abstractBeanFactory.toString());
    	
    	return new PropertyLoader(abstractBeanFactory);
    }

}
