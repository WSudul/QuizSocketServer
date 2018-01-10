import org.ws.Main;
import org.ws.client.Connect;
import org.ws.client.gui.ClientApplication;
import org.ws.server.WorkerServer;
import org.ws.server.config.WorkerServerConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import static org.ws.Main.prepareConfig;

public class CommonMain {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        WorkerServerConfiguration serverConfiguration = prepareConfig();
        logger.info("Starting the WorkServer " + serverConfiguration.getName());
        WorkerServer workerServer = new WorkerServer(serverConfiguration);

        Thread thread=new Thread(workerServer);
        thread.start();


        System.out.println("Trying to start client");
        String address;
        int port=8081;
        logger.info("Starting client");
        try {
            address=InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ClientApplication myRunnable1 = new ClientApplication(address,port,"TestName1");
        Thread t1 = new Thread(myRunnable1);
        t1.start();
//        ClientApplication myRunnable2 = new ClientApplication(address,port,"TestName2");
//        Thread t2 = new Thread(myRunnable2);
//        t2.start();

        //Connect client=new Connect(address,port);
        //client.work();


        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
