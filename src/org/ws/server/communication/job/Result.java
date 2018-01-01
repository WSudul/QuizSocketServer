package org.ws.server.communication.job;

import java.io.Serializable;

public class Result implements Serializable{

    private static final long serialVersionUID = -8396862149531067009L;
    private final Boolean successful;
    private final String originalAnswer;
    private final String providedAnswer;
    private final Long questionId;


    public Result(boolean successful, String originalAnswer, String providedAnswer, Long questionId) {
        this.successful = successful;
        this.originalAnswer = originalAnswer;
        this.providedAnswer = providedAnswer;
        this.questionId = questionId;
    }

    @Deprecated
    public Result(){

        successful = null;
        originalAnswer = null;
        providedAnswer = null;
        questionId = null;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getOriginalAnswer() {
        return originalAnswer;
    }

    public String getProvidedAnswer() {
        return providedAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }
}
