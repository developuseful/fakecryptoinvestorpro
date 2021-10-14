package app.useful.cryptocurrencytrackerandroidapp.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import app.useful.cryptocurrencytrackerandroidapp.MainActivity;
import app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter.CurrencyRVAdapter;
import app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter.CurrencyRVModal;

public class Common {

    public static final String imageUrl = "https://res.cloudinary.com/dxi90ksom/image/upload/";

    public static final String imageUrlv2 = "https://i-invdn-com.investing.com/ico_flags/80x80/v32/";

    public static final String imageUrlv3 = "https://myfin.by/images/cryptoCurrency/";

    public static final String imageUrlv4 = "https://www.blockchain.com/explorer-frontend/_next/image?url=https%3A%2F%2Fwww.cryptocompare.com%2Fmedia%2F37746346%2F";

    public static final String imageUrlR = "R.drawable.";

    //xlm.png&w=48&q=75


    public static long getDate(){
        Date c = Calendar.getInstance().getTime();
        return c.getTime();
    }

    public static String getDateFormated(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getDateFormatedForDetail(double date){
        SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault());
        String formattedDate = df.format(date);

        return formattedDate;
    }

   public static String getTimePassed(long timeAgo){

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
    public static DecimalFormat df0 = new DecimalFormat("#");
    public static DecimalFormat df2 = new DecimalFormat("#.##");
    public static DecimalFormat df3 = new DecimalFormat("#.###");
    public static DecimalFormat df4 = new DecimalFormat("#.####");
    public static DecimalFormat df5 = new DecimalFormat("#.#####");





}
