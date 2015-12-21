package com.kharevich;

import com.datastax.driver.core.SSLOptions;
import com.google.common.collect.Sets;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.config.EncryptionOptions;
import org.apache.cassandra.config.YamlConfigurationLoader;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.io.sstable.SSTableLoader;
import org.apache.cassandra.security.SSLFactory;
import org.apache.cassandra.streaming.StreamConnectionFactory;
import org.apache.cassandra.streaming.StreamResultFuture;
import org.apache.cassandra.tools.BulkLoadConnectionFactory;
import org.apache.cassandra.utils.NativeSSTableLoaderClient;
import org.apache.cassandra.utils.OutputHandler;
import org.apache.commons.cli.*;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.kharevich.config.CQLConfig.DEFAULT_OUTPUT_DIR;
import static com.kharevich.config.CQLConfig.KEYSPACE;
import static com.kharevich.config.CQLConfig.TABLE;

/**
 * Created by khorevich on 16.12.15.
 */
public class LoadSSTables {

    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {
        Config.setClientMode(true);
        File outputDir = new File(DEFAULT_OUTPUT_DIR + File.separator + KEYSPACE + File.separator + TABLE);
        if (!outputDir.exists()) {
            throw new RuntimeException("Directory doesn't exist: " + outputDir);
        }
        OutputHandler handler = new OutputHandler.SystemOutput(true, true);
        LoaderOptions options = new LoaderOptions(outputDir);
        options.hosts.add(InetAddress.getByName("localhost"));
        SSTableLoader loader = new SSTableLoader(
                outputDir,
                new ExternalClient(
                        options.hosts,
                        options.nativePort,
                        options.user,
                        options.passwd,
                        options.storagePort,
                        options.sslStoragePort,
                        options.serverEncOptions,
                        buildSSLOptions((EncryptionOptions.ClientEncryptionOptions)options.encOptions)),
                handler,
                options.connectionsPerHost);
        StreamResultFuture future = loader.stream();

        future.get();
        Thread.sleep(1000);

    }
    private static SSLOptions buildSSLOptions(EncryptionOptions.ClientEncryptionOptions clientEncryptionOptions)
    {

        if (!clientEncryptionOptions.enabled)
            return null;

        SSLContext sslContext;
        try
        {
            sslContext = SSLFactory.createSSLContext(clientEncryptionOptions, true);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not create SSL Context.", e);
        }

        return null;
    }


    static class ExternalClient extends NativeSSTableLoaderClient
    {
        private final int storagePort;
        private final int sslStoragePort;
        private final EncryptionOptions.ServerEncryptionOptions serverEncOptions;

        public ExternalClient(Set<InetAddress> hosts,
                              int port,
                              String user,
                              String passwd,
                              int storagePort,
                              int sslStoragePort,
                              EncryptionOptions.ServerEncryptionOptions serverEncryptionOptions,
                              SSLOptions sslOptions)
        {
            super(hosts, port, user, passwd, sslOptions);
            this.storagePort = storagePort;
            this.sslStoragePort = sslStoragePort;
            this.serverEncOptions = serverEncryptionOptions;
        }

        @Override
        public StreamConnectionFactory getConnectionFactory()
        {
            return new BulkLoadConnectionFactory(storagePort, sslStoragePort, serverEncOptions, false);
        }
    }

    static class LoaderOptions
    {
        public final File directory;

        public boolean debug;
        public boolean verbose;
        public boolean noProgress;
        public int nativePort = 9042;
        public String user;
        public String passwd;
        public int throttle = 0;
        public int storagePort;
        public int sslStoragePort;
        public EncryptionOptions encOptions = new EncryptionOptions.ClientEncryptionOptions();
        public int connectionsPerHost = 1;
        public EncryptionOptions.ServerEncryptionOptions serverEncOptions = new EncryptionOptions.ServerEncryptionOptions();

        public final Set<InetAddress> hosts = new HashSet<>();
        public final Set<InetAddress> ignores = new HashSet<>();

        LoaderOptions(File directory)
        {
            this.directory = directory;
        }



    }
}
