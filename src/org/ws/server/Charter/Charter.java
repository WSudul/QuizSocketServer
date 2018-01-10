package org.ws.server.Charter;

import org.ws.server.database.IResultBrowser;
import org.ws.server.database.ResultBrowser;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Charter implements Runnable {

    private final static Logger logger = Logger.getLogger(Charter.class.getName());
    private IResultBrowser resultBrowser;
    private CharterFrame charterFrame;

    public Charter(Connection connection, String schemaName) {
        resultBrowser = new ResultBrowser(connection, schemaName);

    }


    @Override
    public void run() {
        logger.info("Starting Charter service");

        charterFrame = new CharterFrame();
        try {
            while (true) {

                //do stuff

                List<Long> quizIds = resultBrowser.getQuizIds();

                for (Long id : quizIds) {
                    charterFrame.getData(id,resultBrowser.getAnswersCounts(id));
                    sleep(2000);
                }
                sleep(2000);

            }

        } catch (InterruptedException e) {

            logger.info("Charter service interrrupted!");
            return;
        }
    }
}