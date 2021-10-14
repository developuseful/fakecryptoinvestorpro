package app.useful.cryptocurrencytrackerandroidapp.adapters.investmentsadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.useful.cryptocurrencytrackerandroidapp.R;
import app.useful.cryptocurrencytrackerandroidapp.common.Common;
import app.useful.cryptocurrencytrackerandroidapp.room.Investment;
import app.useful.cryptocurrencytrackerandroidapp.ui.fragments.investdetail.InvestDetailFragmentViewModel;


public class InvestmentAdapter extends RecyclerView.Adapter<InvestmentAdapter.InvestmentsHolder> {


    private List<Investment> investments = new ArrayList<>();
    private OnItemClickListener listener;

    private Context context;


    public void setLdCurrentCourse(HashMap<String, Double> ldCurrentCourse) {
        this.ldCurrentCourse = ldCurrentCourse;
    }

    private HashMap<String, Double> ldCurrentCourse;


    public InvestmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public InvestmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_investments, parent, false);
        return new InvestmentsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvestmentsHolder holder, int position) {
        Investment currentInvest = investments.get(position);

        holder.tvInvestCoinSymbol.setText(currentInvest.getInvestSymbol());
        holder.tvCoinName.setText(currentInvest.getInvestCoinName());
        holder.tvInvestNameItem.setText(currentInvest.getInvestName());

        String tvAmount = String.format(Locale.CANADA_FRENCH, "%,d", currentInvest.getInvestAmount());
        holder.tvInvestAmount.setText(tvAmount);

        holder.tvInvestTime.setText(getTimePassed(currentInvest.getInvestDate()));

       // double currentCourse =
        //String profit = String.valueOf(currentCourse);
//        holder.tvInvestProfit.setText(String.valueOf(ldCurrentCourse.get("BTC")));


        /*
        Picasso.with(context)
                .load(new StringBuilder(Common.imageUrl)
                        .append(currentInvest.getInvestSymbol().toLowerCase())
                        .append(".png")
                        .toString())
                .into(holder.ivInvestCoin);


        Picasso.with(context)
                .load(new StringBuilder(Common.imageUrlv2)
                        //.append(coinModel.symbol!!.toLowerCase())
                        .append(currentInvest.getInvestCoinName().toLowerCase())
                        .append(".png")
                        .toString())
                .into(holder.ivInvestCoin);
                */
        InputStream inputStream = null;
        try{

            String imgString = new StringBuilder()
                    .append(currentInvest.getInvestSymbol().toLowerCase())
                    .append(".png")
                    .toString();

            //inputStream = getContext().getAssets().open(imgString);
            //inputStream = root.getContext().getAssets().open("btc.png");
            //inputStream = getResources().getAssets().open(imgString);
            inputStream = context.getAssets().open(imgString);
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            holder.ivInvestCoin.setImageDrawable(drawable);

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
    public int getItemCount() {
        return investments.size();
    }

    public void setInvestments(List<Investment> investments){
        this.investments = investments;
        notifyDataSetChanged();
    }

    public Investment getInvestmentAt(int position){
        return investments.get(position);
    }


    class InvestmentsHolder extends RecyclerView.ViewHolder{

        private ImageView ivInvestCoin;

        private TextView tvInvestCoinSymbol;
        private TextView tvCoinName;

        private TextView tvInvestNameItem;
        private TextView tvInvestAmount;
        private TextView tvInvestTime;

        private TextView tvInvestProfit;


        public InvestmentsHolder(@NonNull View itemView) {
            super(itemView);

            tvInvestCoinSymbol = itemView.findViewById(R.id.investments_coin_symbol);
            tvCoinName = itemView.findViewById(R.id.investments_coin_name);

            tvInvestNameItem = itemView.findViewById(R.id.investments_name_item);
            tvInvestAmount = itemView.findViewById(R.id.investments_amount);
            tvInvestTime = itemView.findViewById(R.id.investments_time);

            tvInvestProfit = itemView.findViewById(R.id.investments_profit);

            ivInvestCoin = itemView.findViewById(R.id.investments_coin_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(investments.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Investment investment);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    private String getTimePassed(long timeAgo){

        long diff = System.currentTimeMillis() - timeAgo;

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);


        StringBuilder sb = new StringBuilder();
        if (diffDays == 0){
            if (diffHours == 0 ){
                if (diffMinutes == 0){
                    sb.append(diffSeconds + " sec ");
                } else {
                    sb.append(diffMinutes + " min ");
                }
            } else {
                sb.append(diffHours + " h ");
            }
        } else {
            sb.append(diffDays + " d ");
        }
        return sb.toString();
    }

}
