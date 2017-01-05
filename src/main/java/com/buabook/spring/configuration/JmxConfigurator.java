package com.buabook.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;

import com.buabook.spring.jmx.IExposeToJmx;

/**
 * <h3>Spring JMX Bean Exposer</h3>
 * <p>This {@link Configuration} class automatically enables all configured Spring resources to be exposed via JMX
 * for administration.</p>
 * <p>To allow a Spring resource to be exposed, it <b>must</b> implement the {@link IExposeToJmx} interface.</p>
 * <br/><br/>(c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 8 Nov 2016
 */
@Configuration
@EnableMBeanExport
public class JmxConfigurator {

	@Bean
	public InterfaceBasedMBeanInfoAssembler jmxInterfaceDiscovery() {
		InterfaceBasedMBeanInfoAssembler interfaceDiscovery = new InterfaceBasedMBeanInfoAssembler();
    	interfaceDiscovery.setManagedInterfaces(new Class<?>[] { IExposeToJmx.class });
    	
    	return interfaceDiscovery;
	}
	
	@Bean
    public MBeanExporter jmxBeanExporter() {
    	MBeanExporter jmx = new MBeanExporter();
    	jmx.setAssembler(jmxInterfaceDiscovery());
    	
    	return jmx;
    }
}
