/*
 * Copyright (c) ANTVISION 2011 All Rights Reserved Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package cn.antvision.jmx.hello;

/**
 * <p>功能描述,该部分必须以中文句号结尾。<p>
 * 
 * 创建日期 2013-3-29<br>
 * @author $Author$<br>
 * @version $Revision$ $Date$
 * @since 3.0.0
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXProxyTest {
    public static void main(String[] args) {
        JMXWrapper jmxWrapper = new JMXWrapper(HelloMBean.class, "jmx:type=hello");
        HelloMBean mbean = (HelloMBean) jmxWrapper.getProxy();
        mbean.addName();
        mbean.print();
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
                url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/zml");
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
