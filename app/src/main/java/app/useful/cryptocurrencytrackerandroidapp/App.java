package app.useful.cryptocurrencytrackerandroidapp;

import android.app.Application;

import java.util.ArrayList;

import app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter.CurrencyRVModal;
import app.useful.cryptocurrencytrackerandroidapp.retrofit.CoinMarketCapService;
import app.useful.cryptocurrencytrackerandroidapp.retrofit.Model.Result;
import app.useful.cryptocurrencytrackerandroidapp.services.CurrentCourseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private ArrayList<CurrencyRVModal> currencyRVModalArrayList;

    public ArrayList<CurrencyRVModal> getCurrencyRVModalArrayList() {
        return currencyRVModalArrayList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        currencyRVModalArrayList = new ArrayList<>();
    }

    private void getCurrentCourse(){
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
                    String name = result.getData().get(i).getName();
                    String symbol = result.getData().get(i).getSymbol();

                    // Получаем значение типа double из объекта USD с именем price
                    double price = result.getData().get(i).getQuote().getUsd().getPrice();


                    // Заполняем коллекцию эелементами CurrencyRVModal
                    currencyRVModalArrayList.add(new CurrencyRVModal(
                            name,
                            symbol,
                            price,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

}
