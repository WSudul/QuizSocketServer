import org.ws.Main;
import org.ws.client.Connect;
import org.ws.server.WorkerServer;
import org.ws.server.config.WorkerConfiguration;
import org.ws.server.config.WorkerConfigurationBuilder;
import org.ws.server.config.WorkerServerConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.ws.Main.prepareConfig;

public class CommonMain {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.out.println("Hi");
        WorkerServerConfiguration serverConfiguration = prepareConfig();
        logger.info("Starting the WorkServer " + serverConfiguration.getName());
        WorkerServer workerServer = new WorkerServer(serverConfiguration);

        Thread thread=new Thread(workerServer);
        thread.start();


        System.out.println("Trying to start client");
        String address;
        logger.info("Starting client");
        try {
            address=InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Connect client=new Connect(address,8081);
        client.work();


        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
