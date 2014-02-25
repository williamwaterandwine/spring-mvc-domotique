/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import entities.*;
import java.util.List;

/**
 *
 * @author usrlocal
 */
public interface IMetier {

    public List<Arduino> getArduinos();
// lecture d'une pin
    public Reponse pinRead(String idCommande, String idArduino, int pin, String mode);
// écriture d'une pin
    public Reponse pinWrite(String idCommande, String idArduino, int pin, String mode, int val);
// faire clignoter une led
    public Reponse faireClignoterLed(String idCommande, String idArduino, int pin, int millis, int nbIter);
// envoyer une suite de commandes Json à un Arduino
    public String sendCommandesJson(String idArduino, String commandes);
}
