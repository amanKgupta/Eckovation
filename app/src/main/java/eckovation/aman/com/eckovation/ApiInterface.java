package eckovation.aman.com.eckovation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Instinct on 7/20/2018.
 */

public interface ApiInterface {
    @GET("/services/rest/?method=flickr.photos.search&api_key=9f54fd83bd0e18bb5aad38880efb9224&per_page=50&user_id=52540720@N02&format=json&nojsoncallback=1")
    Call<GetData> savePost(
    );
}
