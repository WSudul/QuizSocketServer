package org.ws.client;

import org.ws.communication.QuizMessage;
import org.ws.communication.QuizResultsMessage;
import org.ws.communication.QuizesMessage;
import org.ws.communication.RequestQuizesMessage;
import org.ws.communication.job.Question;
import org.ws.communication.job.Result;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Set;

public class RequestsHandling {
    protected ObjectOutputStream output = null;
    private boolean shouldRead=true;

    RequestsHandling(ObjectOutputStream out) {
        this.output = out;
        quizChosen();


    }
        protected List<String> quizesChosen() {
            System.out.println("quizes chosen");
            List<String> listofquizes = null;
            listofquizes.add("1"); listofquizes.add("2");
            String NIU = "123";
            RequestQuizesMessage qu = new RequestQuizesMessage(listofquizes, NIU);
            listofquizes=qu.getQuizes();
            try {
                while(shouldRead){
                System.out.println("quizes try succ");
                output.writeObject(new RequestQuizesMessage(listofquizes, NIU));}
            } catch (IOException e) {
                System.out.println("quizes try failed");
                System.out.println(e.getMessage());
            }
            System.out.println("request " + this.getClass().getName() + " sent");

            return listofquizes;
        }

        protected List<Question> quizChosen(){
            List<Question> questions=null;
            String NIU = "123";
            Question first=new Question();
            first.setId(1l);first.setQuestion("Why though"); Set<String> answers = null; answers.add("dont ask"); answers.add("yea"); first.setPossibleAnswers(answers);
            QuizMessage qu=new QuizMessage(NIU);
            questions=qu.getQuestions();
            return questions;
        }

        protected List<Result> resultsChosen(){
            String NIU = "123";
            List<Result> results=null;
            Result res=new Result(true, "Why though", "whyy", 1l) ;
            results.add(res);
            QuizResultsMessage qu= new QuizResultsMessage(NIU);
            results=qu.getResults();
            return results;
        }
}

