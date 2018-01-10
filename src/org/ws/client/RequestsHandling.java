package org.ws.client;

import org.ws.communication.*;
import org.ws.communication.job.Answer;
import org.ws.communication.job.Question;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class RequestsHandling {
    private final static Logger logger = Logger.getLogger(RequestsHandling.class.getName());
    private  ObjectInputStream input;
    protected ObjectOutputStream output = null;
    private boolean shouldRead = true;
    private String name;
    private Long currentQuizId;

    RequestsHandling(ObjectOutputStream out,ObjectInputStream input) {
        this.output = out;
        this.input = input;
    }

    public boolean login(String name){
        this.name=name;
        try {
            output.writeObject(new QuizLoginMessage(name));
            Object message= input.readObject();


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
            Object message= input.readObject();


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
        logger.info("Trying to start quiz "+id);
        try {
            output.writeObject(new RequestQuizMessage(id,name));
            Object message= input.readObject();


            if(message instanceof RejectionMessage)
            {
                logger.warning("Client: Received Rejection message"+
                        ((RejectionMessage) message).getReason());
                return null;
            }
            else if(message instanceof QuizMessage)
            {
                QuizMessage quizListMessage = (QuizMessage) message;
                this.currentQuizId=quizListMessage.getQuizId();
                logger.info("Client: Received quiz with questions:"+quizListMessage.getQuestions());
                return quizListMessage.getQuestions();
            }
            else
                return null;

        } catch (IOException e) {
           logger.warning("Client:"+e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
           logger.warning("Client:"+e.getMessage());
            return null;
        }

    }
    public boolean sendAnswer(Answer answer){
        return sendAnswer(Arrays.asList(answer));
    }

    public boolean sendAnswer(List<Answer> answers){
        QuizAnswerMessage answerMessage = new QuizAnswerMessage(name);
        answerMessage.setAnswers(answers);
        try {
            output.writeObject(answerMessage);

            Object response=input.readObject();
            if(response instanceof OkResponseMessage)
                return true;
            else if(response instanceof RejectionMessage){
                RejectionMessage rejection = (RejectionMessage) response;
               logger.warning(rejection.getReason());
                return false;
            }else
                return false;



        } catch (IOException e) {
           logger.warning("Client:"+e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
           logger.warning("Client:"+e.getMessage());
        }
        return true;


    }

    public void endCommunication() {
        EndCommunicationMessage message = new EndCommunicationMessage(name);
        try {
            output.writeObject(message);
        } catch (IOException e) {
            logger.warning("Client:" + e.getMessage());
        }
        return;
    }

}

