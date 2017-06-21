package com.example.kaarthiha.finaltreasurehunt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EdgeEffect;
import android.widget.EditText;
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

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class introactivity extends AppCompatActivity {

    TextView rules,frgt;
    EditText mob,pwd;
    Button b1,b2;
    String mobile,password,JSON_STRING,usrname,usrpwd,mobi,imagedstr;
    boolean net;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "LHQB5fgrXFNngscFj5MmoJ8VL";
    private static final String TWITTER_SECRET = "DiPeKiEk6VyhcLywhLDXHVgnlSNbrZNUIw9tIZLk0HytJYOIY3";


    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introactivity);
        rules=(TextView)findViewById(R.id.textView);
        b2=(Button)findViewById(R.id.button2);
        b1=(Button)findViewById(R.id.button);
        mob=(EditText)findViewById(R.id.editText);


        pwd=(EditText)findViewById(R.id.editText2);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
            digitsButton.setText("Forgot your password?");




        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber.substring(3), Toast.LENGTH_LONG).show();

                Intent i = new Intent(introactivity.this,otp.class);
                i.putExtra("num", phoneNumber.substring(3));
                startActivity(i);
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });

        net = isOnline();

        if(net==false)
        {


            Snackbar.make(findViewById(android.R.id.content), "Network Unavailable", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();


        }




            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    net = isOnline();
                    if(net!=false) {
                        loginpage();
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), "Network Unavailable", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    net = isOnline();
                    if(net!=false) {
                        signuppage();
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), "Network Unavailable", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                }
            });
            rules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    net = isOnline();
                    if(net!=false) {
                        Intent i = new Intent(introactivity.this, rules.class);
                        startActivity(i);
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), "Network Unavailable", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                }
            });

        }






    public void loginpage() {

     mobile=mob.getText().toString();
        password=pwd.getText().toString();

        if(mobile.length()!=10)
        {
            Toast.makeText(getApplication(),"Enter valid mobile number",Toast.LENGTH_LONG).show();
        }
        else {
            if (mobile.equals("") || password.equals("")) {
                Toast.makeText(getApplication(), "fill all details", Toast.LENGTH_LONG).show();
            } else {
                numbgtsk nm = new numbgtsk();
                nm.execute(mobile);

            }
        }


    }

    class numbgtsk extends AsyncTask<String,Void,String>
    {
        String cmurl; //retriveing the pwd;

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
            cmurl="http://thunt.96.lt/kaarthiha/sumret.php";
    }

        @Override
        protected void onPostExecute(String s) {
            String str=s,ck="{\"server_response\":[]}";
            int c=0;


                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                        int n = jsonArray.length();
                        if (n == 0) {
                                Toast.makeText(getApplication(),"you are not registered",Toast.LENGTH_LONG).show();
                        } else {
                            while (c < jsonArray.length()) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(c);
                                usrname = jsonObject1.getString("name");
                                usrpwd = jsonObject1.getString("password");


                                c++;
                            }
                            if (usrpwd.equals(password)) {
                                Toast.makeText(getApplication(), "password matched successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(introactivity.this, homescreen.class);
                                i.putExtra("name", usrname);
                                i.putExtra("mob", mobile);

                                startActivity(i);

                            } else {
                                Toast.makeText(getApplication(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                            }
                        }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected String doInBackground(String... params) {
            String mob_no=params[0];
            URL url= null;
            try {
                url = new URL(cmurl);

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

    public void signuppage()
    {
        Intent i = new Intent(introactivity.this,signup.class);
        startActivity(i);
    }
}
