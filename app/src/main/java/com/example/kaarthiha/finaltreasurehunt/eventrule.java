package com.example.kaarthiha.finaltreasurehunt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class eventrule extends AppCompatActivity implements DialogInterface.OnClickListener {
TextView textView,tv1,tv2;
    String contestname,thq1,thq2,thq3,thq4,thq5,JSON_STRING="";
    double lat1,lat2,lat3,lat4,lat5,lot1,lot2,lot3,lot4,lot5;
    protected LocationManager lm;
    protected LocationListener ls;
    Button verify;
    ImageButton loca;
    public String lat="", lon="";
    public double ll, ln;

    String usrname,evntname,mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventrule);

        contestname=getIntent().getExtras().getString("eventname");
        evntname=getIntent().getExtras().getString("eventname");
        usrname=getIntent().getExtras().getString("username");
        mobile=getIntent().getExtras().getString("mobile");
        textView=(TextView)findViewById(R.id.textView5);
        loca=(ImageButton)findViewById(R.id.imageButton);
        verify=(Button)findViewById(R.id.button5);
        tv1=(TextView)findViewById(R.id.textView7);
        tv2=(TextView)findViewById(R.id.textView9);
    AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("please do no go back during the event,check the internet is available incase if it is not you will be eliminated");
        ad.setIcon(R.drawable.logo);
        ad.setTitle("Terms and Conditions");
        ad.setPositiveButton("Yes", this);
        ad.setNegativeButton("No", this);
        ad.setCancelable(false);
        ad.create();
        ad.show();

        background bg = new background();
        bg.execute(contestname);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        ls = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {




                ll=location.getLatitude();
                ln=location.getLongitude();
                lat=String.valueOf(ll);
                lon=String.valueOf(ln);

                //noinspection ResourceType
                lm.removeUpdates(ls);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        //noinspection ResourceType
        lm.requestLocationUpdates("gps", 5000, 0, ls);

        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //noinspection ResourceType
                lm.requestLocationUpdates("gps", 5000, 0, ls);
                setloca();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double df;
                Latilongiverify lt = new Latilongiverify();
                if(!tv1.getText().toString().equals("") && !tv2.getText().toString().equals("") &&!tv1.getText().toString().equals("Small Text") && !tv2.getText().toString().equals("Small Text") ) {
                    double dd = Double.parseDouble(tv1.getText().toString());
                    double ds = Double.parseDouble(tv2.getText().toString());
                    df = lt.ansloca(dd, ds, lat1, lot1);
                    Toast.makeText(getApplication(), "" + df, Toast.LENGTH_LONG).show();
                    if (df <= 20) {
                        MediaPlayer mp = MediaPlayer.create(eventrule.this, R.raw.correct);
                        mp.start();
                        Toast.makeText(getApplication(), "" + df, Toast.LENGTH_LONG).show();
                        TastyToast.makeText(eventrule.this, "correct", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                        Intent i = new Intent(eventrule.this, question2.class);

                        i.putExtra("ques2", thq2);
                        i.putExtra("lati2", lat2);
                        i.putExtra("longi2", lot2);


                        i.putExtra("ques3", thq3);
                        i.putExtra("lati3", lat3);
                        i.putExtra("longi3", lot3);


                        i.putExtra("ques4", thq4);
                        i.putExtra("lati4", lat4);
                        i.putExtra("longi4", lot4);

                        i.putExtra("ques5", thq5);
                        i.putExtra("lati5", lat5);
                        i.putExtra("longi5", lot5);

                        i.putExtra("username", usrname);
                        i.putExtra("eventname", evntname);
                        i.putExtra("mobile", mobile);

                        startActivity(i);
                    } else {

                        MediaPlayer mp = MediaPlayer.create(eventrule.this, R.raw.wrong);
                        mp.start();
                        TastyToast.makeText(eventrule.this, "wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    }
                }
                else
                {
                    MediaPlayer mp = MediaPlayer.create(eventrule.this, R.raw.wrong);
                    mp.start();
                    TastyToast.makeText(eventrule.this, "wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                }
            }
        });

    }

    public void setloca() {
        tv1.setText(lat);
        tv2.setText(lon);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ap = new AlertDialog.Builder(this);
        ap.setTitle("Closing Activity");
        ap.setMessage("Are you sure want to close the game");
        ap.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(eventrule.this, homescreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);

            }
        });
        ap.setNegativeButton("No", null);
        ap.show();
    }

    class background extends AsyncTask<String,Void,String>
    {
        String qusansurl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            qusansurl="http://thunt.96.lt/kaarthiha/quesans.php";
        }

        @Override
        protected void onPostExecute(String s) {
            String str=s;

            int c =0;

            try {
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray=jsonObject.getJSONArray("server_response");
                while(c<jsonArray.length())
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(c);
                    thq1=jsonObject1.getString("ques1");
                    thq2=jsonObject1.getString("ques2");
                    thq3=jsonObject1.getString("ques3");
                    thq4=jsonObject1.getString("ques4");
                    thq5=jsonObject1.getString("ques5");

                    lat1=jsonObject1.getDouble("thlat1");
                    lot1=jsonObject1.getDouble("thlot1");


                    lat2=jsonObject1.getDouble("thlat2");
                    lot2=jsonObject1.getDouble("thlot2");

                    lat3=jsonObject1.getDouble("thlat3");
                    lot3=jsonObject1.getDouble("thlot3");


                    lat4=jsonObject1.getDouble("thlat4");
                    lot4=jsonObject1.getDouble("thlot4");


                    lat5=jsonObject1.getDouble("thlat5");
                    lot5=jsonObject1.getDouble("thlot5");

                    c++;
                }
                textView.setText(thq1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected String doInBackground(String... params) {
            String contname=params[0];
            URL url= null;
            try {
                url = new URL(qusansurl);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("contname", "UTF-8")+"="+URLEncoder.encode(contname,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder str1=new StringBuilder();

                while((JSON_STRING=reader.readLine())!=null){

                    str1=str1.append(JSON_STRING+"\n");


                }
                reader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                return str1.toString();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which  The button that was clicked (e.g.
     *               {@link DialogInterface#BUTTON1}) or the position
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which)
        {
            case DialogInterface.BUTTON_POSITIVE :
                    TastyToast.makeText(eventrule.this,"Events Starts",TastyToast.LENGTH_LONG,TastyToast.INFO);
                break;


            case DialogInterface.BUTTON_NEGATIVE :
                this.finish();
                break;
        }

    }
}
