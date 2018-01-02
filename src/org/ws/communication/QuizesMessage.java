package org.ws.communication;

import org.ws.communication.job.Question;

import java.io.Serializable;
import java.util.List;

public class QuizesMessage extends SocketMessage implements Serializable {

    private static final long serialVersionUID = 3590476803891320170L;
    private List<Long> quizes;
    //private Long quizId;

    public QuizesMessage(String author) {
        super(author);
    }

    @Deprecated
    public QuizesMessage() {

    }

    public List<Long> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<Long> quizes) {
        this.quizes = quizes;
    }


}
