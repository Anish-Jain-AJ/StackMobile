package aj.stackmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TagInterface {

    @GET("/2.2/tags?order=desc&sort=popular&site=stackoverflow")
    Call<TagItem> getTag();
}