package com.example.student.mydiary;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Handler;

/**
 * Created by student on 2018-01-26.
 */

public class HoliDayThread extends Thread {
    private Handler handler;
    private String year;
    private String month;

    public HoliDayThread(Handler handler, String year, String month) {
        this.handler = handler;
        this.month = month;
        this.year = year;
    }

    @Override
    public void run() {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getSundryDayInfo"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=jup5gbT4sRfnBFu%2FMab71AcZn1P67pErryIXShmSNknyi6XsQRrdJ2S3zdIbNGtb4oVRoWAsBO%2BMuAgUonY6sw%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode("2015", "UTF-8")); /*연*/
            urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*월*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /**/
            urlBuilder.append("&" + URLEncoder.encode("totalCount", "UTF-8") + "=" + URLEncoder.encode("16", "UTF-8")); /**/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            //   System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            Log.d("api", sb.toString());
        } catch (Exception e) {

        }
    }


}
