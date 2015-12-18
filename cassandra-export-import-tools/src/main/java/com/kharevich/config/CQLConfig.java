package com.kharevich.config;

/**
 * Created by khorevich on 16.12.15.
 */
public interface CQLConfig {

    public static final String KEYSPACE = "java_load";

    public static final String TABLE = "user";

    public static final String DEFAULT_OUTPUT_DIR = "./data";

    public static final String INSERT_STMT = String.format("INSERT INTO %s.%s (uuid, surname, name, email, birthday) VALUES (?, ?, ?, ?, ?)", KEYSPACE, TABLE);

    public static final String SCHEMA = String.format("CREATE TABLE if not exists %s.%s (uuid uuid, surname text, name text, email text, birthday timestamp, primary key((uuid), surname, email))", KEYSPACE, TABLE);

    public static final String CREATE_KEYSPACE = String.format("create keyspace if not exists %s with replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1};", KEYSPACE);

}
