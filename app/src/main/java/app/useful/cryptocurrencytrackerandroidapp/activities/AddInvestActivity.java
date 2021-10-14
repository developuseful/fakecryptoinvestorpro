package app.useful.cryptocurrencytrackerandroidapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.useful.cryptocurrencytrackerandroidapp.R;
import app.useful.cryptocurrencytrackerandroidapp.common.Common;
import app.useful.cryptocurrencytrackerandroidapp.room.AddInvestmentViewModel;
import app.useful.cryptocurrencytrackerandroidapp.room.Investment;

import static app.useful.cryptocurrencytrackerandroidapp.common.Common.getDate;

public class AddInvestActivity extends AppCompatActivity {

    private TextView tvCoinName;
    private TextView tvCoinPrice;
    private TextView tvDate;

    private TextView tvDetailCoinNameInvest;
    private TextView tvDetailCoinAmountInvest;

    private ImageView ivCoinSymbol;

    private Button btnAddInvest;
    private AddInvestmentViewModel addInvestmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coin);

        initFun();
        Intent intent = getIntent();

        tvCoinName.setText(intent.getStringExtra("name"));
        tvCoinPrice.setText(String.valueOf(intent.getDoubleExtra("price", 1)));

        Picasso.with(this)
                .load(new StringBuilder(Common.imageUrl)
                        //.append(coinModel.symbol!!.toLowerCase())
                        .append(intent.getStringExtra("symbol"))
                        .append(".png")
                        .toString())
                .into(ivCoinSymbol);

        tvDate.setText(" " + Common.getDateFormated());

        btnAddInvest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addInvest(intent, addInvestmentViewModel);
            }
        });

    }

    private void initFun() {
        addInvestmentViewModel = ViewModelProviders.of(this).get(AddInvestmentViewModel.class);

        tvCoinName = findViewById(R.id.detail_coin_name);
        tvCoinPrice = findViewById(R.id.detail_coin_price);

        ivCoinSymbol = findViewById(R.id.detail_coin_img);
        tvDate = findViewById(R.id.detail_coin_date_text_view);

        tvDetailCoinNameInvest = findViewById(R.id.detail_coin_name_invest);
        tvDetailCoinAmountInvest = findViewById(R.id.detail_coin_amount_invest);

        btnAddInvest = findViewById(R.id.btn_add_invest);

    }


    private void addInvest(Intent intent, AddInvestmentViewModel addInvestmentViewModel){
        String nameInvest = tvDetailCoinNameInvest.getText().toString();
        long dateInvest = getDate();
        String courseInvestString = tvCoinPrice.getText().toString();
        String amountInvestString = tvDetailCoinAmountInvest.getText().toString();

        if (courseInvestString.contentEquals("***")){
            Toast.makeText(this, "\n" +
                    "Currency exchange rate data not known, please check your internet connection", Toast.LENGTH_LONG).show();
            return;
        }

        if (nameInvest.trim().isEmpty() || amountInvestString.trim().isEmpty()){
            Toast.makeText(this, "Please insert a name and amount", Toast.LENGTH_LONG).show();
            return;
        }

        double courseInvest = intent.getDoubleExtra("price", 1);
        String symbolInvest = intent.getStringExtra("symbol");
        String coinNameInvest = intent.getStringExtra("name");
        int amountInvest = Integer.parseInt(amountInvestString);

        Investment investment = new Investment(
                nameInvest,
                symbolInvest,
                coinNameInvest,
                dateInvest,
                courseInvest,
                amountInvest,
                0);
        addInvestmentViewModel.insert(investment);

        //Toast.makeText(AddInvestActivity.this, "Investment saved", Toast.LENGTH_SHORT).show();
        finish();
    }



}