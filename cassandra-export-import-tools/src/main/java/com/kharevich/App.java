//package com.kharevich;
//
////import com.datastax.driver.core.Cluster;
////import com.datastax.driver.core.Session;
//import org.apache.cassandra.io.sstable.CQLSSTableWriter;
//import org.apache.cassandra.service.StorageServiceMBean;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.management.JMX;
//import javax.management.MBeanServerConnection;
//import javax.management.MalformedObjectNameException;
//import javax.management.ObjectName;
//import javax.management.remote.JMXConnector;
//import javax.management.remote.JMXConnectorFactory;
//import javax.management.remote.JMXServiceURL;
//import java.io.IOException;
//import java.rmi.server.RMISocketFactory;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Hello world!
// *
// */
//public class App
//{
//    private static Logger logger = LoggerFactory.getLogger(App.class);
//
//    private static String keyspace = "ad_network";
//    private static String tableName = keyspace + ".reseller";
//
//    private static final String fmtUrl = "service:jmx:rmi:///jndi/rmi://[%s]:%d/jmxrmi";
//    private static final String ssObjName = "org.apache.cassandra.db:type=StorageService";
//    private static final int defaultPort = 7199;
//
//    private static String schema = "CREATE TABLE " + tableName + " (" +
//            "  id text,\n" +
//            "  effective_since text, -- day in the format of 'YYYY-MM-DD'\n" +
//            "  reward_percent float, -- value from 0.0 to 1.0\n" +
//            "  PRIMARY KEY (id, effective_since)" +
//            ");";
//    private static CQLSSTableWriter writer;
//
//    private static String insertStatement = "INSERT INTO " + tableName + " (id, effective_since, reward_percent) VALUES (?, ?, ?);";
//
//    public static void main( String[] args ) throws IOException {
//        JMXServiceURL jmxUrl = new JMXServiceURL(String.format(fmtUrl, "localhost", 7199));
//        Map<String,Object> env = new HashMap<String,Object>();
////        if (username != null)
//        {
//            String[] creds = { "cassandra", "cassandra" };
//            env.put(JMXConnector.CREDENTIALS, creds);
//        }
//
//        env.put("com.sun.jndi.rmi.factory.socket", RMISocketFactory.getDefaultSocketFactory());
//
//        JMXConnector jmxc = JMXConnectorFactory.connect(jmxUrl, env);
//        MBeanServerConnection mbeanServerConn = jmxc.getMBeanServerConnection();
//        ObjectName name = null;
//        try {
//            name = new ObjectName(ssObjName);
//            StorageServiceMBean ssProxy = JMX.newMBeanProxy(mbeanServerConn, name, StorageServiceMBean.class);
//            System.out.println(ssProxy);
////            ssProxy.takeTableSnapshot(null, "reseller", "main111");
//        } catch (MalformedObjectNameException e) {
//            e.printStackTrace();
//        }
//    }
//}
