package learn.boardgames.domain;

import learn.boardgames.models.BoardGame;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private final ArrayList<String> messages = new ArrayList<>();
    private ResultType resultType = ResultType.SUCCESS;
    private T payload;

    public boolean isSuccess() {
        return resultType == ResultType.SUCCESS;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void addMessage(String message, ResultType resultType) {
        this.resultType = resultType;
        this.messages.add(message);
    }
}
