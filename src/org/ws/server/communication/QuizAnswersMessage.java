package org.ws.server.communication;


import org.ws.server.communication.job.Answer;
import org.ws.server.communication.job.Question;

import java.io.Serializable;
import java.util.List;


public class QuizAnswersMessage extends SocketMessage implements Serializable {


    private static final long serialVersionUID = 8100311873209486609L;
    private List<Answer> questions;
    private Long quizId;

    public QuizAnswersMessage(String author) {
        super(author);

    }

    @Deprecated
    public QuizAnswersMessage() {

    }

}
