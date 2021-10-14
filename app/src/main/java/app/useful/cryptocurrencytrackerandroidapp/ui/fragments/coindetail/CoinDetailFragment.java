package app.useful.cryptocurrencytrackerandroidapp.ui.fragments.coindetail;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import app.useful.cryptocurrencytrackerandroidapp.R;
import app.useful.cryptocurrencytrackerandroidapp.common.Common;

import static android.content.ContentValues.TAG;


public class CoinDetailFragment extends Fragment {

    private FloatingActionButton fabAddInvest;
    private TextView tvCoinName;
    private TextView tvCoinSymbol;
    private TextView tvCoinPrice;

    private TextView tvPercent1h;
    private TextView tvPercent24h;
    private TextView tvPercent7d;
    private TextView tvPercent30d;
    private TextView tvPercent60d;
    private TextView tvPercent90d;

    private TextView tvMarketCap;
    private TextView tvVolume24h;
    private TextView tvDominance;

    private ImageView ivCoinImg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_coin_detail, container, false);

        initField(root);

        fabAddInvest = root.findViewById(R.id.coin_detail_btn_add_invest);
        fabAddInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name2", getArguments().getString("name"));
                bundle.putString("symbol2", getArguments().getString("symbol"));
                bundle.putDouble("price2", getArguments().getDouble("price"));

                Navigation.findNavController(root).navigate(R.id.add_invest_fragment, bundle);
            }
        });

        return root;
    }

    private void initField(View root){
        tvCoinName = root.findViewById(R.id.coin_detail_coin_name);
        tvCoinSymbol = root.findViewById(R.id.coin_detail_coin_symbol);
        tvCoinPrice = root.findViewById(R.id.coin_detail_price);

        tvPercent1h = root.findViewById(R.id.detail_coin_1h_percent);
        tvPercent24h = root.findViewById(R.id.detail_coin_24h_percent);
        tvPercent7d = root.findViewById(R.id.detail_coin_7d_percent);
        tvPercent30d = root.findViewById(R.id.detail_coin_30d_percent);
        tvPercent60d = root.findViewById(R.id.detail_coin_60d_percent);
        tvPercent90d = root.findViewById(R.id.detail_coin_90d_percent);

        tvMarketCap = root.findViewById(R.id.detail_coin_market_cap);
        tvVolume24h = root.findViewById(R.id.detail_coin_volume24h);
        tvDominance = root.findViewById(R.id.detail_coin_dominance);

        ivCoinImg = root.findViewById(R.id.coin_detail_coin_img);

        tvCoinName.setText(getArguments().getString("name"));
        tvCoinSymbol.setText(getArguments().getString("symbol"));


        //tvCoinPrice.setText(String.valueOf(getArguments().getDouble("price")));

        if (getArguments().getDouble("price") <= 0.009){
            tvCoinPrice.setText(
                    String.valueOf(Common.df5.format(getArguments().getDouble("price")) + " $"));
        }else if (getArguments().getDouble("price") <= 0.99){
            tvCoinPrice.setText(
                    String.valueOf(Common.df4.format(getArguments().getDouble("price")) + " $"));
        }else if (getArguments().getDouble("price") < 1){
            tvCoinPrice.setText(
                    String.valueOf(Common.df3.format(getArguments().getDouble("price"))) + " $");
        }else if (getArguments().getDouble("price") < 1000){
            tvCoinPrice.setText(
                    String.valueOf(Common.df2.format(getArguments().getDouble("price"))) + " $");
        } else {
            String priceString = Common.df0.format(getArguments().getDouble("price"));
            int priceInt = Integer.parseInt(priceString);
            tvCoinPrice.setText(String.format(Locale.CANADA_FRENCH, "%,d", priceInt) + " $");
        }

        tvPercent1h.setText(Common.df2.format(getArguments().getDouble("1h")) + "%");
        if (getArguments().getDouble("1h") < 0){
            tvPercent1h.setTextColor(Color.parseColor("#F44336"));
        }else {
            tvPercent1h.setTextColor(Color.parseColor("#4CAF50"));
        }

        tvPercent24h.setText(Common.df2.format(getArguments().getDouble("24h")) + "%");
        if (getArguments().getDouble("24h") < 0){
            tvPercent24h.setTextColor(Color.parseColor("#F44336"));
        }else {
            tvPercent24h.setTextColor(Color.parseColor("#4CAF50"));
        }

        tvPercent7d.setText(Common.df2.format(getArguments().getDouble("7d")) + "%");
        if (getArguments().getDouble("7d") < 0){
            tvPercent7d.setTextColor(Color.parseColor("#F44336"));
        }else {
            tvPercent7d.setTextColor(Color.parseColor("#4CAF50"));
        }

        tvPercent30d.setText(Common.df2.format(getArguments().getDouble("30d")) + "%");
        if (getArguments().getDouble("30d") < 0){
            tvPercent30d.setTextColor(Color.parseColor("#F44336"));
        }else {
            tvPercent30d.setTextColor(Color.parseColor("#4CAF50"));
        }

        tvPercent60d.setText(Common.df2.format(getArguments().getDouble("60d")) + "%");
        if (getArguments().getDouble("60d") < 0){
            tvPercent60d.setTextColor(Color.parseColor("#F44336"));
        }else {
            tvPercent60d.setTextColor(Color.parseColor("#4CAF50"));
        }

        tvPercent90d.setText(Common.df2.format(getArguments().getDouble("90d")) + "%");
        if (getArguments().getDouble("90d") < 0){
            tvPercent90d.setTextColor(Color.parseColor("#F44336"));
        }else {
            tvPercent90d.setTextColor(Color.parseColor("#4CAF50"));
        }

        tvMarketCap.setText(Common.df0.format(getArguments().getDouble("market_cap")) + " $");
        tvVolume24h.setText(Common.df0.format(getArguments().getDouble("volume24h")) + " $");
        tvDominance.setText(getArguments().getDouble("dominance") + "%");



        /*Picasso.with(root.getContext())
                .load(new StringBuilder(Common.imageUrl)
                        .append(getArguments().getString("symbol").toLowerCase())
                        .append(".png")
                        .toString())
                .into(ivCoinImg);

         */
        Picasso.with(root.getContext())
                .load(new StringBuilder(Common.imageUrlv2)
                        //.append(coinModel.symbol!!.toLowerCase())
                        .append(getArguments().getString("name").toLowerCase())
                        .append(".png")
                        .toString())
                .into(ivCoinImg);


        InputStream inputStream = null;
        try{

            String imgString = new StringBuilder()
                    .append(getArguments().getString("symbol").toLowerCase())
                    .append(".png")
                    .toString();

            //inputStream = getContext().getAssets().open(imgString);
            //inputStream = root.getContext().getAssets().open("btc.png");
            inputStream = getResources().getAssets().open(imgString);
            //inputStream = context.getAssets().open(imgString);
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            ivCoinImg.setImageDrawable(drawable);

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
}