package org.ws;

import org.ws.server.config.*;
import org.ws.server.WorkerServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        serverConfiguration.setPort(8081);

        List<WorkerConfiguration> workerConfigurations;
        workerConfigurations = new ArrayList<WorkerConfiguration>();

        workerConfigurations.add(new WorkerConfigurationBuilder().name("Worker-1").buildWorkerConfiguration());

        serverConfiguration.setWorkerConfigurations(workerConfigurations);

        DaoConfiguration daoConfiguration=prepareDaoConfig();
        DBConnectionConfiguration dbConnectionConfiguration=prepareDBConnectionConfig();

        serverConfiguration.setDaoConfiguration(daoConfiguration);
        serverConfiguration.setDbConnectionConfiguration(dbConnectionConfiguration);


        logger.info("Starting the WorkServer " + serverConfiguration.getName());

        WorkerServer workerServer=new WorkerServer(serverConfiguration);

        workerServer.run();

    }

    private static DBConnectionConfiguration prepareDBConnectionConfig() {

        DBConnectionConfiguration dbConnectionConfiguration=new DBConnectionConfiguration();
        dbConnectionConfiguration.setDriverName( "com.mysql.jdbc.Driver");
        dbConnectionConfiguration.setDatabaseSpecificAddress( "jdbc:mysql://");
        dbConnectionConfiguration.setDatabaseServerAddress ( "127.0.0.1");
        dbConnectionConfiguration.setPort (3306);
        dbConnectionConfiguration.setUserName ("quiz_account");
        dbConnectionConfiguration.setPassword ( "quiz_account");

        return dbConnectionConfiguration;

    }


    private static DaoConfiguration prepareDaoConfig(){
        DaoConfiguration daoConfiguration=new DaoConfiguration();

        List<String> sqlCreationList=new ArrayList<>();

        sqlCreationList.add("CREATE TABLE `answers` (`question_id` int(11) NOT NULL,`id` int(11) NOT NULL,`text` varchar(512) NOT NULL);");
        sqlCreationList.add("CREATE TABLE `correct_answers` (`question_id` int(11) NOT NULL,`answer_id` int(11) NOT NULL);");
        sqlCreationList.add("CREATE TABLE `question` (`quiz_id` int(11) NOT NULL,`id` int(11) NOT NULL,`text` varchar(512) NOT NULL,`value` int(11) NOT NULL DEFAULT '1');");
        sqlCreationList.add("CREATE TABLE `quiz` (`id` int(11) NOT NULL,`active` tinyint(1) NOT NULL DEFAULT '1');");
        sqlCreationList.add("CREATE TABLE `results` (`id` int(11) NOT NULL,`quiz_id` int(11) NOT NULL,`NIU` varchar(16) NOT NULL,`score` int(11) NOT NULL,  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP);");
        sqlCreationList.add("ALTER TABLE `answers` ADD PRIMARY KEY (`question_id`,`id`);");
        sqlCreationList.add("ALTER TABLE `correct_answers` ADD UNIQUE KEY `question_id_2` (`question_id`), ADD KEY `question_id` (`question_id`,`answer_id`);");
        sqlCreationList.add("ALTER TABLE `question` ADD PRIMARY KEY (`quiz_id`,`id`);");
        sqlCreationList.add("ALTER TABLE `quiz` ADD PRIMARY KEY (`id`);");
        sqlCreationList.add("ALTER TABLE `results` ADD KEY `quiz_id` (`quiz_id`);");
        sqlCreationList.add("ALTER TABLE `answers` ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE;");
        sqlCreationList.add("ALTER TABLE `correct_answers` ADD CONSTRAINT `correct_answers_ibfk_1` FOREIGN KEY (`question_id`,`answer_id`) REFERENCES `answers` (`question_id`, `id`);");
        sqlCreationList.add("ALTER TABLE `question` ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;");
        sqlCreationList.add("ALTER TABLE `results` ADD CONSTRAINT `results_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;");


        daoConfiguration.setUsedSchema("quiz");
        daoConfiguration.setCreateStatements(sqlCreationList);

        return daoConfiguration;
    }
}
