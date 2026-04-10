package komunikacija;

import enums.ResponseStatus;
import java.io.Serializable;

public class Response implements Serializable {

    private ResponseStatus status = ResponseStatus.SUCCESS;
    private Object response;
    private Exception exception;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
        if (exception != null) {
            this.status = ResponseStatus.ERROR;
        }
    }
}
