package com.kharevich;

import org.apache.cassandra.io.sstable.SSTableLoader;

import java.io.File;

import static com.kharevich.config.CQLConfig.DEFAULT_OUTPUT_DIR;
import static com.kharevich.config.CQLConfig.KEYSPACE;
import static com.kharevich.config.CQLConfig.TABLE;

/**
 * Created by khorevich on 16.12.15.
 */
public class LoadSSTables {

    public static void main(String[] args) {
        File outputDir = new File(DEFAULT_OUTPUT_DIR + File.separator + KEYSPACE + File.separator + TABLE);
        if (!outputDir.exists()) {
            throw new RuntimeException("Directory doesn't exist: " + outputDir);
        }
//        SSTableLoader ssTableLoader = new SSTableLoader();
    }
}
