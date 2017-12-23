/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applytranslation;

/**
 *
 * @author Yin
 */

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApplyTranslation {
  // TODO: If you have your own Premium account credentials, put them down here:
  private static final String CLIENT_ID = "yinruohua@gmail.com";
  private static final String CLIENT_SECRET = "74ec23fbdc684028a16d260165170cd8";
  private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

  /**
   * Entry Point
   */
  /**
   * Sends out a WhatsApp message via WhatsMate WA Gateway.
   */
  public static String translate(String fromLang, String toLang, String text) throws Exception {
    // TODO: Should have used a 3rd party library to make a JSON string from an object
    String result="";
    String jsonPayload = new StringBuilder()
      .append("{")
      .append("\"fromLang\":\"")
      .append(fromLang)
      .append("\",")
      .append("\"toLang\":\"")
      .append(toLang)
      .append("\",")
      .append("\"text\":\"")
      .append(text)
      .append("\"")
      .append("}")
      .toString();

    URL url = new URL(ENDPOINT);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(jsonPayload.getBytes());
    os.flush();
    os.close();

    int statusCode = conn.getResponseCode();
    System.out.println("Status Code: " + statusCode);
    BufferedReader br = new BufferedReader(new InputStreamReader(
        (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
      ));
    String output;
    while ((output = br.readLine()) != null) {
        System.out.println(output);
        result = output;
    }
    conn.disconnect();
    
    return result;
  }

}
