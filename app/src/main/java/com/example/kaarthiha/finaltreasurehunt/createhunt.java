package com.example.kaarthiha.finaltreasurehunt;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Date;

public class createhunt extends AppCompatActivity {

    EditText conm,q1,q2,q3,q4,q5;
    TextView tvla1,tvlo1,tvla2,tvlo2,tvla3,tvlo3,tvla4,tvlo4,tvla5,tvlo5;
    Button b,l1,l2,l3,l4,l5;
    DatePicker dp;
    protected LocationManager lm;
    protected LocationListener ls;
    public String lat="", lon="";
    public double ll, ln;
    String contest_name,thq1,thq2,thq3,thq4,thq5,thdate;
    String thla1,thla2,thla3,thla4,thla5,thlo1,thlo2,thlo3,thlo4,thlo5;
    String op;
    String mobile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createhunt);
        mobile=getIntent().getExtras().getString("mob");
        Toast.makeText(getApplication(),mobile,Toast.LENGTH_LONG).show();
        conm=(EditText)findViewById(R.id.editText8);
        q1=(EditText)findViewById(R.id.editText9);
        q2=(EditText)findViewById(R.id.editText10);
        q3=(EditText)findViewById(R.id.editText11);
        q4=(EditText)findViewById(R.id.editText12);
        q5=(EditText)findViewById(R.id.editText13);
        tvla1=(TextView)findViewById(R.id.textView12);
        tvlo1=(TextView)findViewById(R.id.textView13);
        tvla2=(TextView)findViewById(R.id.textView15);
        tvlo2=(TextView)findViewById(R.id.textView16);
        tvla3=(TextView)findViewById(R.id.textView18);
        tvlo3=(TextView)findViewById(R.id.textView19);
        tvla4=(TextView)findViewById(R.id.textView21);
        tvlo4=(TextView)findViewById(R.id.textView22);
        tvla5=(TextView)findViewById(R.id.textView24);
        tvlo5=(TextView)findViewById(R.id.textView25);
            b=(Button)findViewById(R.id.button4);
        l1=(Button)findViewById(R.id.button8);
        l2=(Button)findViewById(R.id.button9);
        l3=(Button)findViewById(R.id.button10);
        l4=(Button)findViewById(R.id.button11);
        l5=(Button)findViewById(R.id.button12);
        dp=(DatePicker)findViewById(R.id.datePicker);
        dp.setMinDate(System.currentTimeMillis()-1000);
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
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqltablecreatee();
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getloca();
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getloca2();
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getloca3();
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getloca4();
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getloca5();
            }
        });
    }

    public void getloca() {

        //noinspection ResourceType
        lm.requestLocationUpdates("gps", 5000, 0, ls);
        setloca();

    }

    public void setloca() {
        tvla1.setText(lat);
        tvlo1.setText(lon);
        lat="";
        lon="";
    }


    public void getloca2() {

        //noinspection ResourceType
        lm.requestLocationUpdates("gps", 5000, 0, ls);
        setloca2();

    }

    public void setloca2() {
        tvla2.setText(lat);
        tvlo2.setText(lon);
        lat="";
        lon="";
    }



    public void getloca3() {

        //noinspection ResourceType
        lm.requestLocationUpdates("gps", 5000, 0, ls);
        setloca3();

    }

    public void setloca3() {
        tvla3.setText(lat);
        tvlo3.setText(lon);
        lat="";
        lon="";
    }


    public void getloca4() {

        //noinspection ResourceType
        lm.requestLocationUpdates("gps", 5000, 0, ls);
        setloca4();

    }

    public void setloca4() {
        tvla4.setText(lat);
        tvlo4.setText(lon);
        lat="";
        lon="";
    }

    public void getloca5() {

        //noinspection ResourceType
        lm.requestLocationUpdates("gps", 5000, 0, ls);
        setloca5();

    }

    public void setloca5() {
        tvla5.setText(lat);
        tvlo5.setText(lon);
        lat="";
        lon="";
    }
    public void sqltablecreatee() {


      // mobile="99";
        contest_name=conm.getText().toString();
        int day=dp.getDayOfMonth();
        int month=dp.getMonth()+1;
        int year=dp.getYear();
        Toast.makeText(getApplication(),""+month,Toast.LENGTH_LONG).show();
      thdate = year+"-"+month+"-"+day;
        thq1=q1.getText().toString();
        thq2=q2.getText().toString();
        thq3=q3.getText().toString();
        thq4=q4.getText().toString();
        thq5=q5.getText().toString();

        thla1= tvla1.getText().toString();
        thlo1=tvlo1.getText().toString();

        thla2= tvla2.getText().toString();
        thlo2=tvlo2.getText().toString();

        thla3= tvla3.getText().toString();
        thlo3=tvlo3.getText().toString();


        thla4= tvla4.getText().toString();
        thlo4=tvlo4.getText().toString();

        thla5= tvla5.getText().toString();
        thlo5=tvlo5.getText().toString();





            bgtask bg=new bgtask();
            bg.execute(mobile,contest_name,thdate,thq1,thq2,thq3,thq4,thq5,thla1,thlo1,thla2,thlo2,thla3,thlo3,thla4,thlo4,thla5,thlo5);
            this.finish();



    }
    class bgtask extends AsyncTask<String,Void,String>
    {
        String qurl;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            qurl="http://thunt.96.lt/kaarthiha/questionupload.php";
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("success")) {
                super.onPostExecute(s);
                Toast.makeText(getApplication(), "event created successfully", Toast.LENGTH_LONG).show();

            }

            Toast.makeText(getApplication(), "event created successfully", Toast.LENGTH_LONG).show();
            System.out.println(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected String doInBackground(String... params) {

            String roll,contname,data,quest1,lat1,lot1,quest2,lat2,lot2,quest3,lat3,lot3,quest4,lat4,lot4,quest5,lat5,lot5;
            roll=params[0];
            contname=params[1];
            data=params[2];
            quest1=params[3];
            quest2=params[4];
            quest3=params[5];
            quest4=params[6];
            quest5=params[7];
            lat1=params[8];
            lot1=params[9];
            lat2=params[10];
            lot2=params[11];
            lat3=params[12];
            lot3=params[13];
            lat4=params[14];
            lot4=params[15];
            lat5=params[16];
            lot5=params[17];
            try {
                URL url = new URL(qurl);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("roll","UTF-8")+"="+URLEncoder.encode(roll,"UTF-8")+"&"+
                        URLEncoder.encode("contname","UTF-8")+"="+URLEncoder.encode(contname,"UTF-8")+"&"+
                        URLEncoder.encode("data","UTF-8")+"="+URLEncoder.encode(data,"UTF-8")+"&"+
                        URLEncoder.encode("quest1","UTF-8")+"="+URLEncoder.encode(quest1,"UTF-8")+"&"+
                        URLEncoder.encode("quest2","UTF-8")+"="+URLEncoder.encode(quest2,"UTF-8")+"&"+
                        URLEncoder.encode("quest3","UTF-8")+"="+URLEncoder.encode(quest3,"UTF-8")+"&"+
                        URLEncoder.encode("quest4","UTF-8")+"="+URLEncoder.encode(quest4,"UTF-8")+"&"+
                        URLEncoder.encode("quest5","UTF-8")+"="+URLEncoder.encode(quest5,"UTF-8")+"&"+
                        URLEncoder.encode("lat1","UTF-8")+"="+URLEncoder.encode(lat1,"UTF-8")+"&"+
                        URLEncoder.encode("lat2","UTF-8")+"="+URLEncoder.encode(lat2,"UTF-8")+"&"+
                        URLEncoder.encode("lat3","UTF-8")+"="+URLEncoder.encode(lat3,"UTF-8")+"&"+
                        URLEncoder.encode("lat4","UTF-8")+"="+URLEncoder.encode(lat4,"UTF-8")+"&"+
                        URLEncoder.encode("lat5","UTF-8")+"="+URLEncoder.encode(lat5,"UTF-8")+"&"+
                        URLEncoder.encode("lot1","UTF-8")+"="+URLEncoder.encode(lot1,"UTF-8")+"&"+
                        URLEncoder.encode("lot2","UTF-8")+"="+URLEncoder.encode(lot2,"UTF-8")+"&"+
                        URLEncoder.encode("lot3","UTF-8")+"="+URLEncoder.encode(lot3,"UTF-8")+"&"+
                        URLEncoder.encode("lot4","UTF-8")+"="+URLEncoder.encode(lot4,"UTF-8")+"&"+
                        URLEncoder.encode("lot5","UTF-8")+"="+URLEncoder.encode(lot5,"UTF-8");

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
}
