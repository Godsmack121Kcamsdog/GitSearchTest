package github.android.skywell.com.git_sch.api.search;

import github.android.skywell.com.git_sch.data.Response;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SearchAPIInterface {

    @GET
    Flowable<Response> search(@Url String url);
}
