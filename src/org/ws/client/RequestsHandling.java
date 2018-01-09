package org.ws.client;

import org.ws.communication.*;
import org.ws.communication.job.Question;
import org.ws.communication.job.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestsHandling {
    private  ObjectInputStream in;
    protected ObjectOutputStream output = null;
    private boolean shouldRead = true;
    private String name;

    RequestsHandling(ObjectOutputStream out,ObjectInputStream in) {
        this.output = out;
        this.in=in;
    }

    public boolean login(String name){
        this.name=name;
        try {
            output.writeObject(new QuizLoginMessage(name));
            Object message=in.readObject();


            if(message instanceof RejectionMessage)
                return false;
            else if(message instanceof OkResponseMessage)
                return true;
            else
                return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Long> requestQuizList(){
        try {
            output.writeObject(new RequestQuizListMessage(name));
            Object message=in.readObject();


            if(message instanceof RejectionMessage)
                return null;
            else if(message instanceof QuizListMessage)
            {
                QuizListMessage quizlistmsg = (QuizListMessage) message;
                return quizlistmsg.getQuizes();
            }
            else
                return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Question> startQuiz(Long id){

        try {
            output.writeObject(new RequestQuizMessage(id,name));
            Object message=in.readObject();


            if(message instanceof RejectionMessage)
                return null;
            else if(message instanceof QuizMessage)
            {
                QuizMessage quizlistmsg = (QuizMessage) message;
                return quizlistmsg.getQuestions();
            }
            else
                return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    protected List<String> quizesChosen() {
        System.out.println("quizes chosen");
        List<String> listofquizes = null;
        listofquizes.add("1");
        listofquizes.add("2");
        String NIU = "123";
        RequestQuizListMessage qu = new RequestQuizListMessage( NIU);
        listofquizes =null;// qu.getQuizes();
        try {
            while (shouldRead) {
                System.out.println("quizes try succ");
                output.writeObject(new RequestQuizListMessage(NIU));
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

