/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usrlocal
 */
public class Reponse implements Serializable {
// données
    private String id;
    private String erreur;
    private String json;
    private Map<String, Object> etat;
// constructeurs
    public Reponse() {
    }

    public Reponse(String id, String erreur, Map<String, Object> etat, String json) {
        this.etat = etat;
        this.id = id;
        this.erreur = erreur;
        this.json = json;
    }
    
    @Override
    public String toString(){
        return "{id:"+getId()+",etat:"+getEtat()+",erreur:"+getErreur()+",json:"+getJson()+"}";  
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the erreur
     */
    public String getErreur() {
        return erreur;
    }

    /**
     * @param erreur the erreur to set
     */
    public void setErreur(String erreur) {
        this.erreur = erreur;
    }

    /**
     * @return the etat
     */
    public Map<String, Object> getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(Map<String, Object> etat) {
        this.etat = etat;
    }

    /**
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * @param json the json to set
     */
    public void setJson(String json) {
        this.json = json;
    }
    
    
}
