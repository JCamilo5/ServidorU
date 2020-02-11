/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.management.monitor.Monitor;

/**
 *
 * @author JuanCamilo
 */
public class GestorUsuarioBD {

    private final ConectorJdbc conector;

    public GestorUsuarioBD() {
        this.conector = ConectorJdbc.getConector();
    }

    public Usuario consultarUsuario(String user, String password) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        conector.crearConsulta("select * from usuarios as us where us.user = '" + user + "' and us.password = '" + password + "'");
        Usuario miUsuario = null;
        if (conector.getResultado().next()) {
            String user_cedula = conector.getResultado().getString("cedula");
            String user_nombres = conector.getResultado().getString("nombres");
            String user_apellidos = conector.getResultado().getString("apellidos");
            String user_fechaNaci = conector.getResultado().getString("fechaNaci");
            String user_user = conector.getResultado().getString("user");
            String user_password = conector.getResultado().getString("password");
            String user_privilegio = conector.getResultado().getString("privilegio");
            miUsuario = new Usuario(user_cedula, user_nombres, user_apellidos, user_fechaNaci, user_user, user_password,user_privilegio);
        }
        conector.desconectarse();
        return miUsuario;
    }

    public ArrayList<Vehiculo> consultarVehiculosCon(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        conector.crearConsulta("select v.noplaca,v.marca,v.tipo\n"
                + "FROM\n"
                + "conducvehicul cv inner join vehiculo v on cv.noplaca = v.noplaca\n"
                + "where cv.idcedulacond ='"+cedula+"'");
        ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
        Vehiculo miVehiculo;
        while(conector.getResultado().next()){
            String placa = conector.getResultado().getString("noplaca");
            String marca = conector.getResultado().getString("marca");
            String tipo = conector.getResultado().getString("tipo");
            
            miVehiculo = new Vehiculo(placa, marca, tipo);
            listaVehiculos.add(miVehiculo);
        }
        conector.desconectarse();
        return listaVehiculos;
    }
}
