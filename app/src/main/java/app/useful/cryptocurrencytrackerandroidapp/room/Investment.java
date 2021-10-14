package app.useful.cryptocurrencytrackerandroidapp.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "invest_table")
public class Investment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "invest_name")
    private String investName;

    @ColumnInfo(name = "invest_symbol")
    private String investSymbol;

    @ColumnInfo(name = "invest_coin_name")
    private String investCoinName;

    @ColumnInfo(name = "invest_date")
    private long investDate;

    @ColumnInfo(name = "invest_course")
    private double investCourse;

    @ColumnInfo(name = "invest_amount")
    private int investAmount;

    @ColumnInfo(name = "invest_current_course")
    private double currentCourse;


    public Investment(String investName, String investSymbol, String investCoinName, long investDate, double investCourse, int investAmount, double currentCourse) {
        this.investName = investName;
        this.investSymbol = investSymbol;
        this.investCoinName = investCoinName;
        this.investDate = investDate;
        this.investCourse = investCourse;
        this.investAmount = investAmount;
        this.currentCourse = currentCourse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getInvestName() {
        return investName;
    }

    public String getInvestSymbol() {
        return investSymbol;
    }

    public String getInvestCoinName() {
        return investCoinName;
    }

    public long getInvestDate() {
        return investDate;
    }

    public double getInvestCourse() {
        return investCourse;
    }

    public int getInvestAmount() {
        return investAmount;
    }

    public double getCurrentCourse() {
        return currentCourse;
    }


}
