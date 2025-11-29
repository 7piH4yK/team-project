package interface_adapter.gateways.trivia;

import data_access.api.RetrofitClientDataAccessObject;
import data_access.api.TriviaAPI;
import data_access.api.TriviaResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import use_case.question.QuestionDataAccessInterface;

public class TriviaApiDataAccessObject implements QuestionDataAccessInterface {
    private final TriviaAPI triviaAPI;

    public TriviaApiDataAccessObject() {
        Retrofit retrofit = RetrofitClientDataAccessObject.getClient();
        this.triviaAPI = retrofit.create(TriviaAPI.class);
    }

    @Override
    public TriviaResponse fetchRiddles() {
        try {
            Call<TriviaResponse> call = triviaAPI.getQuestions(3, 9, "multiple");
            Response<TriviaResponse> response = call.execute();
            return response.body();
        } catch (Exception e) {
            return null;
        }
    }
}
