package com.kharevich;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.kharevich.config.CQLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.kharevich.config.CQLConfig.KEYSPACE;
import static com.kharevich.config.CQLConfig.TABLE_BLOB;
/**
 * Created by zeremit on 12/29/15.
 */
public class LoadBlobToCassandra {

    final static Logger logger = LoggerFactory.getLogger(LoadBlobToCassandra.class);

    public static void main(String[] args) throws IOException {
        logger.info("Loading blob");
        logger.info(CQLConfig.INSERT_STMT_BLOB);
        Cluster cluster = Cluster.builder().addContactPoint("192.168.2.67").withCredentials("cassandra", "cassandra").build();
        Session session = cluster.connect();
        Path path = Paths.get("test.jpg");
        byte[] data = Files.readAllBytes(path);

        Insert query = QueryBuilder.insertInto(KEYSPACE, TABLE_BLOB).value("user_name", "test@test.com").value("avatar", ByteBuffer.wrap(data));
        session.execute(query);
        cluster.close();
    }
}
