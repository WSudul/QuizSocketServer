package org.ws.client;

import org.ws.communication.QuizMessage;
import org.ws.communication.QuizResultsMessage;
import org.ws.communication.RequestQuizListMessage;
import org.ws.communication.job.Question;
import org.ws.communication.job.Result;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestsHandling {
    protected ObjectOutputStream output = null;
    private boolean shouldRead = true;

    RequestsHandling(ObjectOutputStream out) {
        this.output = out;
        quizChosen();


    }

    protected List<String> quizesChosen() {
        System.out.println("quizes chosen");
        List<String> listofquizes = null;
        listofquizes.add("1");
        listofquizes.add("2");
        String NIU = "123";
        RequestQuizListMessage qu = new RequestQuizListMessage(listofquizes, NIU);
        listofquizes = qu.getQuizes();
        try {
            while (shouldRead) {
                System.out.println("quizes try succ");
                output.writeObject(new RequestQuizListMessage(listofquizes, NIU));
            }
        } catch (IOException e) {
            System.out.println("quizes try failed");
            System.out.println(e.getMessage());
        }
        System.out.println("request " + this.getClass().getName() + " sent");

        return listofquizes;
    }

    protected List<Question> quizChosen() {
        List<Question> questions = null;
        String NIU = "123";
        Question first = new Question();
        first.setId(1l);
        first.setQuestion("Why though");
        Map<Long, String> answers = null;
        answers.put(1l, "dont ask");
        answers.put(2l, "yea");
        first.setPossibleAnswers(answers);
        QuizMessage qu = new QuizMessage(NIU);
        questions = qu.getQuestions();
        return questions;
    }

    protected List<Result> resultsChosen() {
        String NIU = "123";
        List<Result> results = null;
        List<Long> valid = new ArrayList<Long>() {{
            add(1l);
        }};
        List<Long> provided = new ArrayList<Long>() {{
            add(2l);
        }};
        Result res = new Result(valid, provided, 1l);
        results.add(res);
        QuizResultsMessage qu = new QuizResultsMessage(NIU);
        results = qu.getResults();
        return results;
    }
}

