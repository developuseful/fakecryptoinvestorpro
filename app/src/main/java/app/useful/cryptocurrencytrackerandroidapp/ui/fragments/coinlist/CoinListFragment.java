package app.useful.cryptocurrencytrackerandroidapp.ui.fragments.coinlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

import app.useful.cryptocurrencytrackerandroidapp.MainActivity;
import app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter.CurrencyRVAdapter;
import app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter.CurrencyRVModal;
import app.useful.cryptocurrencytrackerandroidapp.activities.AddInvestActivity;
import app.useful.cryptocurrencytrackerandroidapp.R;
import app.useful.cryptocurrencytrackerandroidapp.retrofit.CoinMarketCapService;
import app.useful.cryptocurrencytrackerandroidapp.retrofit.Model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CoinListFragment extends Fragment {

    private ProgressBar loadingPB;
    private EditText searchEdit;
    private RecyclerView currenciesRV;

    private CurrencyRVAdapter adapter;
    private ArrayList<CurrencyRVModal> currencyRVModalArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_coin_list, container, false);
        initFunc(root);
        return root;
    }

    private void initFunc(View root) {
        currenciesRV = root.findViewById(R.id.idRVCurrencies);
        loadingPB = root.findViewById(R.id.idPBLoading);

        // Инициализируем коллекцию для последующей передачи в адаптер
        currencyRVModalArrayList = new ArrayList<>();

        // Инициализируем адаптер, передаем в конструктор коллекцию и контекст
        //currencyRVAdapter = new CurrencyRVAdapter(currencyRVModalArrayList, root.getContext());
        adapter = new CurrencyRVAdapter(currencyRVModalArrayList, root.getContext());

        // Задаем лэйаут менеджер для рецайклера
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        currenciesRV.setLayoutManager(linearLayoutManager);

        // Задаем адаптер для рецайклера
        currenciesRV.setAdapter(adapter);

        // Получаем данные для спискового представления
        getCurrencyData(adapter);



        adapter.setOnItemClickListener(new CurrencyRVAdapter.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(CurrencyRVModal coin) {
                //navController.navigate(R.id.navigation_investments);
                /*
                Intent intent = new Intent(getActivity(), AddInvestActivity.class);
                intent.putExtra("name", coin.getName());
                intent.putExtra("symbol", coin.getSymbol());
                intent.putExtra("price", coin.getPrice());
                startActivity(intent);
                 */

                Bundle bundlePlus = new Bundle();
                bundlePlus.putString("name", coin.getName());
                bundlePlus.putString("symbol", coin.getSymbol());
                bundlePlus.putDouble("price", coin.getPrice());

                bundlePlus.putDouble("1h", coin.getPercent_change_1h());
                bundlePlus.putDouble("24h", coin.getPercent_change_24h());
                bundlePlus.putDouble("7d", coin.getPercent_change_7d());
                bundlePlus.putDouble("30d", coin.getPercent_change_30d());
                bundlePlus.putDouble("60d", coin.getPercent_change_60d());
                bundlePlus.putDouble("90d", coin.getPercent_change_90d());

                bundlePlus.putDouble("market_cap", coin.getMarket_cap());
                bundlePlus.putDouble("volume24h", coin.getVolume24h());
                bundlePlus.putDouble("dominance", coin.getDominance());

                Navigation.findNavController(root).navigate(R.id.coin_detail_fragment, bundlePlus);
            }
        });


    }

    private void getCurrencyData(CurrencyRVAdapter adapter) {

        loadingPB.setVisibility(View.VISIBLE);

        /*
        // Базовый урл
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
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
                        currencyRVModalArrayList.add(new CurrencyRVModal(
                                name,
                                symbol,
                                price,
                                percent_change_1h,
                                percent_change_24h,
                                percent_change_7d));
                    }
                    adapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Fail to extract json data..", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_LONG).show();
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
 */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinMarketCapService service = retrofit.create(CoinMarketCapService.class);

        Call<Result> call = service.search();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                loadingPB.setVisibility(View.GONE);
                Result result = (Result) response.body();

                for (int i = 0; i < result.getData().size(); i++){
                    String name = result.getData().get(i).getName();
                    String symbol = result.getData().get(i).getSymbol();

                    // Получаем значение типа double из объекта USD с именем price
                    double price = result.getData().get(i).getQuote().getUsd().getPrice();

                    double percent_change_1h = result.getData().get(i).getQuote().getUsd().getPercentChange1h();
                    double percent_change_24h = result.getData().get(i).getQuote().getUsd().getPercentChange24h();
                    double percent_change_7d = result.getData().get(i).getQuote().getUsd().getPercentChange7d();

                    double percent_change_30d = result.getData().get(i).getQuote().getUsd().getPercentChange30d();
                    double percent_change_60d = result.getData().get(i).getQuote().getUsd().getPercentChange60d();
                    double percent_change_90d = result.getData().get(i).getQuote().getUsd().getPercentChange90d();

                    double marketCap = result.getData().get(i).getQuote().getUsd().getMarketCap();
                    double volume24h = result.getData().get(i).getQuote().getUsd().getVolume24h();
                    double dominance = result.getData().get(i).getQuote().getUsd().getMarketCapDominance();

                    // Заполняем коллекцию эелементами CurrencyRVModal
                    currencyRVModalArrayList.add(new CurrencyRVModal(
                            name,
                            symbol,
                            price,
                            percent_change_1h,
                            percent_change_24h,
                            percent_change_7d,
                            percent_change_30d,
                            percent_change_60d,
                            percent_change_90d,
                            marketCap,
                            volume24h,
                            dominance
                            ));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }




}