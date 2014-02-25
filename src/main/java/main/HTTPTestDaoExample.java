package main;

import dao.Dao;
import entities.Arduino;
import entities.Reponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HTTPTestDaoExample {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        System.out.println("Testing 1 - Send Http GET request");
        Dao dao = new Dao();
        List<Arduino> arduinoList = new ArrayList<Arduino>();
        //arduinoList = dao.getArduinos();
        if (arduinoList.size() > 0) {
           // Reponse reponse = dao.setPin(arduinoList.get(0).getId(), 9, true, 1);
           // Reponse reponse1 = dao.getPin(arduinoList.get(0).getId(), 9, true);
           // Reponse reponse2 = dao.setBlinkLed(arduinoList.get(0).getId(), 9, 100, 100);
        }

        System.out.println("\nTesting 2 - Send Http POST request");
        //http.sendPost();

    }
}