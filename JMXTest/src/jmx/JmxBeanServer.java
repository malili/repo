package jmx;

import java.rmi.registry.LocateRegistry;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class JmxBeanServer {
    /**
     * localhost:9998 -Dcom.sun.management.jmxremote.port=9998 -Dcom.sun.management.jmxremote.ssl=false
     * -Dcom.sun.management.jmxremote.authenticate=false
     */
    public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException,
            NotCompliantMBeanException, MalformedObjectNameException, NullPointerException, InterruptedException,
            IntrospectionException, InstanceNotFoundException, ReflectionException {
        try {
            String DOMAIN = "localhost";
            MBeanServer server = MBeanServerFactory.createMBeanServer(DOMAIN);
            server.registerMBean(new Hello(), new ObjectName("jmx:type=hello"));
            JMXServiceURL url = new JMXServiceURL("rmi", null, 9998, "/jndi/rmi://localhost:" + 11099 + "/zml");
            JMXConnectorServer jmxServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
            System.out.println(url);
            LocateRegistry.createRegistry(11099);
            jmxServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}
