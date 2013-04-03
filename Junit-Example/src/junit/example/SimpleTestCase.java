package junit.example;

import junit.framework.TestCase;
import junit.money.Money;

/**
 * 
 * <p>测试类，继承自TestCase。<p>
 * 
 * 创建日期 2013-4-3<br>
 * @author $lili$<br>
 * @version $Revision$ $Date$
 * @since 3.0.0
 */
public class SimpleTestCase extends TestCase {
    private Money f12CHF;
    private Money f14CHF;

    public void setUp() {
        f12CHF = new Money(12, "CHF");
        f14CHF = new Money(14, "CHF");
    }

    public void testEquals() {
        assertTrue(!f12CHF.equals(null));
        assertEquals(f12CHF, f12CHF);
        assertEquals(f12CHF, new Money(12, "CHF"));
        assertTrue(!f12CHF.equals(f14CHF));
    }

    public void testSimpleAdd() {
        Money expected = new Money(26, "CHF");
        Money result = f12CHF.add(f14CHF);
        assertTrue(expected.equals(result));
    }
}
