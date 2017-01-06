# Spring Auto-Configuration Library

This library provides the following features:

* Dynamic properties access

* JMX Bean detection and configuration

[![Build Status](https://travis-ci.org/BuaBook/spring-common.svg?branch=master)](https://travis-ci.org/BuaBook/spring-common)
[![Coverage Status](https://coveralls.io/repos/github/BuaBook/spring-common/badge.svg?branch=master)](https://coveralls.io/github/BuaBook/spring-common?branch=master)

## Adding Configuration

The provided `@Configuration` classes in this library can be enabled in your application by using one of the following options:

### `@Import`

Use the `@Import` annotation on your primary configuration class to include the specific configuration class you wish to add:

```java
@Configuration
@Import(PropertyLoaderConfigurator.class)
public class AppConfig { }
```

### `@ComponentScan`

Use the `@ComponentScan` annotation on your primary configuration to include all configuration classes within the specified package:

```java
@Configuration
@ComponentScan(basePackages = { "com.your.package", "com.buabook.spring.configuration" })
public class AppConfig { }
```

## Dynamic Property Loader

Most of the time, Spring's `@Value` annotation allows you to assign configuration from a properties file into a variable and use it. 

There are some usecases where you need to dynamically generate the property name before it and then load it. The `PropertyLoader` class provides provides this. The `PropertyLoaderConfigurator` will configure this class for you. Simply autowire the property loader to use it:

```java
@Component
public class UrlResolver {

    @Autowired
    private PropertyLoader properties;

    private String getSiteUrlFor(String provider) {
        return properties.getProperty("buabook.site." + provider);
    }
}
```

## JMX Bean Detection

The `JmxConfigurator` configuration class will automatically register all classes that implement the `IExposeToJmx` interface to Spring in order for it to expose it to JMX.

The `IExposeToJmx` interface requires no methods to be implemented, it just provides a way for Spring to detect the classes. The following annotations should be used to expose data to JMX:

* `@ManagedResource` on the class
* `@ManagedAttribute` to return data to JMX
* `@ManagedOperation` to define a method as executable via JMX
* `@ManagedOperationParameter` to define each parameter required for a JMX-exposed method

```java
@Component
@ManagedResource(
    objectName="com.buabook.spring.example:name=JMX Example",
    description="Provides an example JMX class"
)
public class JmxExample implements IExposeToJmx {

    @ManagedAttribute(description="Current time in milliseconds")
    public Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    @ManagedOperation(description="Get system environment variable")
    @ManagedOperationParameter(name="Environment variable")
    public String getEnvironmentVariable(String envName) {
        return System.getenv(envName);
    }
}
```

Once a class has been correctly configured for JMX, you should lines like the following in your log:

```
2017-01-06 15:14:56.392  INFO 6792 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Bean with name 'apiInterfaceManagement' has been autodetected for JMX exposure
2017-01-06 15:14:56.396  INFO 6792 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Located managed bean 'apiInterfaceManagement': registering with JMX server as MBean [com.buabook.api_interface:name=API Interface Control]
```

### Enabling JMX

Once you have JMX MBeans configured, you need to also configure Java to listen for JMX connections so you can connect via Java Mission Control or similar application.

To do this with no password or SSL authentication, use the following Java arguments as part of your application start command:

```
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=$(hostname -I)
```

In our experience you should specify the hostname as the IP address of the host (which `hostname -I` will do on Ubuntu servers).