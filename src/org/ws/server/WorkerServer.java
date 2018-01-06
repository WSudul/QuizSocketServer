package org.ws.server;

import org.ws.server.config.DBConnectionConfiguration;
import org.ws.server.config.WorkerConfiguration;
import org.ws.server.config.WorkerServerConfiguration;
import org.ws.server.database.DAO;
import org.ws.server.database.JDBCConnectionPool;
import org.ws.server.worker.Worker;


import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;


public class  WorkerServer implements Runnable {


    private final static Logger logger = Logger.getLogger(WorkerServer.class.getName());
    ExecutorService executors;
    private List<WorkerConfiguration> workerConfigurations = new ArrayList<>
            (Arrays.asList(new WorkerConfiguration("Worker", TimeUnit.SECONDS, 10, 3)));
    private String name = "Worker-Server";
    private int workerPoolSize = 10;
    private int serverPoolSize = 5;
    private InetAddress inetAddress;
    private Integer port = 8080;
    private DAO dao;
    private ThreadSafeSet<String> connectedUsers=new ThreadSafeSet<>();
    private JDBCConnectionPool connectionPool;
    private WorkerServerConfiguration workerServerConfiguration;


    public WorkerServer(WorkerServerConfiguration configuration) {
        this.workerServerConfiguration=configuration;

        logger.info("WorkerServer is being configured");


        DBConnectionConfiguration connConfig=configuration.getDbConnectionConfiguration();

        String url=connConfig.getDatabaseSpecificAddress() +
                connConfig.getDatabaseServerAddress() + ":" + connConfig.getPort() + "/";

        logger.info("creating connection pool to "+url);
        connectionPool=new JDBCConnectionPool(connConfig.getDriverName(),url,connConfig.getUserName(),
                connConfig.getPassword());

        logger.info("Dao object created");
        dao=new DAO(configuration.getDaoConfiguration(),connectionPool.checkOut());



        this.configure(configuration);

        //#TODO wrap this

        logger.info("WorkerServer is almost configured");



        logger.info("Created WorkerServer with name: " + this.name + " address:" + this.inetAddress.getHostAddress());

    }

    private void configure(WorkerServerConfiguration configuration) {

        if (configuration.getName().isPresent())
            this.name = configuration.getName().get();

        if (configuration.getWorkerConfigurations().isPresent())
            workerConfigurations = configuration.getWorkerConfigurations().get();

        if (configuration.getInetAddress().isPresent())
            this.inetAddress = configuration.getInetAddress().get();
        else {
            try {
                this.inetAddress = InetAddress.getByName("127.0.0.1");
            } catch (UnknownHostException e) {
                logger.severe("Unable to create InetAddress:" + e.getMessage());
            }
        }
        if (configuration.getPort().isPresent())
            this.port = configuration.getPort().get();

        workerPoolSize=workerConfigurations.size();

    }

    @Override
    public void run() {
        //call looping method
        logger.info("Server " + this.name + " thread is started: " + Thread.currentThread().getName());

        if(!dao.initialize()) {
            logger.severe("Could not  valid ensure database access");
            //return;
        }
        handleRequests();

    }



    private void handleRequests() {



        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
           logger.warning(name + " " + e.getMessage());

        }
        //make it thread safe variable
        boolean shouldLive=true;

        executors =  Executors.newFixedThreadPool(workerPoolSize);

        while(shouldLive){

            logger.info("WorkerServer "+ this.name +" is waiting for client");
            try {
                Socket client=serverSocket.accept();
                executors.submit(new Worker(client,connectedUsers,connectionPool.checkOut()));
            } catch (IOException e) {
                logger.warning("Exception caught when handling socket "+e.getMessage());
                return;
            }
        }

        executors.shutdown();



    }

    public String getName() {
        return name;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public Integer getPort() {
        return port;
    }

    public long getConnectedUserCount() {
        return connectedUsers.size();
    }
}
