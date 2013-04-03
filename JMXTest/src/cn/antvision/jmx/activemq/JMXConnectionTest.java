package cn.antvision.jmx.activemq;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JMXConnectionTest {
    private static Log log = LogFactory.getLog(JMXConnectionTest.class);

    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.1.26:1099/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        ObjectName name = new ObjectName("org.apache.activemq:BrokerName=localhost,Type=Broker");
        BrokerViewMBean mbean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(connection, name,
                BrokerViewMBean.class, true);
        log.info("**************");
        log.info("getBrokerId:" + mbean.getBrokerId());
        log.info("getBrokerName:" + mbean.getBrokerName());
        log.info("getTotalMessageCount:" + mbean.getTotalMessageCount());
        log.info("getTotalEnqueueCount:" + mbean.getTotalEnqueueCount());
        log.info("getTotalDequeueCount:" + mbean.getTotalDequeueCount());
        log.info("**************");
        log.info("==============");
        ObjectName[] queueNames = mbean.getQueues();
        log.info("getQueues count:" + queueNames.length);
        for (ObjectName queueName : queueNames) {
            QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler.newProxyInstance(connection,
                    queueName, QueueViewMBean.class, true);
            log.info("------------");
            log.info("queueName:" + queueMbean.getName());
            log.info("queuesize:" + queueMbean.getQueueSize());
            log.info("getEnqueueCount:" + queueMbean.getEnqueueCount());
            log.info("getDequeueCount:" + queueMbean.getDequeueCount());
        }
        log.info("==============");
        log.info("++++++++++++++");
        ObjectName[] topicNames = mbean.getTopics();
        log.info("topic count:" + topicNames.length);
        for (ObjectName topicName : topicNames) {
            TopicViewMBean topicMBean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(connection,
                    topicName, TopicViewMBean.class, true);
            log.info("------------");
            log.info("topivName:" + topicMBean.getName());
            log.info("topivSize:" + topicMBean.getQueueSize());
            log.info("getEnqueueCount:" + topicMBean.getEnqueueCount());
            log.info("getDequeueCount:" + topicMBean.getDequeueCount());
        }
        log.info("++++++++++++++");
    }
}
