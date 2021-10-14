package app.useful.cryptocurrencytrackerandroidapp.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Investment.class}, version = 2)
public abstract class InvestDatabase extends RoomDatabase {

    private static InvestDatabase instance;

    public abstract InvestDao investDao();

    public static synchronized InvestDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    InvestDatabase.class, "invest_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private InvestDao investDao;

        public PopulateDbAsyncTask(InvestDatabase db){
            investDao = db.investDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //investDao.insert(new Investment("Title 1", 100, 1, 1, 0));
            //investDao.insert(new Investment("Title 2", 200, 2, 2, 0));
            //investDao.insert(new Investment("Title 3", 300, 3, 3, 0));

            return null;
        }
    }
}
