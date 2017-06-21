package com.example.kaarthiha.finaltreasurehunt;

/**
 * Created by Dhamo on 22-03-2017.
 */
public class Latilongiverify {

    public double ansloca(double lat,double lon,double lat2,double lon2)
    {
        double d1=(double)(180.d/Math.PI);
        double a1=lat/d1;
        double a2=lon/d1;

        double b1=lat2/d1;
        double b2 = lon2/d1;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);

        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);

        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1+t2+t3);
        return 6366000*tt;

    }


}
