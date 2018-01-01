package org.ws.server.communication;


import org.ws.server.communication.job.Question;

import java.io.Serializable;
import java.util.List;


public class QuizMessage extends SocketMessage implements Serializable {


    private static final long serialVersionUID = 8100311873209486609L;
    private List<Question> questions;
    private Long quizId;

    public QuizMessage(String author) {
        super(author);

    }

    @Deprecated
    public QuizMessage() {

    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
