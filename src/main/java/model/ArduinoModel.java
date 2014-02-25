/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Arduino;
import entities.Commande;
import entities.Reponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author usrlocal
 */
public class ArduinoModel {

    private Arduino arduino;
    private List<Arduino> arduinoList;
    private Commande commandeWrite;
    private Commande commandeRead;
    private Reponse reponse;
    private String mode;
    private String arduinoSelected;
    
    @NotNull
    private Integer value;
    
    @NotNull
    private Integer pin;
    
    @NotNull
    private Integer blinkTime;
    
    @NotNull
    private Integer blinkNumber;
    
    @NotNull
    private String jsonCommand;
    
    private String urlREST;
    
    private String rep;
    private String modeChoose = "Binaire";

    public ArduinoModel() {
        arduinoList = new ArrayList<Arduino>();
    }

    /**
     * @return the commande
     */
    public Commande getCommandeWrite() {
        return commandeWrite;
    }

    /**
     * @param commande the commande to set
     */
    public void setCommandeWrite(Commande commandeWrite) {
        this.commandeWrite = commandeWrite;
    }

    public Commande getCommandeRead() {
        return commandeRead;
    }

    /**
     * @param commande the commande to set
     */
    public void setCommandeRead(Commande commandeRead) {
        this.commandeRead = commandeRead;
    }

    /**
     * @return the arduino
     */
    public Arduino getArduino() {
        return arduino;
    }

    /**
     * @param arduino the arduino to set
     */
    public void setArduino(Arduino arduino) {
        this.arduino = arduino;
    }

    /**
     * @return the reponse
     */
    public Reponse getReponse() {
        return reponse;
    }

    /**
     * @param reponse the reponse to set
     */
    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    /**
     * @return the arduinoList
     */
    public List<Arduino> getArduinoList() {
        return arduinoList;
    }

    /**
     * @param arduinoList the arduinoList to set
     */
    public void setArduinoList(List<Arduino> arduinoList) {
        this.arduinoList = arduinoList;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * @return the pin
     */
    public Integer getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(Integer pin) {
        this.pin = pin;
    }

    /**
     * @return the rep
     */
    public String getRep() {
        return rep;
    }

    /**
     * @param rep the rep to set
     */
    public void setRep(String rep) {
        this.rep = rep;
    }

    /**
     * @return the modeChoose
     */
    public String getModeChoose() {
        return modeChoose;
    }

    /**
     * @param modeChoose the modeChoose to set
     */
    public void setModeChoose(String modeChoose) {
        this.modeChoose = modeChoose;
    }

    /**
     * @return the arduinoSelected
     */
    public String getArduinoSelected() {
        return arduinoSelected;
    }

    /**
     * @param arduinoSelected the arduinoSelected to set
     */
    public void setArduinoSelected(String arduinoSelected) {
        this.arduinoSelected = arduinoSelected;
    }

    /**
     * @return the blinkTime
     */
    public Integer getBlinkTime() {
        return blinkTime;
    }

    /**
     * @param blinkTime the blinkTime to set
     */
    public void setBlinkTime(Integer blinkTime) {
        this.blinkTime = blinkTime;
    }

    /**
     * @return the blinkNumber
     */
    public Integer getBlinkNumber() {
        return blinkNumber;
    }

    /**
     * @param blinkNumber the blinkNumber to set
     */
    public void setBlinkNumber(Integer blinkNumber) {
        this.blinkNumber = blinkNumber;
    }

    /**
     * @return the jsonCommand
     */
    public String getJsonCommand() {
        return jsonCommand;
    }

    /**
     * @param jsonCommand the jsonCommand to set
     */
    public void setJsonCommand(String jsonCommand) {
        this.jsonCommand = jsonCommand;
    }

    /**
     * @return the urlREST
     */
    public String getUrlREST() {
        return urlREST;
    }

    /**
     * @param urlREST the urlREST to set
     */
    public void setUrlREST(String urlREST) {
        this.urlREST = urlREST;
    }
}
