package com.example.kaarthiha.finaltreasurehunt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class signup extends AppCompatActivity {

    Button sup,imbut;
    EditText email,name,mob,pwd;
    String alredmob="";
    int finalq=0,temp=0;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS=0;
    boolean net;
    Session session=null;
    ProgressDialog pgd = null;
    Context context=null;
    String rec,sub,msg;
    public Uri filepath;
    public Bitmap bitmap;
    public ImageView imageView;
    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        context=this;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));
            email=(EditText)findViewById(R.id.editText3);
            name=(EditText)findViewById(R.id.editText4);
            pwd=(EditText)findViewById(R.id.editText5);
            mob=(EditText)findViewById(R.id.editText6);
            sup=(Button)findViewById(R.id.button3);




        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net = isOnline();

                if (net != false) {
                    String ma, mo;
                    ma = email.getText().toString();
                    mo = mob.getText().toString();
                    if (ma.contains("@")) {

                        if (mo.length() == 10) {

                            alredy();

                            register();

                            mail();

                        } else {

                            Toast.makeText(getApplication(), "enter valid mobile number ", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplication(), "enter valid email address", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Network Unavailable", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }


            }
        });

    }



    public void alredy() {
        String mobile;
        mobile=mob.getText().toString();
        bgnum bg = new bgnum();
        bg.execute(mobile);



    }
    class bgnum extends AsyncTask<String,Void,String>
    {
        String alredurl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alredurl="http://thunt.96.lt/kaarthiha/alreadyregister.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
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
                    Toast.makeText(getApplication(),"successful!!!!",Toast.LENGTH_LONG).show();
                    finalq=1;
                    temp=0;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        @Override
        protected String doInBackground(String... params) {
            String mob_no=params[0];
            URL url= null;
            try {
                url = new URL(alredurl);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("mob_no", "UTF-8")+"="+URLEncoder.encode(mob_no,"UTF-8");
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

    public void register() {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,60,byteArrayOutputStream);
//        byte[] imBytes = byteArrayOutputStream.toByteArray();
//        String imstr= Base64.encodeToString(imBytes,Base64.URL_SAFE);
        String nm,em,mobnum,passwd,ck="";
        em=email.getText().toString();
        nm=name.getText().toString();
        passwd=pwd.getText().toString();
        mobnum=mob.getText().toString();
        if(nm.equals(ck)||em.equals(ck)|| passwd.equals(ck)|| mobnum.equals(ck))
        {
            Toast.makeText(getApplication(),"fill all the details",Toast.LENGTH_LONG).show();
        }
        else {
            BackgroundTask bt = new BackgroundTask();
            bt.execute(nm, mobnum, em, passwd);


        }

    }

    public void mail() {

        String ems;
        ems=email.getText().toString();
        rec=ems;
        sub="Treasure-hunt";
        msg="welcome to treasure hunt";

        Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port","465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");

        session=Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("treasure.hunt.me@gmail.com", "treasure@1234");
            }
        });

        pgd=ProgressDialog.show(context, "", "Sending mail...", true);
        rep rp = new rep();
        rp.execute();




    }
    class rep extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... params) {


            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("arumugam.dhamu@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(sub);
                message.setContent(msg, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            pgd.dismiss();
            Toast.makeText(getApplication(),"success mail",Toast.LENGTH_LONG).show();
        }
    }

    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String signup_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            signup_url="http://thunt.96.lt/kaarthiha/thuntinsert.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           if(temp==0) {
               Toast.makeText(getApplication(), "inserted", Toast.LENGTH_SHORT).show();
           }

        }



        @Override
        protected String doInBackground(String... params) {
            String name,mob_no,email,pass,imagestr;
            name=params[0];
            mob_no=params[1];
            email=params[2];
            pass=params[3];



            // Toast.makeText(getApplication(),""+email_id+"\t"+password,Toast.LENGTH_LONG).show();
            try {


                URL url=new URL(signup_url);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("mob_no","UTF-8")+"="+URLEncoder.encode(mob_no,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "data is inserted";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }
    }
    }
