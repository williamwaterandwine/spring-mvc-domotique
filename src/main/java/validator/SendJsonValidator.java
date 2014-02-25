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
public class SendJsonValidator implements Validator {

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

        if (arduino.getJsonCommand() == null) {
            errors.rejectValue("jsonCommand", "100", "Vous devez renseigner une chaine de caractère");
        }
        }
}
