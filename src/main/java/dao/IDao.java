/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Arduino;
import entities.Reponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usrlocal
 */
public interface IDao {
    
    public String executeRestService(String method, String urlService, Object request, Map<String, String> paramètres);
    public void setTimeout(int millis);
    public void checkResponsiveness(String urlService);
}
