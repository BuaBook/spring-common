package com.buabook.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

	@Autowired
	private AbstractBeanFactory abstractBeanFactory;


    @Bean
	public PropertyLoader propertyLoader() {
    	return new PropertyLoader(abstractBeanFactory);
    }

}
