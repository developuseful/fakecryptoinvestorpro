package app.useful.cryptocurrencytrackerandroidapp.ui.fragments.investdetail;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import app.useful.cryptocurrencytrackerandroidapp.R;
import app.useful.cryptocurrencytrackerandroidapp.common.Common;

import static android.content.ContentValues.TAG;


public class InvestDetailFragment extends Fragment {

    private InvestDetailFragmentViewModel investDetailFragmentViewModel;

    private TextView tvCoinSymbol;
    private TextView tvCoinName;
    private TextView tvInvestName;
    private TextView tvDateInvest;
    private TextView tvTimeInvest;
    private TextView tvCourseInvest;
    private TextView tvCurrentCourse;
    private TextView tvAmountInvest;
    private TextView tvProfitInvest;
    private TextView tvProfitInvestProcent;


    private ImageView ivCoinImg;

    private double currentCourseGlobal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_invest_detail, container, false);

        investDetailFragmentViewModel = ViewModelProviders.of(this).get(InvestDetailFragmentViewModel.class);
        investDetailFragmentViewModel.getCourse().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Double>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(HashMap<String, Double> stringDoubleMap) {
                String key = getArguments().getString("coinSymbol");
                key.toLowerCase();
                currentCourseGlobal = stringDoubleMap.get(key);
                //String currentCourseString = String.valueOf(Common.df3.format(currentCourseGlobal));
                //tvCurrentCourse.setText(currentCourseString + " $");

                // Current course init
                if (currentCourseGlobal < getArguments().getDouble("investCourse")){
                    tvCurrentCourse.setTextColor(Color.parseColor("#F44336"));
                } else {
                    tvCurrentCourse.setTextColor(Color.parseColor("#4CAF50"));
                }

                if (currentCourseGlobal <= 0.99){
                    tvCurrentCourse.setText(
                            String.valueOf(Common.df4.format(currentCourseGlobal) + " $"));
                }else if (currentCourseGlobal < 1){
                    tvCurrentCourse.setText(
                            String.valueOf(Common.df3.format(currentCourseGlobal)) + " $");
                }else if (currentCourseGlobal < 100){
                    tvCurrentCourse.setText(
                            String.valueOf(Common.df2.format(currentCourseGlobal)) + " $");
                } else {
                    String courseInvest = Common.df0.format(currentCourseGlobal);
                    int courseInvestInt = Integer.parseInt(courseInvest);
                    tvCurrentCourse.setText(String.format(Locale.CANADA_FRENCH, "%,d", courseInvestInt) + " $");
                }

                if (getProfit() < 0){
                    tvProfitInvest.setTextColor(Color.parseColor("#F44336"));
                    tvProfitInvestProcent.setTextColor(Color.parseColor("#F44336"));
                } else {
                    tvProfitInvest.setTextColor(Color.parseColor("#4CAF50"));
                    tvProfitInvestProcent.setTextColor(Color.parseColor("#4CAF50"));

                }
                tvProfitInvest.setText(getProfitStringFormated());

                tvProfitInvestProcent.setText(getProfitProcent());



                // Course invest init
                if (getArguments().getDouble("investCourse") <= 0.99){
                    tvCourseInvest.setText(
                            String.valueOf(Common.df4.format(getArguments().getDouble("investCourse"))) + " $");
                }else if (getArguments().getDouble("investCourse") < 1){
                    tvCourseInvest.setText(
                            String.valueOf(Common.df3.format(getArguments().getDouble("investCourse"))) + " $");
                }else if (getArguments().getDouble("investCourse") < 100){
                    tvCourseInvest.setText(
                            String.valueOf(Common.df2.format(getArguments().getDouble("investCourse"))) + " $");
                } else {
                    String courseInvest = Common.df0.format(getArguments().getDouble("investCourse"));
                    int courseInvestInt = Integer.parseInt(courseInvest);
                    tvCourseInvest.setText(String.format(Locale.CANADA_FRENCH, "%,d", courseInvestInt) + " $");
                }

            }
        });

        initField(root);
        return root;
    }

    private void initField(View root) {
        tvCoinSymbol = root.findViewById(R.id.invest_detail_coin_symbol);
        tvCoinSymbol.setText(getArguments().getString("coinSymbol"));

        tvCoinName = root.findViewById(R.id.invest_detail_coin_name);
        tvCoinName.setText(getArguments().getString("coinName"));

        tvInvestName = root.findViewById(R.id.invest_detail_invest_name);
        tvInvestName.setText(getArguments().getString("investName"));

        tvDateInvest = root.findViewById(R.id.invest_detail_date_invest);
        tvDateInvest.setText(Common.getDateFormatedForDetail(getArguments().getLong("investDate")));

        tvTimeInvest = root.findViewById(R.id.invest_detail_time);
        tvTimeInvest.setText(Common.getTimePassed(getArguments().getLong("investDate")));

        tvCourseInvest = root.findViewById(R.id.invest_detail_course_invest);

        tvCurrentCourse = root.findViewById(R.id.invest_detail_current_course);

        tvAmountInvest = root.findViewById(R.id.invest_detail_amount_invest);
        //tvAmountInvest.setText(String.valueOf(getArguments().getInt("investAmount")));
        tvAmountInvest.setText(String.format(Locale.CANADA_FRENCH, "%,d", getArguments().getInt("investAmount")));


        tvProfitInvest = root.findViewById(R.id.invest_detail_profit);

        tvProfitInvestProcent = root.findViewById(R.id.invest_detail_procent_profit);

        ivCoinImg = root.findViewById(R.id.invest_detail_coin_img);

        InputStream inputStream = null;
        try{

            String imgString = new StringBuilder()
                    .append(getArguments().getString("coinSymbol").toLowerCase())
                    .append(".png")
                    .toString();

            //inputStream = getContext().getAssets().open(imgString);
            //inputStream = root.getContext().getAssets().open("btc.png");
            inputStream = getResources().getAssets().open(imgString);
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

/*
        Picasso.with(root.getContext())
                .load(new StringBuilder(Common.imageUrl)
                        .append(getArguments().getString("coinSymbol").toLowerCase())
                        .append(".png")
                        .toString())
                .into(ivCoinImg);

 */
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

    private double getProfit(){
        double courseInvest = getArguments().getDouble("investCourse");
        int amountInvest = getArguments().getInt("investAmount");
        double profit = currentCourseGlobal * amountInvest / courseInvest - amountInvest;
        String profitString = String.valueOf(profit);
        return profit;
    }

    private String getProfitStringFormated(){
        double courseInvest = getArguments().getDouble("investCourse");
        int amountInvest = getArguments().getInt("investAmount");
        double profit = currentCourseGlobal * amountInvest / courseInvest - amountInvest;

        String profitString = String.valueOf(Common.df0.format(profit));

        String profitComposite = String.format(Locale.CANADA_FRENCH, "%,d", Integer.parseInt(profitString));

        return profitComposite;
    }

    private String getProfitProcent(){
        double courseInvest = getArguments().getDouble("investCourse");
        double profitProcent = currentCourseGlobal * 100 / courseInvest - 100;
        String proftProcentString = String.valueOf(Common.df2.format(profitProcent)) + "%";
        return proftProcentString;
    }

}