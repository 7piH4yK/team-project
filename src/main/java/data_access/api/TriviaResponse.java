package data_access.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TriviaResponse {

    @SerializedName("response_code")
    private int responseCode;

    private List<Question> results;

    public int getResponseCode() {
        return responseCode;
    }

    public List<Question> getResults() {
        return results;
    }
}
