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
public class BlinkLedValidator implements Validator {

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
        } else {
            if (arduino.getPin() < 0 || arduino.getPin() > 10) {
                errors.rejectValue("pin", "101", "La valeur du pin est comprise entre 0 et 10.");
            }
        }

        if (arduino.getBlinkTime() == null) {
            errors.rejectValue("blinkTime", "200", "Vous devez renseigner un temps (entier).");
        } else {
            if (arduino.getBlinkTime() < 0) {
                errors.rejectValue("blinkTime", "201", "Le temps de clignotement doit être supérieur à 0");
            }
        }

        if (arduino.getBlinkNumber() == null) {
            errors.rejectValue("blinkNumber", "300", "Vous devez renseigner un nombre de clignotement (entier).");
        } else {
            if (arduino.getBlinkNumber() < 0) {
                errors.rejectValue("blinkNumber", "201", "le nombre de clignotements doit être supérieur à 0");
            }
        }
    }
}
