import org.ws.Main;
import org.ws.server.WorkerServer;
import org.ws.server.config.WorkerConfiguration;
import org.ws.server.config.WorkerConfigurationBuilder;
import org.ws.server.config.WorkerServerConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CommonMain {

    private final static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String [ ] args){
        System.out.println("Hi");

        WorkerServer workerServer=new WorkerServer(prepareServerConfiguration());
        workerServer.run();



    }


    private static WorkerServerConfiguration prepareServerConfiguration(){
        WorkerServerConfiguration serverConfiguration=new WorkerServerConfiguration();

        serverConfiguration.setName("WorkerServer-1");
        try {
            serverConfiguration.setInetAddress(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            logger.severe("Configuration setup failed. Could not resolve InetAddress "
                    + e.getMessage());
        }
        serverConfiguration.setPort(8081);

        List<WorkerConfiguration> workerConfigurations;
        workerConfigurations = new ArrayList<WorkerConfiguration>();

        workerConfigurations.add(new WorkerConfigurationBuilder().name("Worker-1").buildWorkerConfiguration());

        serverConfiguration.setWorkerConfigurations(workerConfigurations);

        return serverConfiguration;

    }



}
