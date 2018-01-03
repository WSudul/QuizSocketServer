package org.ws.communication;

import java.io.Serializable;
import java.util.List;

public class QuizListMessage extends SocketMessage implements Serializable {

    private static final long serialVersionUID = 3590476803891320170L;
    private List<String> quizes;
    //private Long quizId;

    public QuizListMessage(String author) {
        super(author);
    }

    @Deprecated
    public QuizListMessage() {

    }

    public List<String> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<String> quizes) {
        this.quizes = quizes;
    }

}
