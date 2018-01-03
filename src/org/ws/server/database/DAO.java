package org.ws.server.database;

import org.ws.communication.job.Question;
import org.ws.server.config.DaoConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class DAO extends DaoBase implements IQuizDAO{
    private final static Logger logger = Logger.getLogger(DAO.class.getName());
    private DaoCreator daoCreator;
    private DaoConfiguration configuration;
    private Connection dbConnection;
    private Statement st;

    public DAO(DaoConfiguration configuration)  {

        if (processConfiguration(configuration))
            //#TODO
            logger.info("Loaded configation for DaoCreator");
        else {
            logger.warning("DaoCreator was unable to load config - using default settings");
            loadDefaultConfig();
        }

    }

    @Override
    public boolean initialize() {

        dbConnection = ConnectToDatabase(configuration.getUserName(), configuration.getPassword());

        if (dbConnection == null) {
            logger.warning("initialization was no completed!");
            return false;
        }else {
            daoCreator = new DaoCreator();
            daoCreator.setDbConnection(dbConnection);
            daoCreator.setUsedSchema(configuration.getUsedSchema());
            if (daoCreator.initialize())
            logger.info("daoCreator initialized");
            else
            {
                logger.warning("daoCreator failed to initialize");
                return false;
            }
        }
        st=createStatement(dbConnection);
        if(st==null){
            logger.warning("unable to create Statement object during initialization");
            return false;
        }

        return true;
    }





    public boolean loadConfiguartion(DaoConfiguration configuration) {
        return processConfiguration(configuration);
    }

    private boolean processConfiguration(DaoConfiguration configuration) {
        return false;
    }


    private void loadDefaultConfig() {
        if(configuration==null)
            configuration=new DaoConfiguration();
        configuration.setDriverName( "com.mysql.jdbc.Driver");
        configuration.setDatabaseSpecificAddress( "jdbc:mysql://");
        configuration.setDatabaseServerAddress ( "localhost");
        configuration.setPort ( 3306);
        configuration.setUserName ("root");
        configuration.setPassword ( "root");
        configuration.setUsedSchema ( "quiz");
    }

    private boolean checkDriver(String driver) {
        try {
            Class.forName(driver).newInstance();
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    private Connection ConnectToDatabase(String userName, String password) {
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", userName);
        connectionProperties.put("password", password);
        Connection connection = null;
        try {

            String url=configuration.getDatabaseSpecificAddress() +
                    configuration.getDatabaseServerAddress() + ":" + configuration.getPort() + "/";
            connection = DriverManager.getConnection(url,
                    connectionProperties);
        } catch (SQLException e) {
            logger.severe("Could not connect to db " + e.getMessage() + " Error Code: " + e.getErrorCode());
        }
        logger.info("Connection to db estabilished");
        return connection;
    }

    @Override
    public List<Long> getQuizes() {
        String[] columns =new String[]{"id"};
        String[] from={"quiz"};
        String condition="active IS TRUE";

        executeUpdate(st,new QueryBuilder()
                .columns(Arrays.asList(columns))
                .from(Arrays.asList(from))
                .where(condition)
                .BuildQuery());
        //#TODO implement
        return null;
    }

    @Override
    public List<Question> getQuiz(Long quizId) {
        String[] columns =new String[]{"id,text,answers.text"};
        String[] from={"question,answers"};
        String condition="WHERE quiz_id"+quizId;
        String joinCondition="id=answers.question_id";

        executeUpdate(st,new QueryBuilder()
                .columns(Arrays.asList(columns))
                .from(Arrays.asList(from))
                .join(JoinType.LEFT,"answers",joinCondition)
                .where(condition)
                .BuildQuery());



        //#TODO implement
        return null;

    }

    @Override
    public Map<Long, Long> getCorrectAnswers(Long quizId) {
        return null;
    }

    @Override
    public boolean persistScore(Long userId, Long quizId, Integer score) {
        return false;
    }

    @Override
    public Map<Long, Long> getUserScores(Long userId) {
        return null;
    }
}
