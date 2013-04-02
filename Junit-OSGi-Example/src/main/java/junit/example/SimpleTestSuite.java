package junit.example;

import junit.framework.Test;

import org.osgi.framework.BundleContext;
import org.trustie.loong.dservice.junit4osgi.OSGiTestSuite;

public class SimpleTestSuite {
    
    public static Test suite(BundleContext context) {
        OSGiTestSuite suite = new OSGiTestSuite("Hello Service Test Suite", context);
        suite.addTestSuite(SimpleTestCase.class);
        return suite;
    }

}
