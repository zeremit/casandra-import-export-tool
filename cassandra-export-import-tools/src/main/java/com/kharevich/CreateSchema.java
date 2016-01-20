package com.kharevich;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.kharevich.config.CQLConfig;

import static com.kharevich.config.CQLConfig.CREATE_KEYSPACE;

/**
 * Created by khorevich on 16.12.15.
 */
public class CreateSchema {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("192.168.2.67").withCredentials("cassandra", "cassandra").build();
        Session session = cluster.connect();

        session.execute(new SimpleStatement(CQLConfig.CREATE_KEYSPACE));
        session.execute(new SimpleStatement(CQLConfig.SCHEMA));
        session.execute(new SimpleStatement(CQLConfig.BLOB_SCHEMA));


        session.close();
        cluster.close();
    }
}
