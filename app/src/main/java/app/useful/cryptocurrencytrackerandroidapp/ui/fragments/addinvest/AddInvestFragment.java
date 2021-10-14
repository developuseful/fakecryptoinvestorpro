package app.useful.cryptocurrencytrackerandroidapp.ui.fragments.addinvest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import app.useful.cryptocurrencytrackerandroidapp.R;
import app.useful.cryptocurrencytrackerandroidapp.common.Common;
import app.useful.cryptocurrencytrackerandroidapp.room.AddInvestmentViewModel;
import app.useful.cryptocurrencytrackerandroidapp.room.Investment;

import static android.content.ContentValues.TAG;
import static app.useful.cryptocurrencytrackerandroidapp.common.Common.getDate;


public class AddInvestFragment extends Fragment {

    private TextView tvCoinName;
    private TextView tvCoinPrice;
    private TextView tvDate;

    private TextView tvDetailCoinNameInvest;
    private TextView tvDetailCoinAmountInvest;

    private ImageView ivCoinSymbol;

    private Button btnAddInvest;
    private AddInvestmentViewModel addInvestmentViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_invest, container, false);
        initFun(root);

        tvCoinName.setText(getArguments().getString("name2"));

        tvCoinPrice.setText(String.valueOf(getArguments().getDouble("price2", 0)));

        if (getArguments().getDouble("price2") <= 0.009){
            tvCoinPrice.setText(
                    String.valueOf(Common.df5.format(getArguments().getDouble("price2")) + " $"));
        }else if (getArguments().getDouble("price2") <= 0.99){
            tvCoinPrice.setText(
                    String.valueOf(Common.df4.format(getArguments().getDouble("price2")) + " $"));
        }else if (getArguments().getDouble("price2") < 1){
            tvCoinPrice.setText(
                    String.valueOf(Common.df3.format(getArguments().getDouble("price2"))) + " $");
        }else if (getArguments().getDouble("price2") < 1000){
            tvCoinPrice.setText(
                    String.valueOf(Common.df2.format(getArguments().getDouble("price2"))) + " $");
        } else {
            String priceString = Common.df0.format(getArguments().getDouble("price2"));
            int priceInt = Integer.parseInt(priceString);
            tvCoinPrice.setText(String.format(Locale.CANADA_FRENCH, "%,d", priceInt) + " $");
        }

        /*
        Picasso.with(getContext())
                .load(new StringBuilder(Common.imageUrl)
                        .append(getArguments().getString("symbol2"))
                        //.append("btc")
                        .append(".png")
                        .toString())
                .into(ivCoinSymbol);
         */

        Picasso.with(getContext())
                .load(new StringBuilder(Common.imageUrlv2)
                        //.append(coinModel.symbol!!.toLowerCase())
                        .append(getArguments().getString("name2").toLowerCase())
                        .append(".png")
                        .toString())
                .into(ivCoinSymbol);


        InputStream inputStream = null;
        try{

            String imgString = new StringBuilder()
                    .append(getArguments().getString("symbol2").toLowerCase())
                    .append(".png")
                    .toString();

            //inputStream = getContext().getAssets().open(imgString);
            //inputStream = root.getContext().getAssets().open("btc.png");
            inputStream = getResources().getAssets().open(imgString);
            //inputStream = context.getAssets().open(imgString);
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            ivCoinSymbol.setImageDrawable(drawable);

            //detailImageMachine.setImageDrawable(drawable);
            //detailImageMachine.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }

        tvDate.setText(" " + Common.getDateFormated());

        btnAddInvest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addInvest(addInvestmentViewModel, root);
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG,"Back Button Pressed");
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(TAG,"home on backpressed");
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initFun(View root) {
        addInvestmentViewModel = ViewModelProviders.of(this).get(AddInvestmentViewModel.class);

        tvCoinName = root.findViewById(R.id.detail_coin_name);
        tvCoinPrice = root.findViewById(R.id.detail_coin_price);

        ivCoinSymbol = root.findViewById(R.id.detail_coin_img);
        tvDate = root.findViewById(R.id.detail_coin_date_text_view);

        tvDetailCoinNameInvest = root.findViewById(R.id.detail_coin_name_invest);
        tvDetailCoinAmountInvest = root.findViewById(R.id.detail_coin_amount_invest);

        btnAddInvest = root.findViewById(R.id.btn_add_invest);

    }


    private void addInvest(AddInvestmentViewModel addInvestmentViewModel, View root){
        String nameInvest = tvDetailCoinNameInvest.getText().toString();
        long dateInvest = getDate();
        String courseInvestString = tvCoinPrice.getText().toString();
        String amountInvestString = tvDetailCoinAmountInvest.getText().toString();

        if (courseInvestString.contentEquals("***")){
            Toast.makeText(getActivity(), "\n" +
                    "Currency exchange rate data not known, please check your internet connection", Toast.LENGTH_LONG).show();
            return;
        }

        if (nameInvest.trim().isEmpty() || amountInvestString.trim().isEmpty()){
            Toast.makeText(getActivity(), "Please insert a name and amount", Toast.LENGTH_LONG).show();
            return;
        }


        double courseInvest = getArguments().getDouble("price2", -1);
        String symbolInvest = getArguments().getString("symbol2");
        String coinNameInvest = getArguments().getString("name2");
        int amountInvest = Integer.parseInt(amountInvestString);

        Investment investment = new Investment(
                nameInvest.trim(),
                symbolInvest,
                coinNameInvest,
                dateInvest,
                courseInvest,
                amountInvest,
                0);
        addInvestmentViewModel.insert(investment);

        Toast.makeText(getActivity(), "Investment saved", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(root).navigate(R.id.navigation_investments);
    }
}