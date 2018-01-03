package org.ws.server.database;

import org.ws.server.config.DaoConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class DaoCreator extends DaoBase{

    private final static Logger logger = Logger.getLogger(DaoCreator.class.getName());
    private List<String> sqlCreateStatements;


    public DaoCreator(){
        sqlCreateStatements=new ArrayList<>();
    }

    public DaoCreator(Connection dbConnection,String usedSchema,List<String> sqlCreateStatements) {
        this.setDbConnection(dbConnection);
        this.setUsedSchema(usedSchema);
        this.sqlCreateStatements=sqlCreateStatements;

    }


    @Override
    public boolean initialize() {
        String shchemaName=getUsedSchema();

        if (getDbConnection() == null || shchemaName==null) {
            logger.warning("initialization was no completed!");
            return false;
        }
        boolean isSchemaValid = true;
        Statement st = createStatement(getDbConnection());
        if (!selectSchema(shchemaName, st)) {
            if (createSchema(shchemaName, st)) {
                logger.info("Schema " + shchemaName + " has been created");
                if (createTables(sqlCreateStatements, st))
                    logger.info("Tables created");
                else {
                    logger.warning("Tables were not created!");
                    isSchemaValid = false;
                }
            } else {
                logger.info("Unable to create schema");
                isSchemaValid = false;
            }

            if (isSchemaValid) {
                logger.info("Schema validation is completed");

            } else {
                logger.severe("Schema validation is not completed!");
                return false;
            }
        }


        return true;
    }


    private boolean createSchema(String usedSchema, Statement st) {
        return executeUpdate(st, "create Database " + usedSchema + ";") == 1;
    }

    private boolean createTables(List<String> sqlStatements, Statement st) {
        for (String sql : sqlStatements) {
            if (!(0 == executeUpdate(st, sql)))
                return false;
        }
        return true;
    }




}
