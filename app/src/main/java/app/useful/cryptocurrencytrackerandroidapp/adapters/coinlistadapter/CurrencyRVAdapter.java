package app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import app.useful.cryptocurrencytrackerandroidapp.common.Common;
import app.useful.cryptocurrencytrackerandroidapp.R;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(CurrencyRVModal coin);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    private ArrayList<CurrencyRVModal> currencyRVModalArrayList;
    private Context context;
    public static DecimalFormat df0 = new DecimalFormat("#");
    public static DecimalFormat df2 = new DecimalFormat("#.##");


    public CurrencyRVAdapter(ArrayList<CurrencyRVModal> currencyRVModalArrayList,
                             Context context) {
        this.currencyRVModalArrayList = currencyRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item, parent, false);
        return new CurrencyRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.ViewHolder holder, int position) {
        CurrencyRVModal currencyRVModal = currencyRVModalArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVModal.getName());
        holder.symbolTV.setText(currencyRVModal.getSymbol());

        /*
        int price;
        if (currencyRVModal.getPrice() < 100){
            holder.rateTv.setText(df2.format(currencyRVModal.getPrice()) + " $");
        }else {
            price = Integer.parseInt(df0.format(currencyRVModal.getPrice()));
            holder.rateTv.setText(String.format(Locale.CANADA_FRENCH, "%,d", price) + " $");
        }
         */

        if (currencyRVModal.getPrice() <= 0.99){
            holder.rateTv.setText(
                    String.valueOf(Common.df4.format(currencyRVModal.getPrice()) + " $"));
        }else if (currencyRVModal.getPrice() < 1){
            holder.rateTv.setText(
                    String.valueOf(Common.df3.format(currencyRVModal.getPrice()) + " $"));
        }else if (currencyRVModal.getPrice() < 100){
            holder.rateTv.setText(
                    String.valueOf(Common.df2.format(currencyRVModal.getPrice()) + " $"));
        } else {
            String courseString = Common.df0.format(currencyRVModal.getPrice());
            int courseInt = Integer.parseInt(courseString);
            holder.rateTv.setText(String.format(Locale.CANADA_FRENCH, "%,d", courseInt) + " $");
        }

        holder.percent_change_1hTV.setText(df2.format(currencyRVModal.getPercent_change_1h()) + "%");
        holder.percent_change_24hTV.setText(df2.format(currencyRVModal.getPercent_change_24h()) + "%");
        holder.percent_change_7dTV.setText(df2.format(currencyRVModal.getPercent_change_7d()) + "%");

        if (currencyRVModal.getPercent_change_1h() < 0){
            holder.percent_change_1hTV.setTextColor(Color.parseColor("#F44336"));
        }else {
            holder.percent_change_1hTV.setTextColor(Color.parseColor("#4CAF50"));
        }

        if (currencyRVModal.getPercent_change_24h() < 0){
            holder.percent_change_24hTV.setTextColor(Color.parseColor("#F44336"));
        }else {
            holder.percent_change_24hTV.setTextColor(Color.parseColor("#4CAF50"));
        }

        if (currencyRVModal.getPercent_change_7d() < 0){
            holder.percent_change_7dTV.setTextColor(Color.parseColor("#F44336"));
        }else {
            holder.percent_change_7dTV.setTextColor(Color.parseColor("#4CAF50"));
        }

/*
        Picasso.with(context)
                .load(new StringBuilder(Common.imageUrlv2)
                        //.append(coinModel.symbol!!.toLowerCase())
                        .append(currencyRVModal.getName().toLowerCase())
                        .append(".png")
                .toString())
                .into(holder.coinIcon);
 */

        InputStream inputStream = null;
        try{

            String imgString = new StringBuilder()
                    .append(currencyRVModal.getSymbol().toLowerCase())
                    .append(".png")
                    .toString();

            //inputStream = getContext().getAssets().open(imgString);
            //inputStream = root.getContext().getAssets().open("btc.png");
            //inputStream = getResources().getAssets().open(imgString);
            inputStream = context.getAssets().open(imgString);
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            holder.coinIcon.setImageDrawable(drawable);

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
        return currencyRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView currencyNameTV, symbolTV, rateTv, percent_change_1hTV, percent_change_24hTV, percent_change_7dTV;
        private ImageView coinIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyNameTV = itemView.findViewById(R.id.idTvCurrencyName);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTv = itemView.findViewById(R.id.idTvCurrencyRate);
            percent_change_1hTV = itemView.findViewById(R.id.percent_change_1h);
            percent_change_24hTV = itemView.findViewById(R.id.percent_change_24h);
            percent_change_7dTV = itemView.findViewById(R.id.percent_change_7d);
            coinIcon = itemView.findViewById(R.id.coinIcon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(currencyRVModalArrayList.get(position));
                    }
                }
            });
        }
    }


}
