/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import model.ArduinoModel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author usrlocal
 */
public class ReadPinValidator implements Validator {

    @Override
    public boolean supports(Class aClass) {
        return ArduinoModel.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArduinoModel arduino = (ArduinoModel) target;
        
        if(arduino.getArduinoSelected()== null){
             errors.rejectValue("arduino", "10", "Sélectionnez une carte arduino.");
        }
        
        if (arduino.getPin() == null) {
            errors.rejectValue("pin", "100", "Vous devez renseigner un numéro de pin (entier).");
           
        }else{

            if (arduino.getModeChoose().equals("Binaire")) {
                if (arduino.getPin() < 0 || arduino.getPin() > 10) {
                    errors.rejectValue("pin", "101", "En mode binaire, la valeur du pin est comprise entre 0 et 10.");
                }
            } else if (arduino.getModeChoose().equals("Analogique")) {
                if (arduino.getPin() < 0 || arduino.getPin() > 1024) {
                    errors.rejectValue("pin", "102", "En mode analogique, la valeur du pin est comprise entre 0 et 1024.");
                }
            }
        }
    }
}
