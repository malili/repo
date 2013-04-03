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

public class JmxBeanDemo {
    /**
     * localhost:9998 -Dcom.sun.management.jmxremote.port=9998 -Dcom.sun.management.jmxremote.ssl=false
     * -Dcom.sun.management.jmxremote.authenticate=false
     */
    public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException,
            NotCompliantMBeanException, MalformedObjectNameException, NullPointerException, InterruptedException,
            IntrospectionException, InstanceNotFoundException, ReflectionException {
        // MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        //
        // ObjectName objectName = new ObjectName("jmx:type=hello");
        //
        // server.registerMBean(new Hello(), objectName);
        //
        // MBeanInfo meanInfo = server.getMBeanInfo(objectName);
        try {
            String DOMAIN = "localhost";
            MBeanServer server = MBeanServerFactory.createMBeanServer(DOMAIN);
            server.registerMBean(new Hello(), new ObjectName("jmx:type=hello"));
            JMXServiceURL url = new JMXServiceURL("rmi", null, 9998, "/jndi/rmi://localhost:" + 1099 + "/zml");
            // start()和stop()来启动和停止 JMXConnectorServer
            // 得到存储jmx用户信息的文件
            // String userFile =context.getRealPath("/")+"/Web-INF/classes/"+Constants.JMX_USERS_FILE;
            // //创建authenticator并且初始化RMI服务器
            // Map<string> env = new HashMap<string>();
            // env.put("jmx.remote.x.password.file", userFile);
            // env = null;
            JMXConnectorServer jmxServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
            // service:jmx:rmi://localhost:9589/jndi/rmi://localhost:1099/zml
            // service:jmx:rmi:///jndi/rmi://localhost:1099/zml
            System.out.println(url);
            // 在RMI上注册
            LocateRegistry.createRegistry(1099);
            jmxServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}
