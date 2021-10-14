package app.useful.cryptocurrencytrackerandroidapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InvestDao {

    @Insert
    void insert(Investment investment);

    @Update
    void update(Investment investment);

    @Delete
    void delete(Investment investment);

    @Query("DELETE FROM invest_table")
    void deleteAllInvest();

    @Query("SELECT * FROM invest_table")
    LiveData<List<Investment>> getAllInvest();
}
