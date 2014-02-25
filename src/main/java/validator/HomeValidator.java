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
public class HomeValidator implements Validator {

    @Override
    public boolean supports(Class aClass) {
        return ArduinoModel.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArduinoModel arduino = (ArduinoModel) target;
        
        /*if(arduino.getArduinoList() == null){
             errors.rejectValue("arduino", "10", "Sélectionnez une carte arduino.");
        }*/
        
        if (arduino.getUrlREST() == null) {
            errors.rejectValue("urlREST", "100", "Vous devez entrer une chaine de caractères.");
            System.out.println("Hola");
           
        }
    }
}
