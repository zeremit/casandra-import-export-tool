package com.kharevich;

import org.apache.cassandra.config.Config;
import org.apache.cassandra.config.Schema;
import org.apache.cassandra.db.Keyspace;

/**
 * Created by khorevich on 21.12.15.
 */
public class SSTableConverter {
    public static void main(String[] args) {
//        Schema.instance.loadFromDisk(false);
        Config.setClientMode(true);
        Schema.instance.loadFromDisk(false);
        Keyspace keyspace = Keyspace.open("java_load");
        System.out.println(keyspace);
    }
}
