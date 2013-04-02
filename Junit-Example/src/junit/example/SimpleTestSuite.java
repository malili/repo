package junit.example;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SimpleTestSuite {
    
    public static Test suite() {
        TestSuite suite = new TestSuite("Money Simple Test Suite");
        suite.addTestSuite(SimpleTestCase.class);
        return suite;
    }

}
