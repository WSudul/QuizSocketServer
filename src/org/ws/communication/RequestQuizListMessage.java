package org.ws.communication;


import java.io.Serializable;
import java.util.List;

public class RequestQuizListMessage extends SocketMessage implements Serializable {

    private static final long serialVersionUID = -6532683589417248936L;

    private List<String> quizes;

    public RequestQuizListMessage(List<String> quizes, String requesterName){
        super(requesterName);
        this.quizes = quizes;
    }

    @Deprecated
    public RequestQuizListMessage(){    }

    public List<String> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<String> quizes) {
        this.quizes = quizes;
    }

}
