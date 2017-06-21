package com.example.kaarthiha.finaltreasurehunt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class homescreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
String ussrname="",imagebackstr;
    TextView tv1;
    String mob="";
    int i=0;
    String json_str,JSON_STRING="";
    ContactAdapter contactAdapter;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListView listView,list1;
    String clkname="";
    String alredmob="";
    int temp=0;
    Bitmap bit;
    ImageView im5;
    TextView tvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ussrname=getIntent().getExtras().getString("name");
        mob=getIntent().getExtras().getString("mob");

        Toast.makeText(getApplication(),""+ussrname,Toast.LENGTH_LONG).show();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 10);

            }
        }

        listView=(ListView)findViewById(R.id.listview);
        contactAdapter=new ContactAdapter(this,R.layout.row_layout);
        listView.setAdapter(contactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clkname = ((TextView) view.findViewById(R.id.tx_name)).getText().toString();
                Toast.makeText(getApplication(), clkname, Toast.LENGTH_LONG).show();


                bgtask b = new bgtask();
                b.execute(clkname, mob);

            }
        });

        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        final TextView textView=(TextView)findViewById(R.id.textView);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh2, R.color.refresh3);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        getdata();
                        parselist();


                    }
                }, 3000);

            }
        });



    }
        class uptsk extends AsyncTask<String,Void,String>
        {
            String name,mobile,cont,contulaurl;

            @Override
            protected void onPreExecute() {
                //super.onPreExecute();
                contulaurl="http://thunt.96.lt/kaarthiha/insertcont.php";
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

               cont=params[0];
                name=params[1];
                mobile=params[2];
                URL url= null;
                try {
                    url = new URL(contulaurl);

                    HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data_string= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("mob_no", "UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
                            URLEncoder.encode("cont", "UTF-8")+"="+URLEncoder.encode(cont,"UTF-8");
                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    StringBuilder str1=new StringBuilder();

                    while((alredmob=reader.readLine())!=null){

                        str1=str1.append(alredmob+"\n");


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
    class bgtask extends AsyncTask<String,Void,String>
    {
        String name,mobile,irukaurl;


        @Override
        protected void onPreExecute() {
          //  super.onPreExecute();
                irukaurl="http://thunt.96.lt/kaarthiha/irukaevent.php";
        }

        @Override
        protected void onPostExecute(String s) {
            String str=s,mi;
            int c=0;
            String name="";
            // tv1.setText(str);
            try {
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray=jsonObject.getJSONArray("server_response");
                while(c<jsonArray.length())
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(c);
                    name=jsonObject1.getString("name");


                    c++;


                }

                if(!name.equals(""))
                {
                    Toast.makeText(getApplication(),"It seems You have already register!!!!",Toast.LENGTH_LONG).show();
                    temp=1;

                }
                else
                {
                    Toast.makeText(getApplication(),"Register panala",Toast.LENGTH_LONG).show();

                    temp=0;

                    uptsk pp = new uptsk();
                    pp.execute(clkname,ussrname,mob);
                    Intent i = new Intent(homescreen.this, eventrule.class);
                    i.putExtra("eventname", clkname);
                    i.putExtra("username", ussrname);
                    i.putExtra("mobile", mob);
                    startActivity(i);

                }


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

            name=params[0];
            mobile=params[1];

            URL url= null;
            try {
                url = new URL(irukaurl);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("mob_no", "UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder str1=new StringBuilder();

                while((alredmob=reader.readLine())!=null){

                    str1=str1.append(alredmob+"\n");


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

    public void parselist() {

        contactAdapter.list.clear();


        try {
            jsonObject=new JSONObject(JSON_STRING);
            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");
            String name;
            while (count<jsonArray.length())
            {
                JSONObject jo = jsonArray.getJSONObject(count);
                name=jo.getString("name");
                Contacts contacts= new Contacts(name);
                contactAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getdata() {

        background bg = new background();
        bg.execute();
    }

    class background extends AsyncTask<Void,Void,String> {
        String returl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            returl = "http://thunt.96.lt/kaarthiha/availcont.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSON_STRING=s;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(returl);
                HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();

                InputStream is = htpcon.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(is));

                StringBuilder sbr = new StringBuilder();

                while ((json_str = bfr.readLine()) != null) {
                    sbr.append(json_str + "\n");
                }

                bfr.close();
                is.close();
                htpcon.disconnect();
                return sbr.toString().trim();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case 10:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getApplication(),"permission granted",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homescreen, menu);
         tv1=(TextView)findViewById(R.id.runname);
        tv1.setText("" + ussrname);
        tv1.setTextSize(20);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {

            getdata();
            parselist();

            return true;
        }

        if(id==R.id.action_exit)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.crehunt) {
            Intent i = new Intent(homescreen.this,createhunt.class);
            i.putExtra("mob", mob);
            startActivity(i);

        } else if (id == R.id.youhunt) {
            Intent i = new Intent(homescreen.this,youhunt.class);
            i.putExtra("name",ussrname);
            startActivity(i);

        } else if (id == R.id.helpisn) {

            Intent i = new Intent(homescreen.this,help.class);
            startActivity(i);

        } else if (id == R.id.share) {
            share();
        }
        else if (id == R.id.feed) {
           feed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void feed() {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("email"));
        String s[] = {"arumugam.dhamu@gmail.com","arumugam.dhamu97@gmail.com"};
        i.putExtra(Intent.EXTRA_EMAIL,s);
        i.putExtra(Intent.EXTRA_SUBJECT,"TREASURE-HUNT FEEDBACK");
        i.putExtra(Intent.EXTRA_TEXT, "please mention your device name and model!!");
        i.setType("message/rfc822");
        Intent chooser = Intent.createChooser(i,"Launch Email");
        startActivity(chooser);

    }

    public void share() {

        Intent shr=new Intent(Intent.ACTION_SEND);
        shr.setType("text/plain");
        shr.putExtra(Intent.EXTRA_TEXT, "Download and enjoy your lovable hunts" + "\n" + "https://play.google.com/TREASURE-HUNT");
        startActivity(Intent.createChooser(shr,"Share Via"));

        startActivity(shr);
    }
}
