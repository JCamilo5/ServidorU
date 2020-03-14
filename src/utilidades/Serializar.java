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

    public JsonObject parseToJSONUsuario(Usuario objUsuario) {
        JsonObject jsonobj = new JsonObject();

        jsonobj.addProperty("cedula", objUsuario.getCedula());
        jsonobj.addProperty("nombres", objUsuario.getNombres());
        jsonobj.addProperty("apellidos", objUsuario.getApellidos());
        jsonobj.addProperty("fechaNaci", objUsuario.getFechaNaci());
        jsonobj.addProperty("user", objUsuario.getUser());
        jsonobj.addProperty("password", objUsuario.getPassword());
        jsonobj.addProperty("privilegio", objUsuario.getPrivilegio());

        return jsonobj;
    }

    public JsonObject parseToJSONConductor(Conductor objConductor) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("cedula", objConductor.getCedula());
        jsonobj.addProperty("nombres", objConductor.getNombres());
        jsonobj.addProperty("apellidos", objConductor.getApellidos());
        jsonobj.addProperty("genero", objConductor.getGenero());
        jsonobj.addProperty("fechaNaci", objConductor.getFechaNaci());
        jsonobj.addProperty("rol", objConductor.getRol());
        return jsonobj;
    }

    public String serializarVehiculos(ArrayList<Vehiculo> objVehiculos) {
        JsonArray array = new JsonArray();
        JsonObject objJson;
        for (Vehiculo vehiculo : objVehiculos) {

            objJson = parseToVehiculo(vehiculo);
            array.add(objJson);
        }
        return array.toString();
    }

    public String serializarBahias(ArrayList<Bahia> objBahias) {
        JsonArray array = new JsonArray();
        JsonObject objJson;
        for (Bahia b : objBahias) {
            objJson = parseToBahia(b);
            array.add(objJson);
        }
        return array.toString();
    }
    public String serializarHorasConges(ArrayList<IntervaloCongestion> objIntervalo){
        JsonArray array = new JsonArray();
        JsonObject objSon;
        for(IntervaloCongestion i : objIntervalo){
            objSon = parseToIntervalo(i);
            array.add(objSon);
        }
        return array.toString();
    }
    public String serializarMultas(ArrayList<Multa> objMultas){
        JsonArray array = new JsonArray();
        JsonObject objJson;
        for(Multa m : objMultas){
            objJson = parseToMulta(m);
            array.add(objJson);
        }
        return array.toString();
    }
    private JsonObject parseToMulta(Multa objMulta){
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("descripcion", objMulta.getDescripcion());
        jsonobj.addProperty("fecha", objMulta.getFecha());
        jsonobj.addProperty("foto", objMulta.getFoto());
        return jsonobj;
        
    }
    private JsonObject parseToIntervalo(IntervaloCongestion objIntervalo){
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("intervalo", objIntervalo.getIntervalo());
        jsonobj.addProperty("cantidad", objIntervalo.getCantidad());
        return jsonobj;
    }
    private JsonObject parseToBahia(Bahia objBahia){
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("identificador", objBahia.getIdentificador());
        return jsonobj;
    }
    
    private JsonObject parseToVehiculo(Vehiculo objVehiculo) {
        JsonObject jsonobj = new JsonObject();

        jsonobj.addProperty("placa", objVehiculo.getPlaca());
        jsonobj.addProperty("marca", objVehiculo.getMarca());
        jsonobj.addProperty("tipo", objVehiculo.getTipo());

        return jsonobj;
    }
}
