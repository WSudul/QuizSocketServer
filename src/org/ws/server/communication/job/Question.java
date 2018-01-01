package org.ws.server.communication.job;

import java.io.Serializable;
import java.util.Set;


public class Question implements Serializable  {


    private static final long serialVersionUID = 2343482157709020208L;
    private QuestionType questionType;
    private Long id;
    private String question;
    private Set<String> possibleAnswers;


    public Question(){
        this.questionType=QuestionType.OneOf;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionType=" + questionType +
                ", id=" + id +
                ", question='" + question + '\'' +
                ", possibleAnswers=" + possibleAnswers +
                '}';
    }


    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(Set<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }



}
