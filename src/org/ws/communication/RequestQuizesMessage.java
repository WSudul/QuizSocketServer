package org.ws.communication;

import org.ws.communication.SocketMessage;

import java.io.Serializable;
import java.util.List;

public class RequestQuizesMessage extends SocketMessage implements Serializable {

    private static final long serialVersionUID = -6532683589417248936L;

    private List<Long> quizes;

    public RequestQuizesMessage(List<Long> quizes, String requesterName){
        super(requesterName);
        this.quizes = quizes;
    }

    @Deprecated
    public RequestQuizesMessage(){    }

    public List<Long> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<Long> quizes) {
        this.quizes = quizes;
    }

}
