package com.example.kaarthiha.finaltreasurehunt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

public class question4 extends AppCompatActivity {

    TextView textView,tv1,tv2;
    double lat1,lat2,lat3,lat4,lat5,lot1,lot2,lot3,lot4,lot5;
    protected LocationManager lm;
    protected LocationListener ls;
    Button verify;
    ImageButton loca;
    public String lat="", lon="";
    public double ll, ln;
    String question,quest5,evntname,usrname,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        question=getIntent().getExtras().getString("ques4");
        lat4=getIntent().getExtras().getDouble("lati4");
        lot4=getIntent().getExtras().getDouble("longi4");


        quest5=getIntent().getExtras().getString("ques5");
        lat5=getIntent().getExtras().getDouble("lati5");
        lot5=getIntent().getExtras().getDouble("longi5");

        evntname=getIntent().getExtras().getString("eventname");
        usrname=getIntent().getExtras().getString("username");
        mobile=getIntent().getExtras().getString("mobile");

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
                    df = lt.ansloca(dd, ds, lat4, lot4);
                    Toast.makeText(getApplication(), "" + df, Toast.LENGTH_LONG).show();
                    if (df <= 20) {
                        MediaPlayer mp = MediaPlayer.create(question4.this, R.raw.correct);
                        mp.start();

                        TastyToast.makeText(question4.this, "correct", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                        Intent i = new Intent(question4.this, question5.class);

                        i.putExtra("ques5", quest5);
                        i.putExtra("lati5", lat5);
                        i.putExtra("longi5", lot5);

                        i.putExtra("username", usrname);
                        i.putExtra("eventname", evntname);
                        i.putExtra("mobile", mobile);

                        startActivity(i);
                    } else {

                        MediaPlayer mp = MediaPlayer.create(question4.this, R.raw.wrong);
                        mp.start();
                        TastyToast.makeText(question4.this, "wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    }
                }
                else {

                    MediaPlayer mp = MediaPlayer.create(question4.this, R.raw.wrong);
                    mp.start();
                    TastyToast.makeText(question4.this, "wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
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
        ap.setMessage("Do you wish to go back to previous question");
        ap.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(question4.this, homescreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });
        ap.setNegativeButton("No", null);
        ap.show();
    }
 }

