package org.ws.communication;


import org.ws.communication.job.Question;

import java.io.Serializable;
import java.util.List;


public class QuizLoginMessage extends SocketMessage implements Serializable {

    private static final long serialVersionUID = -7288386688322415847L;

    public QuizLoginMessage(String author) {
        super(author);

    }

    @Deprecated
    public QuizLoginMessage() {

    }
}
