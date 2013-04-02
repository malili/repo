package junit.example;

import junit.service.hello.HelloService;

import org.osgi.framework.ServiceReference;
import org.trustie.loong.dservice.junit4osgi.OSGiTestCase;

public class SimpleTestCase extends OSGiTestCase {
    
    public SimpleTestCase(String name) {
        super(name);
    }

    public void testHelloAvailability() {
        ServiceReference ref = context.getServiceReference(HelloService.class.getName());
        assertNotNull("Assert Availability", ref);
    }
    
    public void testHelloAvailability2() {
        ServiceReference ref = getServiceReference(HelloService.class.getName(), null);
        assertNotNull("Assert Availability", ref);
    }
    
    public void testHelloMessage() {
        ServiceReference ref = context.getServiceReference(HelloService.class.getName());
        assertNotNull("Assert Availability", ref);
        HelloService hs = (HelloService) context.getService(ref);
        String message = hs.getHelloMessage();
        assertNotNull("Check the message existence", message);
        assertEquals("Check the message", "hello", message);
    }
    
    public void testHelloMessage2() {
    	assertTrue("Check availability of the service", 
    	    isServiceAvailable(HelloService.class.getName()));
        HelloService hs = (HelloService) getServiceObject(HelloService.class.getName(), null);
        String message = hs.getHelloMessage();
        assertNotNull("Check the message existence", message);
        assertEquals("Check the message", "hello", message);
    }
}

