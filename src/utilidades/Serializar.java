/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import unicauca.negocio.*;

/**
 *
 * @author JuanCamilo
 */
public class Serializar {
    
    public Serializar() {
        super();
    }
    public JsonObject parseToJSONUsuario(Usuario objUsuario) 
    {
        JsonObject jsonobj = new JsonObject();
        
        jsonobj.addProperty("cedula", objUsuario.getCedula());
        jsonobj.addProperty("nombres", objUsuario.getNombres());
        jsonobj.addProperty("apellidos", objUsuario.getApellidos());
        jsonobj.addProperty("fechaNaci", objUsuario.getFechaNaci());
        jsonobj.addProperty("user", objUsuario.getUser());
        jsonobj.addProperty("password", objUsuario.getPassword());
       
        return jsonobj;
    }
    public JsonObject parseToJSONConductor(Conductor objConductor){
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("cedula", objConductor.getCedula());
        jsonobj.addProperty("nombres", objConductor.getNombres());
        jsonobj.addProperty("apellidos", objConductor.getApellidos());
        jsonobj.addProperty("genero", objConductor.getGenero());
        jsonobj.addProperty("fechaNaci", objConductor.getFechaNaci());
        return jsonobj;
    }
    public String serializarVehiculos(ArrayList<Vehiculo> objVehiculos){
        JsonArray array = new JsonArray();
        JsonObject objJson;
        for(Vehiculo vehiculo:objVehiculos){
            
            objJson = parseToVehiculo(vehiculo);
            array.add(objJson);
        }
        return array.toString();
    }
    
    public String serializarRoles(ArrayList<String> objRoles){
        JsonArray array = new JsonArray();
        JsonObject objJson;
        
        for (String rol:objRoles) {
            objJson = parseToRol(rol);
            array.add(objJson);
        }
        return array.toString();
    }
    
    public JsonObject parseToRol(String rol){
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("rol", rol);
        
        return jsonobj;
    }
    
    public JsonObject parseToVehiculo(Vehiculo objVehiculo){
        JsonObject jsonobj = new JsonObject();
        
        jsonobj.addProperty("placa", objVehiculo.getPlaca());
        jsonobj.addProperty("marca", objVehiculo.getMarca());
        jsonobj.addProperty("tipo", objVehiculo.getTipo());
        
        return jsonobj;
    }
}
