package org.ws.communication.job;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 2631924532682336018L;
    private Long questionId;
    private Long answerId;
    private String answerText;


    public Answer(Long questionId, Long answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.answerText=null;
    }

    public Answer(Long questionId, String answerText) {
        this.questionId = questionId;
        this.answerText = answerText;
        this.answerId=null;
    }

    @Deprecated
    public Answer() {

    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
