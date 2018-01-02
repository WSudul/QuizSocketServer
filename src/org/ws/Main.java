package org.ws;

import org.ws.server.config.WorkerConfiguration;
import org.ws.server.config.WorkerServerConfiguration;
import org.ws.server.WorkerServer;
import org.ws.server.config.WorkerConfigurationBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String [ ] args){

        WorkerServerConfiguration serverConfiguration=new WorkerServerConfiguration();

        serverConfiguration.setName("WorkerServer-1");
        try {
            serverConfiguration.setInetAddress(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            logger.severe("Configuration setup failed. Could not resolve InetAddress "
                    + e.getMessage());
        }
        serverConfiguration.setPort(8080);

        List<WorkerConfiguration> workerConfigurations;
        workerConfigurations = new ArrayList<WorkerConfiguration>();

        workerConfigurations.add(new WorkerConfigurationBuilder().name("Worker-1").buildWorkerConfiguration());
        workerConfigurations.add(new WorkerConfigurationBuilder().name("Worker-2").buildWorkerConfiguration());
        workerConfigurations.add(new WorkerConfigurationBuilder().name("Worker-3").buildWorkerConfiguration());
        workerConfigurations.add(new WorkerConfigurationBuilder().name("Worker-4").buildWorkerConfiguration());

        serverConfiguration.setWorkerConfigurations(workerConfigurations);

        logger.info("Starting the WorkServer " + serverConfiguration.getName());

        WorkerServer workerServer=new WorkerServer(serverConfiguration);

        workerServer.run();

    }
}
