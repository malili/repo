package junit.example;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * <p>测试suite。<p>
 * 
 * 创建日期 2013-4-3<br>
 * @author $lili$<br>
 * @version $Revision$ $Date$
 * @since 3.0.0
 */
public class SimpleTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite("Money Simple Test Suite");
        suite.addTestSuite(SimpleTestCase.class);
        return suite;
    }
}
