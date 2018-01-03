package org.ws.server.database;

import org.ws.server.config.DaoConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.List;

public class DAO {

    private final static Logger logger = Logger.getLogger(DAO.class.getName());
    private String driverName;
    private String databaseSpecificAdress;
    private String databaseServerAdress;
    private Integer port;
    private String userName;
    private String password;
    private String usedSchema;
    private Connection dbConnection;
    private List<String> sqlCreateStatements;



    DAO(){

    }

    DAO(DaoConfiguration configuration){

        if(processConfiguration(configuration))
            logger.info("Loaded configation for DAO");
        else{
            logger.warning("DAO was unable to load config - using default settings");
            loadDefaulConfig();
            }

    }

    private void loadDefaulConfig() {
        this.driverName="com.mysql.jdbc.Driver";
        this.databaseSpecificAdress="jdbc:mysql://";
        this.databaseServerAdress="localhost";
        this.port=3306;
        this.userName= "root";
        this.password="root";
        this.usedSchema="quiz";
        this.sqlCreateStatements=new ArrayList<>();//#TODO add sql statements


    }

    public boolean loadConfiguartion(DaoConfiguration configuration){
        return processConfiguration(configuration);
    }

    private boolean processConfiguration(DaoConfiguration configuration) {
        return false;
    }

    public boolean init(){
        if (checkDriver(driverName))
            logger.info("DB driver is present: "+driverName );
        else {
            logger.severe("No driver found under name:" +driverName);
        }
        // 2 sposób po³¹czenia

        dbConnection=ConnectToDatabase( userName, password);

        if(dbConnection==null) {
            logger.warning("initialization was no completed!");
            return false;
        }
        boolean isSchemaValid=true;
        Statement st = createStatement(dbConnection);
        if(!selectSchema(usedSchema,st)){
            if(createSchema(usedSchema,st))
            {
                logger.info("Schema "+usedSchema+" has been created");
                if(createTables(sqlCreateStatements,st))
                    logger.info("Tables created");
                else {
                    logger.warning("Tables were not created!");
                    isSchemaValid=false;
                }
            }else {
                logger.info("Unable to create schema");
                isSchemaValid=false;
            }

            if(isSchemaValid){
                logger.info("Schema validation is completed");

            }else {
                logger.severe("Schema validation is not completed!");
                return false;
            }
        }


        return true;
    }

    private int executeUpdate(Statement s, String sql) {
        try {
            return s.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warning( "sql statement: "+sql+" could not be executed " +  e.getMessage() + " Error Code: " + e.getErrorCode());
            return -1;
        }

    }

    private Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            logger.warning( "Exception thrown when creating Statement " + e.getMessage() + " Error Code: " + e.getErrorCode());
            System.exit(3);
        }
        return null;
    }

    private boolean createSchema(String usedSchema,Statement st) {
        return executeUpdate(st, "create Database "+usedSchema+";") == 1;


    }

    private boolean createTables(List<String> sqlStatements,Statement st){
        for(String sql:sqlStatements) {
            if(!(0 == executeUpdate(st,
                    sql)))
                return false;
        }
        return true;
    }

    private boolean selectSchema(String usedSchema,Statement st) {
        return executeUpdate(st, "USE nowaBaza;") == 0 ;


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
        Connection connection=null;
        try {

            connection = DriverManager.getConnection(databaseSpecificAdress+databaseServerAdress + ":" + port + "/",
                    connectionProperties);
        } catch (SQLException e) {
            logger.severe("Could not connect to db " + e.getMessage() + " Error Code: " + e.getErrorCode());
        }
        logger.info("Connection to db estabilished");
        return connection;
    }
}
