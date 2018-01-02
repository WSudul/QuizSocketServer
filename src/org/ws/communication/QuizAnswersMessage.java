package org.ws.communication;


import org.ws.communication.job.Answer;

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


    public List<Answer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Answer> questions) {
        this.questions = questions;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}
