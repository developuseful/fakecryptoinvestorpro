package app.useful.cryptocurrencytrackerandroidapp.ui.fragments.investdetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import app.useful.cryptocurrencytrackerandroidapp.retrofit.CoinMarketCapService;
import app.useful.cryptocurrencytrackerandroidapp.retrofit.Model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InvestDetailFragmentViewModel  extends AndroidViewModel {

    private MutableLiveData<HashMap<String, Double>> ldCurrentCourse;


    public InvestDetailFragmentViewModel(@NonNull Application application) {
        super(application);

        ldCurrentCourse = new MutableLiveData<>();
        getCurrentCourse();
    }

    public MutableLiveData<HashMap<String, Double>> getCourse() {
        return ldCurrentCourse;
    }

    private void getCurrentCourse(){

        HashMap<String, Double> map = new HashMap<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinMarketCapService service = retrofit.create(CoinMarketCapService.class);

        Call<Result> call = service.search();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Result result = (Result) response.body();

                for (int i = 0; i < result.getData().size(); i++){

                    String symbol = result.getData().get(i).getSymbol();
                    double price = result.getData().get(i).getQuote().getUsd().getPrice();

                    map.put(symbol, price);

                }
                ldCurrentCourse.setValue(map);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
