package jmx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXProxyRun {
    public static void main(String[] args) {
        JMXWrapper jmxWrapper = new JMXWrapper(HelloMBean.class, "jmx:type=hello");
        HelloMBean mbean = (HelloMBean) jmxWrapper.getProxy();
        mbean.setName("risheng");
        mbean.printHello();
        mbean.printHello("hello");
        mbean.printHello();
    }
}

class JMXInvokerHandler implements InvocationHandler {
    private String name;
    private MBeanServerConnection mbsc = null;

    public JMXInvokerHandler(String name) {
        this.name = name;
    }

    private MBeanServerConnection getMBeanServerConnection() {
        if (null == mbsc) {
            JMXServiceURL url = null;
            JMXConnector jmxc = null;
            try {
                url = new JMXServiceURL("service:jmx:rmi://malili:9998/jndi/rmi://localhost:11099/zml");
                jmxc = JMXConnectorFactory.connect(url, null);
                mbsc = jmxc.getMBeanServerConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mbsc;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MBeanServerConnection connection = getMBeanServerConnection();
        ObjectName objectName = new ObjectName(name);
        String operationName = method.getName();
        Class classes[] = method.getParameterTypes();
        String[] signature = new String[classes.length];
        for (int i = 0; i < signature.length; i++) {
            signature[i] = classes[i].getName();
        }
        Object object = connection.invoke(objectName, operationName, args, signature);
        return object;
    }
}

class JMXWrapper {
    private Class fInterface;
    private String name;
    private JMXInvokerHandler handler;

    public JMXWrapper(Class interfaces, String name) {
        this.fInterface = interfaces;
        this.name = name;
    }

    public Object getProxy() {
        handler = new JMXInvokerHandler(name);
        Object object = Proxy.newProxyInstance(fInterface.getClassLoader(), new Class[] {fInterface }, handler);
        return object;
    }
}
