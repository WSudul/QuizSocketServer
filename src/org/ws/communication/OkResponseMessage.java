package org.ws.communication;


import org.ws.communication.job.Question;

import java.io.Serializable;
import java.util.List;


public class OkResponseMessage extends SocketMessage implements Serializable {


    private static final long serialVersionUID = -7288386688322415847L;

    public OkResponseMessage(String author) {
        super(author);

    }

    @Deprecated
    public OkResponseMessage() {

    }
}
