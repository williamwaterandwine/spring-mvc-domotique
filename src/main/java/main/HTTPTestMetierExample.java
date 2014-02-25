/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.Dao;
import entities.Arduino;
import entities.Reponse;
import java.util.ArrayList;
import java.util.List;
import metier.Metier;

/**
 *
 * @author usrlocal
 */
public class HTTPTestMetierExample {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        System.out.println("Testing 1 - Send Http GET request");
        Metier metier = new Metier();
        List<Arduino> arduinoList = new ArrayList<Arduino>();
        arduinoList = metier.getArduinos();
        if (arduinoList.size() > 0) {
            //int reponse = metier.setPinValue(arduinoList.get(0), 9, true, 1);
            //int reponse1 = metier.getPinValue(arduinoList.get(0), 9, true);
          //  metier.setPinBlinkLed(arduinoList.get(0), 9, 100, 100);
        }

        System.out.println("\nTesting 2 - Send Http POST request");
    }
}
