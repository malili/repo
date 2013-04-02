package junit.service.hello.impl;

import junit.service.hello.HelloService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        HelloProvider provider = new HelloProvider();
        context.registerService(HelloService.class.getName(), provider, null);
    }

    public void stop(BundleContext context) throws Exception {
        // Cleaned by the framework
    }

}
