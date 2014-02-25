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
public class ReadTemperatureValidator implements Validator {

  @Override
  public boolean supports(Class aClass) {
    return ArduinoModel.class.equals(aClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ArduinoModel arduino = (ArduinoModel) target;

    if (arduino.getArduinoSelected() == null) {
      errors.rejectValue("arduino", "10", "Sélectionnez une carte arduino.");
    }

    if (arduino.getPin() == null) {
      errors.rejectValue("pin", "100", "Vous devez renseigner un numéro de pin (entier).");

    } else {
      if (arduino.getPin() < 0 || arduino.getPin() > 6) {
        errors.rejectValue("pin", "101", "En mode analogique, la valeur du pin est comprise entre 0 et 6.");
      }
    }

  }
}
