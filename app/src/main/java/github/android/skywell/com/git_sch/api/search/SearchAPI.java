package github.android.skywell.com.git_sch.api.search;

import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import org.jetbrains.annotations.NotNull;

import github.android.skywell.com.git_sch.App;
import github.android.skywell.com.git_sch.data.Response;
import github.android.skywell.com.git_sch.db.SearchDatabase;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import github.android.skywell.com.git_sch.App;
import github.android.skywell.com.sky_git_searcher.BuildConfig;
import github.android.skywell.com.sky_git_searcher.R;

public class SearchAPI {

    private static SearchAPIInterface SearchInterface;
    private static SearchAPI APICompetitions;
    private static final String HOST = App.Companion.getInstance().getMResources().getString(R.string.host);
    private static final String SEARCH = "/search/repositories";

    protected static final String Q = "?q=";

    public synchronized static SearchAPI getInstance() {
        if (APICompetitions == null) APICompetitions = new SearchAPI();
        return APICompetitions;
    }

    private SearchAPI() {
        createRetrofit();
    }

    private void createRetrofit() {
        //OkHttpClient3
        final OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(logInterceptorBuilder())
                .build();

        //Retrofit2
        Retrofit retrofitAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HOST)
                .client(client)
                .build();
        SearchInterface = retrofitAdapter.create(SearchAPIInterface.class);
    }

    private LoggingInterceptor logInterceptorBuilder() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("RequestLog")
                .response("ResponseLog")
                .tag("info")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .logger((level, tag, msg) -> Log.w(tag, msg))
                .build();
    }

    public Flowable<Response> searchRepository(@NotNull String name, String language, String sort, String order) {
        String url = HOST.concat(SEARCH).concat(Q).concat(name);
        if (language != null) url = url.concat(language);
        if (sort != null && order != null) url = url.concat(sort).concat(order);
        return SearchInterface.search(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Response> searchRepository(@NotNull String name) {
        return searchRepository(name, null, null, null);
    }

    public Flowable<Response> searchRepository(@NotNull String name, String language) {
        return searchRepository(name, language, null, null);
    }

}
