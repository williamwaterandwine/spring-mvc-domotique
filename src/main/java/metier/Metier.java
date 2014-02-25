/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import com.google.gson.Gson;
import dao.Dao;
import dao.IDao;
import entities.Arduino;
import entities.Commande;
import entities.Reponse;
import exception.ArduinoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author usrlocal
 */
public class Metier implements IMetier {

  //private Gson gson = new Gson();
  private ObjectMapper mapper;
  private IDao dao;
  private String urlRestService;

  public Metier() {
    mapper = new ObjectMapper();
    dao = new Dao();
  }

  public void checkRest(String url) {

    try {
      dao.checkResponsiveness(url);
    } catch (Exception e) {
      throw new ArduinoException(String.format("Réponse incorrecte du serveur: %s", e));
    }


  }

  public List<Arduino> getArduinos() {
    //List<Arduino> arduinoList = new ArrayList<Arduino>();

    String urlService = String.format("%s/", getUrlRestService());

    String reponse = dao.executeRestService("get", urlService, null, new HashMap());

    List<Arduino> arduinos = null;
    try {
      return arduinos = mapper.readValue(reponse, new TypeReference<List<Arduino>>() {
      });
    } catch (Exception ex) {
      throw new ArduinoException(String.format("Réponse incorrecte du serveur: %s", new Object[]{
        reponse
      }), ex, 167);
    }

  }

  public Reponse pinRead(String idCommande, String idArduino, int pin, String mode) {
    String urlService = String.format("%s/pinRead/{idCommande}/{idArduino}/{pin}/{mode}/", getUrlRestService());

    Map parametres = new HashMap();
    parametres.put("idCommande", idCommande);
    parametres.put("idArduino", idArduino);
    parametres.put("pin", (new StringBuilder()).append("").append(pin).toString());
    parametres.put("mode", mode);
    System.out.println("Param" + parametres);
    String reponse = dao.executeRestService("get", urlService, null, parametres);

    try {
      return (Reponse) mapper.readValue(reponse, Reponse.class);
    } catch (Exception ex) {
      throw new ArduinoException(String.format("Réponse incorrecte du serveur: %s", new Object[]{
        reponse
      }), ex, 167);
    }

  }

  public Reponse pinWrite(String idCommande, String idArduino, int pin, String mode, int val) {
    String urlService = String.format("%s/pinWrite/{idCommande}/{idArduino}/{pin}/{mode}/{valeur}", getUrlRestService());
    System.out.println("Send This :" + urlService);

    Map parametres = new HashMap();
    parametres.put("idCommande", idCommande);
    parametres.put("idArduino", idArduino);
    parametres.put("pin", (new StringBuilder()).append("").append(pin).toString());
    parametres.put("mode", mode);
    parametres.put("valeur", (new StringBuilder()).append("").append(val).toString());
    System.out.println("Param" + parametres);
    String reponse = dao.executeRestService("get", urlService, null, parametres);

    try {
      return (Reponse) mapper.readValue(reponse, Reponse.class);
    } catch (Exception ex) {
      throw new ArduinoException(String.format("Réponse incorrecte du serveur : %s", new Object[]{
        reponse
      }), ex, 168);
    }
  }

  public Reponse faireClignoterLed(String idCommande, String idArduino, int pin, int millis, int nbIter) {

    String urlService = String.format("%s/blink/{idCommande}/{idArduino}/{pin}/{duree}/{nombre}/", getUrlRestService());
    Map parametres = new HashMap();
    parametres.put("idCommande", idCommande);
    parametres.put("idArduino", idArduino);
    parametres.put("pin", (new StringBuilder()).append("").append(pin).toString());
    parametres.put("duree", (new StringBuilder()).append("").append(millis).toString());
    parametres.put("nombre", (new StringBuilder()).append("").append(nbIter).toString());
    System.out.println("Param" + parametres);
    String reponse = dao.executeRestService("get", urlService, null, parametres);

    try {
      return (Reponse) mapper.readValue(reponse, Reponse.class);
    } catch (Exception e) {
      throw new ArduinoException("La réponse du serveur a un format JSON incorrect" + e);
    }
  }

  public String sendCommandesJson(String idArduino, String commandes) {
    String urlService = String.format("%s/commands/{idArduino}/", getUrlRestService());
    Map parametres = new HashMap();
    parametres.put("idArduino", idArduino);
    
    System.out.println("Param"+ parametres);
    
    String reponse = dao.executeRestService("post", urlService, commandes, parametres);
    
    try {
      return reponse;
    } catch (Exception e) {
      throw new ArduinoException("La réponse du serveur a un format JSON incorrect");
    }
  }

  /**
   * @return the urlRestService
   */
  public String getUrlRestService() {
    return urlRestService;
  }

  /**
   * @param urlRestService the urlRestService to set
   */
  public void setUrlRestService(String urlRestService) {
    this.urlRestService = urlRestService;
  }
}
