/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author TUTL
 */
public class HttpUtil {

    public static String doPost(String strUrl, String params) throws Exception {
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null;
        try {

            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(3000);

            dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(params);
            dataOutputStream.flush();
            dataOutputStream.close();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    public static String doGet(String strUrl) throws Exception {

        BufferedReader bufferedReader = null;
        try {

            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Language", "en-US");  
            connection.setUseCaches(false);
            connection.setDoInput(true);            
            connection.setDoOutput(true);
            connection.setConnectTimeout(3000);

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

    }

}
