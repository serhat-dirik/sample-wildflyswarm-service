package com.redhat.demo.swarm.jaxrs;

import static org.fest.assertions.Assertions.assertThat;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.DefaultDeployment;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.spi.api.JARArchive;
import org.wildfly.swarm.swagger.SwaggerArchive;

import com.redhat.demo.swarm.AbstractIntegrationTest;
 
@RunWith(Arquillian.class)
//@DefaultDeployment
public class JAXRSSwaggerApplicationIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Deployment
    public static Archive createDeployment() throws Exception {
    	JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class, "test-app.war");
     // Add resource to deployment
    	archive.addPackage(Main.class.getPackage());
    	archive.addAllDependencies();
    
      return archive;
    }
    
    @Test
    //@RunAsClient
    public void testTime() {
        browser.navigate().to("http://localhost:8080/time");
        assertThat(browser.getPageSource()).contains("time:");
    }

    @Test
    public void testHostName() {
        browser.navigate().to("http://localhost:8080/name");
        assertThat(browser.getPageSource()).contains("hostName:");
    }
    
    @Test
    public void testIp() {
        browser.navigate().to("http://localhost:8080/ip");
        assertThat(browser.getPageSource()).contains("ip:");
    }
    
    @Test
    public void testSwagger() {
        browser.navigate().to("http://localhost:8080/swagger.json");
        assertThat(browser.getPageSource()).contains("Get the current time");
    }
}
