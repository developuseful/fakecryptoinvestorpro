package app.useful.cryptocurrencytrackerandroidapp.retrofit;


import app.useful.cryptocurrencytrackerandroidapp.retrofit.Model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CoinMarketCapService {
    @Headers("X-CMC_PRO_API_KEY: e3a58b3a-f257-4c74-935f-eab1715b634d")
    @GET("latest")
    Call<Result> search(
    );
}
