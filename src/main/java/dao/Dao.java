/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.ArduinoException;

public class Dao implements IDao {

    // client REST
    private RestTemplate restTemplate;
    // mapper JSON
    private Gson gson = new Gson();
    // délai d'attente maximal
    private int timeout;

    // constructeur
    public Dao() {
        // on crée un objet [RestTemplate]
        restTemplate = new RestTemplate();
        // on le configure
        //restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }

    // exécution de l'appel au service REST
    public String executeRestService(String method, String urlService, Object request, Map<String, String> paramètres) {

        // on vérifie que le serveur distant répond assez vite
        // une exception est lancée sinon
        checkResponsiveness(urlService);
        // on récupère l'id de l'arduino
        String idArduino = (String) paramètres.get("idArduino");
        // vérification méthode HTTP
        method = method.toLowerCase();
        if (!method.equals("get") && !method.equals("post")) {
            //throw new DomotiqueException(String.format("L'argument [method] doit avoir la valeur post ou get dans [%s]",
            //   idArduino), 165);
        }
        // suivi
        System.out.println(String.format("dao début executeRestService %s : %s", new SimpleDateFormat("hh:mm:ss:SS",
                Locale.FRANCE).format(new Date()), urlService));
        //try {
        // exécution service
        String réponse = null;
        if (method.equals("get")) {
            réponse = restTemplate.getForObject(urlService, String.class, paramètres);
            System.out.println(réponse);
        } else {
            réponse = restTemplate.postForObject(urlService, request, String.class, paramètres);
        }
        // suivi
        System.out.println(String.format("dao fin executeRestService %s : %s %s", new SimpleDateFormat("hh:mm:ss:SS",
                Locale.FRANCE).format(new Date()), urlService, réponse));

        // résultat
        Map<String, Object> map = gson.fromJson(réponse, new TypeToken<Map<String, Object>>() {
        }.getType());
        // erreur ?
        String erreur = (String) map.get("error");
        if (erreur != null) {
            throw new ArduinoException(String.format("Le serveur a renvoyé l'erreur suivante pour [%s] : %s", idArduino,
                    (String) map.get("message")), 168);
        }
        // data ?
        Object data = map.get("data");
        if (data == null) {
             throw new ArduinoException(String.format("Réponse incorrecte du serveur pour [%s] : %s", idArduino, réponse),
                167);
        }
        // c'est bon - on rend le résultat
        return gson.toJson(data);
        /*} catch (Throwable ex) {
         throw new Exception(ex);
         }*/
    }

    public void checkResponsiveness(String urlService) {
        // on crée l'URI du service distant
        String url = urlService.replace("{", "").replace("}", "");
        URI service = null;
        try {
            service = new URI(url);
        } catch (URISyntaxException ex) {
            throw new ArduinoException(String.format("Format d'URL incorrect [%s]", urlService), ex, 170);
        }
        // on se connecte au service
        Socket client = null;
        try {
            // on se connecte au service avec attente maximale définie par
            // configuration
            client = new Socket();
            /*System.out.println("responsiveness");
             System.out.println(urlService);
             System.out.println(url);
             System.out.println(service.getHost());
             System.out.println(service.getPort());*/
            client.connect(new InetSocketAddress(service.getHost(), service.getPort()), timeout);
        } catch (IOException e) {
            throw new ArduinoException("Le service distant n'a pas répondu assez vite", e, 171);
        } finally {
            // on libère les ressources
            if (client != null) {
                try {
                    client.close();
                } catch (IOException ex) {
                    Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    // délai d'attente maximal
    public void setTimeout(int millis) {
        this.timeout = millis;
    }
}