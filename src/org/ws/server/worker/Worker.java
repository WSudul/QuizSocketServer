package org.ws.server.worker;


import org.ws.communication.*;
import org.ws.communication.job.Answer;
import org.ws.communication.job.Question;
import org.ws.communication.job.Result;
import org.ws.server.ThreadSafeSet;
import org.ws.server.database.IQuizDAO;
import org.ws.server.database.QuizDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Worker implements Runnable {

    private final static Logger logger = Logger.getLogger(Worker.class.getName());
    private ThreadSafeSet<String> connectedUsers;
    private Connection connection;
    private Socket client;
    private String clientName;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean shouldRead = true;
    private String workerName;
    private IQuizDAO quizDAO;
    private Long currentQuizId;

    public Worker(Socket client, ThreadSafeSet<String> connectedUsers, Connection connection) {
        this.client = client;
        this.workerName = "server " + Thread.currentThread().getName();
        this.connectedUsers = connectedUsers;
        this.connection = connection;
        quizDAO = new QuizDAO(connection);
        currentQuizId = null;
    }

    @Override
    public void run() {
        logger.info("Worker: " + Thread.currentThread().getName() + " is started");
        //do stuff

        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            logger.warning(e.getMessage());
            performClose();
            return;
        }
        try {

            Object firstMessage = input.readObject();
            if (isLoginMessage(firstMessage)) {
                clientName = ((SocketMessage) firstMessage).getAuthor();
                if (!connectedUsers.set(clientName)) {
                    output.writeObject(new EndCommunicationMessage(workerName, "Client already logged in"));
                    performClose();
                } else
                    output.writeObject(new OkResponseMessage(clientName));
            }

            while (!client.isClosed()) {
                logger.info(Thread.currentThread().toString());
                Object receivedMessage = input.readObject();

                if (!isValidMessage(receivedMessage)) {
                    sendRejectionMessage("Unrecognized message received!");
                } else {
                    boolean messageHandled = true;
                    SocketMessage message = null;

                    String author = ((SocketMessage) receivedMessage).getAuthor();
                    if (!author.equalsIgnoreCase(clientName)) {
                        EndCommunicationMessage endMessage = new EndCommunicationMessage(workerName,
                                "Identity changed!");
                        output.writeObject(endMessage);
                        performClose();
                        break;
                    }


                    if (receivedMessage instanceof RequestQuizListMessage) {
                        QuizListMessage quizList = new QuizListMessage(workerName);
                        quizList.setQuizes(new ArrayList<>());
                        output.writeObject(quizList);

                    } else if (receivedMessage instanceof RequestQuizMessage) {
                        RequestQuizMessage request = (RequestQuizMessage) receivedMessage;
                        Optional<List<Question>> quiz = quizDAO.getQuiz(request.getQuizId());

                        if (quiz.isPresent()) {
                            QuizMessage payload = new QuizMessage(workerName);
                            payload.setQuestions(new ArrayList<>());
                            payload.setQuizId(request.getQuizId());
                            output.writeObject(payload);
                            currentQuizId = request.getQuizId();
                        } else
                            sendRejectionMessage("No quiz found");


                    } else if (receivedMessage instanceof QuizAnswerMessage) {


                        QuizAnswerMessage quizAnswerMessage = (QuizAnswerMessage) receivedMessage;

                        if (!currentQuizId.equals(quizAnswerMessage.getQuizId())) {
                            sendRejectionMessage("Bad quiz id given!");
                        } else {
                            List<Long> unpersisted = new ArrayList<>();
                            for (Answer answer : quizAnswerMessage.getAnswers()) {

                                boolean was_persisted = quizDAO.persistAnswer(clientName, quizAnswerMessage.getQuizId(),
                                        answer.getQuestionId(), answer.getQuestionId());

                                if (!was_persisted) {
                                    logger.warning("Could not persist answer user:" + clientName
                                            + " question: " + answer.getQuestionId() +
                                            " answer: " + answer.getAnswerId());
                                    unpersisted.add(answer.getQuestionId());

                                }
                            }

                            if (!unpersisted.isEmpty())
                                sendRejectionMessage("Could not persist answers" +
                                        ":" + unpersisted.toString());
                            else
                                output.writeObject(new OkResponseMessage(clientName));
                        }


                    } else if (receivedMessage instanceof ResultsRequestMessage) {

                        QuizResultsMessage resultsMessage = new QuizResultsMessage(workerName);
                        Optional<List<Result>> results = quizDAO
                                .getUserAnswers(clientName, ((ResultsRequestMessage) receivedMessage).getQuizId());

                        if (results.isPresent()) {
                            resultsMessage.setResults(results.get());
                            output.writeObject(resultsMessage);
                        } else
                            output.writeObject(new RejectionMessage(workerName, null, "No results found"));

                    } else if (receivedMessage instanceof EndCommunicationMessage) {
                        performClose();
                    } else {
                        sendRejectionMessage("Unhandled message type received");
                    }


                }

            }

        } catch (IOException e) {
            logger.warning(e.getMessage());
            performClose();
            return;
        } catch (ClassNotFoundException e) {
            logger.warning(e.getMessage());
            performClose();
            return;
        }
    }

    private void performClose() {
        try {
            input.close();
            output.close();
            client.close();
            if (client.isClosed())
                logger.info("Closed connection with client: " + clientName);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }


    }

    private boolean isValidMessage(Object message) {
        return message instanceof SocketMessage;

    }

    private boolean isLoginMessage(Object message) {
        return (isValidMessage(message) && message instanceof QuizLoginMessage);
    }

    private void sendRejectionMessage(String message) throws IOException {
        output.writeObject(new RejectionMessage(workerName, null, message));
    }


}
