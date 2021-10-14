package app.useful.cryptocurrencytrackerandroidapp.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

import app.useful.cryptocurrencytrackerandroidapp.retrofit.CoinMarketCapService;
import app.useful.cryptocurrencytrackerandroidapp.retrofit.Model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddInvestmentViewModel extends AndroidViewModel {

    private InvestRepository repository;
    private LiveData<List<Investment>> allInvest;


    public AddInvestmentViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestRepository(application);
        allInvest = repository.getAllInvestments();
    }

    public void insert(Investment investment){
        repository.insert(investment);
    }

    public void update(Investment investment){
        repository.update(investment);
    }

    public void delete(Investment investment){
        repository.delete(investment);
    }

    public void deleteAllInvestments(){
        repository.deleteAllInvestments();
    }

    public LiveData<List<Investment>> getAllInvest(){
        return allInvest;
    }

}
