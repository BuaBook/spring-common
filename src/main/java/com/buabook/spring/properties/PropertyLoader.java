package com.buabook.spring.properties;

import org.springframework.beans.factory.support.AbstractBeanFactory;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * <h3>Spring Properties Programmatic Access</h3>
 * <p>Provides programmatic access to the Spring properties defined in the application.</p>
 * <p>NOTE: This class caches results <i>indefinitely</i> once queried from the property file.</p>
 * (c) 2015 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 6 Oct 2015
 */
public class PropertyLoader {
	private final AbstractBeanFactory beanFactory;
	
	private final LoadingCache<String, String> cache;

	
	/** @see PropertyLoader */
	public PropertyLoader(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		
		this.cache = CacheBuilder.newBuilder().build(getCacheLoader());
	}
	
	/** 
	 * <p>NOTE: It is recommended in most cases to use Spring's <code>@Value</code> annotation to load
	 * property configuration. This method is supplied in the rare cases that you need to programmatically 
	 * define the key.</p> 
	 * @return The specified property value
	 */
	public String getProperty(String property) {
		if(Strings.isNullOrEmpty(property))
			return null;
		
		return cache.getUnchecked(property);
	}
	
	
	private String getPropertyFromSpring(String property) {
		String propertyName = "${" + property + "}";
		String propertyValue = "";
		
		try {
			propertyValue = beanFactory.resolveEmbeddedValue(propertyName);
		} catch(IllegalArgumentException e) {
			// If the property doesn't exist, an IllegalArgumentException will be thrown. We'll catch it and just return empty string
		}
		
		return propertyValue;
	}
	
	
	private CacheLoader<String, String> getCacheLoader() {
		return new CacheLoader<String, String>() {
			@Override
			public String load(String key) throws Exception {
				return getPropertyFromSpring(key);
			}
		};
	}
}