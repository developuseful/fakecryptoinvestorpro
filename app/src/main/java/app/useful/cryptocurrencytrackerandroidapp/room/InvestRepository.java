package app.useful.cryptocurrencytrackerandroidapp.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InvestRepository {

    private InvestDao investDao;
    private LiveData<List<Investment>> allInvestment;

    public InvestRepository (Application application){
        InvestDatabase database = InvestDatabase.getInstance(application);
        investDao = database.investDao();
        allInvestment = investDao.getAllInvest();
    }

    public void insert(Investment investment){
        new InsertInvestmentAsyncTask(investDao).execute(investment);
    }

    public void update(Investment investment){
        new UpdateInvestmentAsyncTask(investDao).execute(investment);
    }

    public void delete(Investment investment){
        new DeleteInvestmentAsyncTask(investDao).execute(investment);
    }

    public void deleteAllInvestments(){
        new DeleteAllInvestmentsAsyncTask(investDao).execute();
    }

    public LiveData<List<Investment>> getAllInvestments(){
        return allInvestment;
    }

    private static class InsertInvestmentAsyncTask extends AsyncTask<Investment, Void, Void> {

        private InvestDao investDao;

        private InsertInvestmentAsyncTask(InvestDao investDao){
            this.investDao = investDao;
        }

        @Override
        protected Void doInBackground(Investment... investments) {
            investDao.insert(investments[0]);
            return null;
        }
    }

    private static class UpdateInvestmentAsyncTask extends AsyncTask<Investment, Void, Void>{

        private InvestDao investDao;

        private UpdateInvestmentAsyncTask(InvestDao investDao){
            this.investDao = investDao;
        }

        @Override
        protected Void doInBackground(Investment... investments) {
            investDao.update(investments[0]);
            return null;
        }
    }

    private static class DeleteInvestmentAsyncTask extends AsyncTask<Investment, Void, Void>{

        private InvestDao investDao;

        private DeleteInvestmentAsyncTask(InvestDao investDao){
            this.investDao = investDao;
        }

        @Override
        protected Void doInBackground(Investment... investments) {
            investDao.delete(investments[0]);
            return null;
        }
    }

    private static class DeleteAllInvestmentsAsyncTask extends AsyncTask<Void, Void, Void>{

        private InvestDao investDao;

        private DeleteAllInvestmentsAsyncTask(InvestDao investDao){
            this.investDao = investDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            investDao.deleteAllInvest();
            return null;
        }
    }
}
