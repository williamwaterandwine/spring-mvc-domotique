package controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author usrlocal
 */
import dao.Dao;
import entities.Arduino;
import entities.Commande;
import entities.Reponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import metier.Metier;
import model.ArduinoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import validator.BlinkLedValidator;
import validator.HomeValidator;
import validator.ReadPinValidator;
import validator.ReadTemperatureValidator;
import validator.SendJsonValidator;
import validator.WritePinValidator;

@Controller
public class ArduinoController {

  @ModelAttribute("binaryPins")
  public List<Integer> populateBinaryPin() {
    return Arrays.asList(new Integer[]{
      0, 1, 2, 3, 4, 5, 6
    });
  }

  @ModelAttribute("allModes")
  public List<String> populateModes() {
    return Arrays.asList(new String[]{
      "Binaire", "Analogique"
    });
  }

  @RequestMapping(value = "/writePin", method = RequestMethod.GET)
  public String commande(@ModelAttribute("arduinoModel") ArduinoModel arduinoModel, final Model model, HttpServletRequest request) {
    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    return "writePin";
  }

  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public String home(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, final Model model, HttpServletRequest request) {
    Metier metier = new Metier();

    arduinoModel.setUrlREST(metier.getUrlRestService());

    return "home";
  }
  
  @RequestMapping(value = "/about", method = RequestMethod.GET)
  public String about(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, final Model model, HttpServletRequest request) {

    return "about";
  }

  @RequestMapping(value = "/homeCheck", method = RequestMethod.POST)
  public String homeCheck(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, BindingResult result,
          ModelMap model, HttpServletRequest request, RedirectAttributes attributes) {

    HomeValidator validator = new HomeValidator();
    validator.validate(arduinoModel, result);

    if (result.hasErrors()) {
      //attributes.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
      for (FieldError error : result.getFieldErrors()) {
        if (error.getField().equals("urlREST")) {
          model.addAttribute("urlRESTError", error.getDefaultMessage());
          System.out.println("Prob");
        }
      }
      return "home";
    }

    Metier metier = new Metier();

    request.getSession().setAttribute("urlService", arduinoModel.getUrlREST());
    try {
      metier.setUrlRestService((String) request.getSession().getAttribute("urlService"));
      List<Arduino> arduinoList = metier.getArduinos();
      model.addAttribute("arduinoList", arduinoList);
      request.getSession().setAttribute("arduinoList", arduinoList);
      model.addAttribute("reponse", "Connection réussie");
    } catch (Exception ex) {
      model.addAttribute("reponse", ex.getMessage());
    }

    return "home";
  }

  @RequestMapping(value = "/readPin", method = RequestMethod.GET)
  public String readPin(@ModelAttribute("arduinoModel") ArduinoModel arduinoModel, final Model model, HttpServletRequest request) {
    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    return "readPin";
  }
  
  @RequestMapping(value = "/readTemperature", method = RequestMethod.GET)
  public String readTemperature(@ModelAttribute("arduinoModel") ArduinoModel arduinoModel, final Model model, HttpServletRequest request) {
    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    return "readTemperature";
  }

  @RequestMapping(value = "/sendJson", method = RequestMethod.GET)
  public String sendJson(@ModelAttribute("arduinoModel") ArduinoModel arduinoModel, final Model model, HttpServletRequest request) {
    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    return "sendJson";
  }

  @RequestMapping(value = "/blinkLed", method = RequestMethod.GET)
  public String blinkLed(@ModelAttribute("arduinoModel") ArduinoModel arduinoModel, final Model model, HttpServletRequest request) {
    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    return "blinkLed";
  }

  @RequestMapping(value = "/writePinValue", method = RequestMethod.POST)
  public String writePin(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, BindingResult result,
          ModelMap model, HttpServletRequest request, RedirectAttributes attributes) {

    System.out.println("arduinoModel : " + arduinoModel.getArduinoSelected());
    WritePinValidator validator = new WritePinValidator();
    validator.validate(arduinoModel, result);

    if (result.hasErrors()) {
      //attributes.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
      for (FieldError error : result.getFieldErrors()) {
        if (error.getField().equals("arduino")) {
          model.addAttribute("arduinoError", error.getDefaultMessage());

        }
        if (error.getField().equals("pin")) {
          model.addAttribute("pinError", error.getDefaultMessage());

        }
        if (error.getField().equals("value")) {
          model.addAttribute("valueError", error.getDefaultMessage());

        }
      }
      model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
      return "writePin";
    }

    if (arduinoModel.getModeChoose().equals("Binaire")) {
      arduinoModel.setMode("b");
    } else if (arduinoModel.getModeChoose().equals("Analogique")) {
      arduinoModel.setMode("a");
    }

    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    model.addAttribute("mode", arduinoModel.getMode());
    model.addAttribute("pin", arduinoModel.getPin());
    model.addAttribute("value", arduinoModel.getValue());

    Metier metier = new Metier();
    metier.setUrlRestService((String) request.getSession().getAttribute("urlService"));
    System.out.println("Url Service " + metier.getUrlRestService());
    Reponse reponse;
    StringBuilder chaine = new StringBuilder("");

    String str = arduinoModel.getArduinoSelected();
    String delims = "[ .,?!]+";
    String[] tokens = str.split(delims);

    for (int i = 0; i < tokens.length; i++) {

      try {
        reponse = metier.pinWrite("" + i, tokens[i], arduinoModel.getPin(), arduinoModel.getMode(), arduinoModel.getValue());
        chaine.append(reponse.toString());

      } catch (Exception ex) {
        if (ex.getMessage().equals("404 Introuvable")) {
          chaine.append("\nLe serveur REST specifié est introuvable.");

        } else {
          chaine.append(ex.getMessage());;
        }
      }
      // return "writePin";
    }
    model.addAttribute("reponse", chaine.toString());
    return "writePin";
  }

  @RequestMapping(value = "/readPinValue", method = RequestMethod.POST)
  public String readPin(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, BindingResult result,
          ModelMap model, HttpServletRequest request, RedirectAttributes attributes) {

    ReadPinValidator validator = new ReadPinValidator();
    validator.validate(arduinoModel, result);

    if (result.hasErrors()) {
      //attributes.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
      for (FieldError error : result.getFieldErrors()) {
        if (error.getField().equals("arduino")) {
          model.addAttribute("arduinoError", error.getDefaultMessage());

        }
        if (error.getField().equals("pin")) {
          model.addAttribute("pinError", error.getDefaultMessage());

        }
      }
      model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
      return "readPin";
    }

    if (arduinoModel.getModeChoose().equals("Binaire")) {
      arduinoModel.setMode("b");
    } else if (arduinoModel.getModeChoose().equals("Analogique")) {
      arduinoModel.setMode("a");
    }

    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    model.addAttribute("mode", arduinoModel.getMode());
    model.addAttribute("pin", arduinoModel.getPin());

    Metier metier = new Metier();
    metier.setUrlRestService((String) request.getSession().getAttribute("urlService"));
    Reponse reponse;
    StringBuilder chaine = new StringBuilder("");

    String str = arduinoModel.getArduinoSelected();
    String delims = "[ .,?!]+";
    String[] tokens = str.split(delims);

    for (int i = 0; i < tokens.length; i++) {

      try {
        reponse = metier.pinRead("" + i, tokens[i], arduinoModel.getPin(), arduinoModel.getMode());
        chaine.append(reponse.toString());

      } catch (Exception ex) {
        if (ex.getMessage().equals("404 Introuvable")) {
          chaine.append("\nLe serveur REST specifié est introuvable.");

        } else {
          chaine.append(ex.getMessage());;
        }
      }
      // return "writePin";
    }
    model.addAttribute("reponse", chaine.toString());

    return "readPin";
  }
  
  @RequestMapping(value = "/readTemperatureValue", method = RequestMethod.POST)
  public String readTemperature(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, BindingResult result,
          ModelMap model, HttpServletRequest request, RedirectAttributes attributes) {

    ReadTemperatureValidator validator = new ReadTemperatureValidator();
    validator.validate(arduinoModel, result);

    if (result.hasErrors()) {
      //attributes.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
      for (FieldError error : result.getFieldErrors()) {
        if (error.getField().equals("arduino")) {
          model.addAttribute("arduinoError", error.getDefaultMessage());

        }
        if (error.getField().equals("pin")) {
          model.addAttribute("pinError", error.getDefaultMessage());

        }
      }
      model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
      return "readTemperature";
    }
    arduinoModel.setMode("a");

    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    model.addAttribute("mode", arduinoModel.getMode());
    model.addAttribute("pin", arduinoModel.getPin());

    Metier metier = new Metier();
    metier.setUrlRestService((String) request.getSession().getAttribute("urlService"));
    Reponse reponse;
    StringBuilder chaine = new StringBuilder("");

    String str = arduinoModel.getArduinoSelected();
    String delims = "[ .,?!]+";
    String[] tokens = str.split(delims);

    for (int i = 0; i < tokens.length; i++) {

      try {
        reponse = metier.pinRead("" + i, tokens[i], arduinoModel.getPin(), arduinoModel.getMode());
        Object anaValueString = reponse.getEtat().get("pin"+arduinoModel.getPin().toString());
        System.out.println("Value : " + anaValueString);
        int anaValue = Integer.parseInt((String)anaValueString);
        //float voltage = anaToVolt((Integer)anaValue, 5);
        //float temp = voltToTemp(voltage);
        float temp = anaToTempExp(anaValue, 5);
        chaine.append("Temperature : " + temp + "°C "+ "\n");

      } catch (Exception ex) {
        if (ex.getMessage().equals("404 Introuvable")) {
          chaine.append("\nLe serveur REST specifié est introuvable." + "\n");

        } else {
          chaine.append(ex.getMessage());;
        }
      }
      // return "writePin";
    }
    model.addAttribute("reponse", chaine.toString());

    return "readTemperature";
  }
  
  private float anaToVolt(int anaValue, float voltageMax)
  {
    return (voltageMax*anaValue)/1024;
  }
  
  private float voltToTemp(float voltage)
  {
    return 100*voltage -50;
  }
  
  private float anaToTempExp(int anaValue, float voltageMax){
    return (float)voltageMax * anaValue * 26 / 1024;
  }

  @RequestMapping(value = "/blinkLedValue", method = RequestMethod.POST)
  public String blinkLed(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, BindingResult result,
          ModelMap model, HttpServletRequest request, RedirectAttributes attributes) {

    BlinkLedValidator validator = new BlinkLedValidator();
    validator.validate(arduinoModel, result);

    if (result.hasErrors()) {
      //attributes.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
      for (FieldError error : result.getFieldErrors()) {
        if (error.getField().equals("arduino")) {
          model.addAttribute("arduinoError", error.getDefaultMessage());

        }

        if (error.getField().equals("pin")) {
          model.addAttribute("pinError", error.getDefaultMessage());

        }
        if (error.getField().equals("blinkTime")) {
          model.addAttribute("blinkTimeError", error.getDefaultMessage());

        }

        if (error.getField().equals("blinkNumber")) {
          model.addAttribute("blinkNumberError", error.getDefaultMessage());

        }
      }
      model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
      return "blinkLed";
    }


    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
    model.addAttribute("pin", arduinoModel.getPin());
    model.addAttribute("blinkTime", arduinoModel.getBlinkTime());
    model.addAttribute("blinkNumber", arduinoModel.getBlinkNumber());

    Metier metier = new Metier();
    metier.setUrlRestService((String) request.getSession().getAttribute("urlService"));
    Reponse reponse;

    StringBuilder chaine = new StringBuilder("");

    String str = arduinoModel.getArduinoSelected();
    String delims = "[ .,?!]+";
    String[] tokens = str.split(delims);

    for (int i = 0; i < tokens.length; i++) {

      try {
        reponse = metier.faireClignoterLed("" + i, tokens[i], arduinoModel.getPin(), arduinoModel.getBlinkTime(), arduinoModel.getBlinkNumber());
        chaine.append(reponse.toString());

      } catch (Exception ex) {
        
        if (ex.getMessage().equals("404 Introuvable")) {
          chaine.append("\nLe serveur REST specifié est introuvable.");

        } else {
          chaine.append(ex.getMessage());;
        }
      }
      // return "writePin";
    }
    model.addAttribute("reponse", chaine.toString());

    return "blinkLed";
  }

  @RequestMapping(value = "/sendJsonValue", method = RequestMethod.POST)
  public String sendJson(@ModelAttribute("arduinoModel") @Valid ArduinoModel arduinoModel, BindingResult result,
          ModelMap model, HttpServletRequest request, RedirectAttributes attributes) {

    SendJsonValidator validator = new SendJsonValidator();
    validator.validate(arduinoModel, result);

    if (result.hasErrors()) {
      //attributes.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
      for (FieldError error : result.getFieldErrors()) {
        if (error.getField().equals("jsonCommand")) {
          model.addAttribute("jsonCommandError", error.getDefaultMessage());

        }

      }
      model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));
      return "sendJson";
    }

    model.addAttribute("arduinoList", request.getSession().getAttribute("arduinoList"));

    model.addAttribute("jsonCommand", arduinoModel.getJsonCommand());

    Metier metier = new Metier();
    metier.setUrlRestService((String) request.getSession().getAttribute("urlService"));

    StringBuilder chaine = new StringBuilder("");

    String str = arduinoModel.getArduinoSelected();
    System.out.println("arduinoSelected" + str);
    String delims = "[ .,?!]+";
    String[] tokens = str.split(delims);

    for (int i = 0; i < tokens.length; i++) {
      try {
        String reponse = metier.sendCommandesJson(tokens[i], arduinoModel.getJsonCommand());
        chaine.append(reponse.toString());
        
        System.out.println("Ok" + reponse.toString());
      } catch (Exception ex) {
        if (ex.getMessage().equals("404 Introuvable")) {
          chaine.append("Le serveur REST specifié est introuvable.");
        } else {
          chaine.append(ex.getMessage());
        }
      }
    }
    model.addAttribute("reponse", chaine);
    return "sendJson";
  }

@RequestMapping(value = "/getArduinos", method = RequestMethod.POST)
        public String getArduinos(@ModelAttribute("arduinoModel") ArduinoModel arduinoModel,
          ModelMap model, HttpServletRequest request) {

    Metier metier = new Metier();
    List<Arduino> arduinoList = metier.getArduinos();
    if (arduinoList.size() > 0) {
      model.addAttribute("arduinoList", arduinoList);
      request.getSession().setAttribute("arduinoList", arduinoList);
    }
    return "";
  }
}
