package org.ws.communication.job;

import java.util.List;
import java.io.Serializable;

public class Result implements Serializable{

    private static final long serialVersionUID = -8396862149531067009L;
    private  List<Long> validAnswers;
    private  List<Long> providedAnswer;
    private  Long questionId;



    public Result(List<Long> validAnswers, List<Long>providedAnswer, Long questionId) {
        this.validAnswers = validAnswers;
        this.providedAnswer = providedAnswer;
        this.questionId = questionId;
    }

    public Result(){
    }

    public boolean isSuccessful() {
        return providedAnswer.equals(validAnswers);
    }

    public List<Long> getValidAnswers() {
        return validAnswers;
    }

    public List<Long> getProvidedAnswer() {
        return providedAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setValidAnswers(List<Long> validAnswers) {
        this.validAnswers = validAnswers;
    }

    public void setProvidedAnswer(List<Long> providedAnswer) {
        this.providedAnswer = providedAnswer;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
