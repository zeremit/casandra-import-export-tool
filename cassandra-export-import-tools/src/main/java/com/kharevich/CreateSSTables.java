package com.kharevich;

import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.exceptions.InvalidRequestException;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.fluttercode.datafactory.impl.DataFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


import static com.kharevich.config.CQLConfig.*;

/**
 * Created by khorevich on 16.12.15.
 */
public class CreateSSTables {

    private static int OPERATIONS_AMOUNT = 10000;

    /**
     * Default output directory
     */



    public static void main(String[] args) {

        File outputDir = new File(DEFAULT_OUTPUT_DIR + File.separator + KEYSPACE + File.separator + TABLE);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new RuntimeException("Cannot create output directory: " + outputDir);
        }

        // Prepare SSTable writer
        CQLSSTableWriter.Builder builder = CQLSSTableWriter.builder();
        builder.inDirectory(outputDir).
                forTable(SCHEMA).
                using(INSERT_STMT).
                withPartitioner(new Murmur3Partitioner());
        CQLSSTableWriter writer = builder.build();
        DataFactory df = new DataFactory();
        try {
            for (int i = 0; i < OPERATIONS_AMOUNT; i++) {
                writer.addRow(UUID.randomUUID(), df.getLastName(), df.getName(), df.getEmailAddress(), df.getBirthDate());

//            Insert insertStatement = QueryBuilder.insertInto("sstable_export3", "user").value("uuid", UUID.randomUUID()).value("name", df.getName()).value("surname", df.getLastName()).value("email", df.getEmailAddress()).value("birthday", df.getBirthDate().getTime());
//            session.execute(insertStatement);
//            if(op==operationPerPercent){
//                System.out.println(100*i/OPERATIONS_AMOUNT+"%");
//                op=0;
//            }
//            op++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }
}
