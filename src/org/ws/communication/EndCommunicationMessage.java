package org.ws.communication;

import java.io.Serializable;
import java.util.List;

public class EndCommunicationMessage extends SocketMessage implements Serializable {

    private static final long serialVersionUID = -5751621698248274612L;

    public EndCommunicationMessage(String author) {
        super(author);
    }

    @Deprecated
    public EndCommunicationMessage() {

    }
}
