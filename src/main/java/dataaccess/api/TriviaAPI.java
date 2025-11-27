package dataaccess.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import data_access.api.TriviaResponse;

public interface TriviaAPI {
    @GET("api.php")
    Call<TriviaResponse> getQuestions(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("type") String type
    );
}
