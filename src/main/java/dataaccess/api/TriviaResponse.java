package dataaccess.api;

import java.util.List;

public class TriviaResponse {
    private int responseCode;
    private List<Question> results;

    public int getResponseCode() {
        return responseCode;
    }

    public List<Question> getResults() {
        return results;
    }
}
