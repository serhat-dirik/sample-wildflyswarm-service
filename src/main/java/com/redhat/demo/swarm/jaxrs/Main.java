package com.redhat.demo.swarm.jaxrs;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.swagger.SwaggerArchive;


public class Main {

    public static void main(String[] args) throws Exception {

        // Instantiate the container
        Swarm swarm = new Swarm(args);
        SwaggerArchive archive = ShrinkWrap.create(SwaggerArchive.class, "swagger-app.war");
     // Now we can use the SwaggerArchive to fully customize the JSON output
        archive.setVersion("1.0"); // our API version
        archive.setContact("serhat@redhat.com");  // set contact info
        archive.setLicense("ApacheV2"); // set license
        // Finally tell swagger where our resources are
        archive.setResourcePackages("com.redhat.demo.swarm.jaxrs");
        
        JAXRSArchive deployment = archive.as(JAXRSArchive.class);
        //WARArchive deployment = archive.as(WARArchive.class);

        
     // Add resource to deployment
        deployment.addPackage(Main.class.getPackage());
        deployment.addAllDependencies();
        swarm
                .fraction(LoggingFraction.createDefaultLoggingFraction())
                .start()
                .deploy(deployment);
    }
}
