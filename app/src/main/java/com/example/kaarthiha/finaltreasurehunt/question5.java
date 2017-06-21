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
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

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

public class question5 extends AppCompatActivity {

    TextView textView,tv1,tv2;
    double lat1,lat2,lat3,lat4,lat5,lot1,lot2,lot3,lot4,lot5;
    protected LocationManager lm;
    protected LocationListener ls;
    Button verify;
    ImageButton loca;
    public String lat="", lon="",op;
    public double ll, ln;
    String question,eventname,username,mobilenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        eventname=getIntent().getExtras().getString("eventname");
        username=getIntent().getExtras().getString("username");
        mobilenumber=getIntent().getExtras().getString("mobile");

        Toast.makeText(getApplication(),""+eventname+"\t"+username+"\t"+mobilenumber,Toast.LENGTH_LONG).show();

        question=getIntent().getExtras().getString("ques5");
        lat5=getIntent().getExtras().getDouble("lati5");
        lot5=getIntent().getExtras().getDouble("longi5");
        textView=(TextView)findViewById(R.id.textView5);
        loca=(ImageButton)findViewById(R.id.imageButton);
        verify=(Button)findViewById(R.id.button5);
        tv1=(TextView)findViewById(R.id.textView7);
        tv2=(TextView)findViewById(R.id.textView9);
        textView.setText(question);
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
        back bg = new back();
        bg.execute(eventname,mobilenumber,username);
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
                if (!tv1.getText().toString().equals("") && !tv2.getText().toString().equals("") && !tv1.getText().toString().equals("Small Text") && !tv2.getText().toString().equals("Small Text")) {
                    double dd = Double.parseDouble(tv1.getText().toString());
                    double ds = Double.parseDouble(tv2.getText().toString());
                    df = lt.ansloca(dd, ds, lat5, lot5);
                    Toast.makeText(getApplication(), "" + df, Toast.LENGTH_LONG).show();

                    if (df <= 20) {
                        MediaPlayer mp = MediaPlayer.create(question5.this, R.raw.correct);
                        mp.start();

                        TastyToast.makeText(question5.this, "correct", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();


                        Intent i = new Intent(question5.this, finallast.class);


                        startActivity(i);
                    } else {

                        MediaPlayer mp = MediaPlayer.create(question5.this, R.raw.wrong);
                        mp.start();
                        TastyToast.makeText(question5.this, "wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    }
                }
                else {

                    MediaPlayer mp = MediaPlayer.create(question5.this, R.raw.wrong);
                    mp.start();
                    TastyToast.makeText(question5.this, "wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                }

            }
        });



    }

    class back extends AsyncTask<String,Void,String>
    {
        String purl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            purl="http://thunt.96.lt/kaarthiha/particont.php";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplication(), "finishd ", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected String doInBackground(String... params) {

            String contestname,username,mobilenumber;
            contestname=params[0];
            username=params[2];
            mobilenumber=params[1];

            try {
                URL url = new URL(purl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("mobilenumber", "UTF-8") + "=" + URLEncoder.encode(mobilenumber, "UTF-8") + "&" +
                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("contestname", "UTF-8") + "=" + URLEncoder.encode(contestname, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream ip=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ip,"iso-8859-1"));
                op=bufferedReader.readLine();

                bufferedReader.close();
                ip.close();

                httpURLConnection.disconnect();
                return op;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void setloca() {
        tv1.setText(lat);
        tv2.setText(lon);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ap = new AlertDialog.Builder(this);
        ap.setTitle("Closing Activity");
        ap.setMessage("Do you wish to close the game");
        ap.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(question5.this, homescreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });
        ap.setNegativeButton("No", null);
        ap.show();
    }
    }
