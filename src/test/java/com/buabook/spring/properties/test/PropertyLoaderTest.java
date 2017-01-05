package com.buabook.spring.properties.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.buabook.spring.properties.PropertyLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class PropertyLoaderTest {

	@Autowired
	private PropertyLoader propertyLoader;

	
	@Test
	public void testGetPropertyReturnsNullIfNullOrEmptyInput() {
		assertThat(propertyLoader.getProperty(null), is(nullValue()));
		assertThat(propertyLoader.getProperty(""), is(nullValue()));
	}
	
	@Test
	public void testGetPropertyReturnsValueIfSpecified() {
		assertThat(propertyLoader.getProperty("property-1"), is(equalTo("a String")));
		assertThat(propertyLoader.getProperty("property-2"), is(equalTo("1")));
		assertThat(propertyLoader.getProperty("property-3"), is(equalTo("")));
	}
	
	@Test
	public void testGetPropertyReturnsEmptyStringForUnknownProperties() {
		assertThat(propertyLoader.getProperty("unknown-property"), is(equalTo("")));
	}
	
	
	@Configuration
    static class TestConfiguration {
		
		@Autowired
		public AbstractBeanFactory abstractBeanFactory;

        @Bean
        public static PropertySourcesPlaceholderConfigurer properties() {
            final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            
            Properties properties = new Properties();
            properties.setProperty("property-1", "a String");
            properties.setProperty("property-2", "1");
            properties.setProperty("property-3", "");
            
            pspc.setProperties(properties);
            
            return pspc;
        }
        
        @Bean
        public PropertyLoader propertyLoader() {
        	return new PropertyLoader(abstractBeanFactory);
        }
	}
}
