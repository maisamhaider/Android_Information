package com.dragster.android.information.system.my.android.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class SpeedTestHandler extends Thread {

    HashMap<Integer, String> mKey = new HashMap<>();
    HashMap<Integer, List<String>> mValue = new HashMap<>();
    boolean isDone = false;
    double sLon = 0.0;
    double sLat = 0.0;


    public HashMap<Integer, String> getmKey() {
        return mKey;
    }

    public HashMap<Integer, List<String>> getmValue() {
        return mValue;
    }

    public double getsLat() {
        return sLat;
    }

    public double getsLon() {
        return sLon;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public void run() {
        //Get latitude, longitude
        try {
            URL url = new URL("https://www.speedtest.net/speedtest-config.php");
            HttpURLConnection urlConc = (HttpURLConnection) url.openConnection();
            int code = urlConc.getResponseCode();

            if (code == 200) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                urlConc.getInputStream()));

                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.contains("isp=")) {
                        continue;
                    }
                    sLat = Double.parseDouble(line.split("lat=\"")[1].split(" ")[0].replace("\"", ""));
                    sLon = Double.parseDouble(line.split("lon=\"")[1].split(" ")[0].replace("\"", ""));
                    break;
                }

                br.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        String name = "";
        String cc = "";
        String lon = "";
        String lat = "";
        String country = "";
        String sponsor = "";
        String host = "";
        String uploadAddress = "";


        //Best server
        int count = 0;
        try {
            URL url = new URL("https://www.speedtest.net/speedtest-servers-static.php");
            HttpURLConnection urlConc = (HttpURLConnection) url.openConnection();
            int code = urlConc.getResponseCode();

            if (code == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                urlConc.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("<server url")) {
                        uploadAddress = line.split("server url=\"")[1].split("\"")[0];
                        lat = line.split("lat=\"")[1].split("\"")[0];
                        lon = line.split("lon=\"")[1].split("\"")[0];
                        name = line.split("name=\"")[1].split("\"")[0];
                        country = line.split("country=\"")[1].split("\"")[0];
                        cc = line.split("cc=\"")[1].split("\"")[0];
                        sponsor = line.split("sponsor=\"")[1].split("\"")[0];
                        host = line.split("host=\"")[1].split("\"")[0];

                        List<String> ls = Arrays.asList(lat, lon, name, country, cc, sponsor, host);
                        mKey.put(count, uploadAddress);
                        mValue.put(count, ls);
                        count++;
                    }
                }

                bufferedReader.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        isDone = true;
    }
}
