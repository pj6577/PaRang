package com.waveway.parang.httpconnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {
    public static JSONArray getJsonArray(String baseDate, String lat, String lot, String baseTime) {

        JSONObject jObject = null;
        try {
            System.out.println("getJsonArray working!!");
            URL url = new URL("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?base_time="+baseTime+"&nx="+lat+"&serviceKey=()&numOfRows=900&pageNo=1&base_date="+ baseDate +"&ny="+lot+"&dataType=JSON");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setRequestProperty("auth", "myAuth"); // header의 auth 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }
            jObject = new JSONObject(sb.toString());
            JSONObject responseJson = jObject.getJSONObject("response");
            JSONObject body = responseJson.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray item = items.getJSONArray("item");

//            for(int i=0;i<item.length();i++){
//                JSONObject obj = item.getJSONObject(i);
//                System.out.println(obj);
//            }
            return item;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
