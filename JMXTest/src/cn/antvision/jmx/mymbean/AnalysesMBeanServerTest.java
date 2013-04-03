/*
 * Copyright (c) ANTVISION 2011 All Rights Reserved Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package cn.antvision.jmx.mymbean;

/**
 * <p>功能描述,该部分必须以中文句号结尾。<p>
 * 
 * 创建日期 2013-3-29<br>
 * @author $Author$<br>
 * @version $Revision$ $Date$
 * @since 3.0.0
 */
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

/**
 * 运行此程序需要加入以下参数就可以以JMX方式监控程序了<br><br> -Dcom.sun.management.jmxremote.port=9999 该参数表示Server的访问端口<br><br>
 * -Dcom.sun.management.jmxremote.authenticate=false
 * 该参数设置客户端访问时无需认证，如果不加，需要客户端连接时传入认证，认证配置文件是jmxremote.password，该文件在jre/lib/managerment目录下<br><br>
 * -Dcom.sun.management.jmxremote.ssl=false 连接时无需进行ssl认证<br><br>
 * 
 * @version
 * 
 */
public class AnalysesMBeanServerTest {
    private static MBeanServer server;
    private static String PROTOCOL = "rmi";
    private static String HOST = "localhost";
    private static int PORT = 9999;

    public static void init() throws Exception {
        startServerConnector();
        // 将我们的应用MBean注册到Server中，客户端就可以连接访问了
        ObjectName name = new ObjectName("comba:name=analysesServiceMBean");
        server.registerMBean(new Hello(), name);
    }

    public static void startServerConnector() throws Exception {
        // 这里调用ManagementFactory.getPlatformMBeanServer()创建MBeanServer
        // 实际其第一次启动时也是调用MBeanServerFactory.createMBeanServer()创建MBeanServer
        // 之后将创建的这个Server注册到平台的MBeanServer
        // 从jdk文档可知，该方法返回的Server主要用于注册平台MXBean，
        // 但jdk也建议 将此平台用于注册平台 MXBean 之外的其他应用程序管理 Bean。
        // 这将允许所有 MBean 通过同一个 MBeanServer 发布，从而能够更方便地进行网络发布和发现。应该避免平台 MXBean 的名称冲突。
        server = ManagementFactory.getPlatformMBeanServer();
        // 这里再用以下构造方法构造JMXServiceURL，再启动客户端连接监听，客户端就可以正常连接了
        JMXServiceURL serviceURL = new JMXServiceURL(PROTOCOL, HOST, PORT);
        JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(serviceURL, null, server);
        server.registerMBean(connectorServer, new ObjectName("comba:name=connector"));
        connectorServer.start();
        System.out.println(connectorServer.isActive());
    }

    public static void main(String[] args) {
        try {
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        System.out.print("startup....");
    }
}
