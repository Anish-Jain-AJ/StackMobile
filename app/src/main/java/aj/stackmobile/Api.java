package aj.stackmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("questions")
    Call<QuestionObject> getAnswers(@Query("page") int page, @Query("pagesize") int pagesize, @Query("site") String site, @Query("tagged") String tag);
}
