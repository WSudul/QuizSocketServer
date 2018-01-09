package org.ws.server;

import org.ws.server.database.IResultBrowser;
import org.ws.server.database.ResultBrowser;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Charter implements Runnable {

    private IResultBrowser resultBrowser;

    public Charter(Connection connection,String schemaName) {

       resultBrowser=new ResultBrowser(connection,schemaName);

    }



    @Override
    public void run() {


        while(true){

            //do stuff

            List<Long> ids = resultBrowser.getQuizIds();
            Map<Long, List<Long>> map = resultBrowser.getQuizQuestionIds(ids);

            for(Long id:ids) {
                resultBrowser.getAnswersCounts(map.get(id));
            }

            try {
                sleep(2000);
            } catch (InterruptedException e) {

                e.printStackTrace();
                return;
            }


        }

    }
}
