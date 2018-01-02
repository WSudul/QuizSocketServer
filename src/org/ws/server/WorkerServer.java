package org.ws.server;

import org.ws.server.config.WorkerConfiguration;
import org.ws.server.config.WorkerServerConfiguration;
import org.ws.server.worker.Worker;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    private int workerPoolSize = 1;
    private int serverPoolSize = 5;
    private InetAddress inetAddress;
    private Integer port = 8080;

    public WorkerServer(WorkerServerConfiguration configuration) {

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

        logger.info("Created WorkerServer with name: " + this.name + " address:" + this.inetAddress.getHostAddress());

    }

    @Override
    public void run() {
        //call looping method

        logger.info("Server " + this.name + " thread is started: " + Thread.currentThread().getName());

        handleRequests();

    }


    //old implementation for socket - should be moved to handle http requests
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
        Socket client=new Socket();
        InputStream inputStream;
        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;
        //JsonReader jsonReader;




        //#TODO implement pooling method
       // List<Future<Response>> futureResponses=new LinkedList<>();

        while(shouldLive){

            logger.info("WorkerServer "+ this.name +" is waiting for client");
            try {
                client=serverSocket.accept();  //only 1 connection allowed to serverSocket
                executors.submit(new Worker(client));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception caught when handling socket "+e.getMessage());
                return;
            }



            //#TODO - use java EE JsonReader + create a shared container (LIFO ) to give response

            //reading and writing needs to be synchronized!

           // Request request=new Request(new ArrayList<Job>());

            //#TODO better config passing
           // Worker workerCallable=new Worker(request,workerConfigurations.get(0));
          // futureResponses.add(executors.submit(workerCallable));


            //implement pooling throught futureResponses and send response when isDone() returns true;
//            Iterator<Future<Response>> iterator=futureResponses.iterator();
//            while(iterator.hasNext()){
//                Future<Response> response=iterator.next();
//                if(response.isDone()) {
//                    try {
//                        Response result=response.get();
//                        iterator.remove();
//                    } catch (InterruptedException e) {
//                        logger.warning(e.getMessage());
//                    } catch (ExecutionException e) {
//                        logger.warning(e.getMessage());
//                    }
//                }else{
//                    //skip or try waiting for short time ?
//                }
//
//
//            }

        }


    }




}
