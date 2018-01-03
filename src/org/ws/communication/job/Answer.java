package org.ws.communication.job;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 2631924532682336018L;
    private Long questionId;
    private Long answerId;


    public Answer(Long questionId, Long answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
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

}
