package app.useful.cryptocurrencytrackerandroidapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter.CurrencyRVModal;
import app.useful.cryptocurrencytrackerandroidapp.retrofit.CoinMarketCapService;

import app.useful.cryptocurrencytrackerandroidapp.retrofit.Model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentCourseService extends Service {

    public ArrayList<CurrencyRVModal> currencyRVModalArrayListStatic;

    public static double price;

    @Override
    public void onCreate() {
        super.onCreate();
        getCourseRetrofit();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*private void getCourse(){

        currencyRVModalArrayListStatic = null;

        // Базовый урл
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

        RequestQueue requestQueue = Volley.newRequestQueue(CurrentCourseService.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Из ответа получаем json массив с именем data
                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++){
                        // Получаем объект из массива dataArray по индексу и присваеваем переменной типа JSONObject
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");

                        // Получаем объект из объекта dataObject c именем quote
                        JSONObject quote = dataObj.getJSONObject("quote");

                        // Получаем объект из объекта quote c именем USD
                        JSONObject USD = quote.getJSONObject("USD");

                        // Получаем значение типа double из объекта USD с именем price
                        double price = USD.getDouble("price");

                        double percent_change_1h = USD.getDouble("percent_change_1h");
                        double percent_change_24h = USD.getDouble("percent_change_24h");
                        double percent_change_7d = USD.getDouble("percent_change_7d");


                        // Заполняем коллекцию эелементами CurrencyRVModal
                        currencyRVModalArrayListStatic.add(new CurrencyRVModal(
                                name,
                                symbol,
                                price,
                                percent_change_1h,
                                percent_change_24h,
                                percent_change_7d));
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Log.d("log2", "error");
                    //Toast.makeText(CurrentCourseService.this, "Fail to extract json data..", Toast.LENGTH_LONG).show();
                }
                Log.d("log2", "ok");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log2", "error");
                //Toast.makeText(CurrentCourseService.this, "Fail to get the data..", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","e3a58b3a-f257-4c74-935f-eab1715b634d");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

     */


   private void getCourseRetrofit(){

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

               //price = result.getData().get(0).getQuote().getUsd().getPrice();

               for (int i = 0; i < result.getData().size(); i++){
                   // Получаем объект из массива dataArray по индексу и присваеваем переменной типа JSONObject
                   //JSONObject dataObj = dataArray.getJSONObject(i);

                   String name = result.getData().get(i).getName();
                   String symbol = result.getData().get(i).getSymbol();

                   // Получаем объект из объекта dataObject c именем quote
                   //JSONObject quote = dataObj.getJSONObject("quote");

                   // Получаем объект из объекта quote c именем USD
                   //JSONObject USD = quote.getJSONObject("USD");

                   // Получаем значение типа double из объекта USD с именем price
                   double price = result.getData().get(i).getQuote().getUsd().getPrice();

                   //double percent_change_1h = USD.getDouble("percent_change_1h");
                   //double percent_change_24h = USD.getDouble("percent_change_24h");
                   //double percent_change_7d = USD.getDouble("percent_change_7d");


/*
                   // Заполняем коллекцию эелементами CurrencyRVModal
                   currencyRVModalArrayListStatic.add(new CurrencyRVModal(
                           name,
                           symbol,
                           price,
                           result.getData().get(i).getQuote().getUsd().getPercentChange1h(),
                           result.getData().get(i).getQuote().getUsd().getPercentChange24h(),
                           result.getData().get(i).getQuote().getUsd().getPercentChange7d()));

 */
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

}
