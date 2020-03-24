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
    /**
     * Metodo que retorna un usuario de la base de datos
     * @param user usuario
     * @param password contraseña
     * @return usuario
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Usuario consultarUsuario(String user, String password) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        Usuario miUsuario = null;
        String consultaAdmin = "SELECT * FROM ADMINISTRADOR WHERE \n"
                + "usuaradmin = '" + user + "' AND usuarcontras = '" + password + "'";
        conector.crearConsulta(consultaAdmin);
        if (conector.getResultado().next()) {
            String user_cedula = conector.getResultado().getString("idcedula");
            String user_nombres = conector.getResultado().getString("connombres");
            String user_apellidos = conector.getResultado().getString("conapellidos");
            String user_genero = conector.getResultado().getString("congenero");
            String user_fechaNaci = conector.getResultado().getString("confechanacimiento");
            String user_user = conector.getResultado().getString("usuaradmin");
            String user_password = conector.getResultado().getString("usuarcontras");
            String user_privilegio = "administrador";
            miUsuario = new Usuario(user_cedula, user_nombres, user_apellidos,user_genero,user_fechaNaci, user_user, user_password, user_privilegio);
            conector.desconectarse();
            return miUsuario;
        }
        String consultaVigi = "SELECT * FROM VIGILANTE \n"
                + "WHERE vigusuario = '"+user+"' AND vigcontra = '"+password+"'";
        conector.crearConsulta(consultaVigi);
        if (conector.getResultado().next()) {
            String user_cedula = conector.getResultado().getString("idcedula");
            String user_nombres = conector.getResultado().getString("connombres");
            String user_apellidos = conector.getResultado().getString("conapellidos");
            String user_genero = conector.getResultado().getString("congenero");
            String user_fechaNaci = conector.getResultado().getString("confechanacimiento");
            String user_user = conector.getResultado().getString("vigusuario");
            String user_password = conector.getResultado().getString("vigcontra");
            String user_privilegio = "vigilante";
            miUsuario = new Usuario(user_cedula, user_nombres, user_apellidos, user_genero,user_fechaNaci, user_user, user_password, user_privilegio);
            conector.desconectarse();
            return miUsuario;
        }
        conector.desconectarse();
        return miUsuario;
    }

    public ArrayList<Vehiculo> consultarVehiculosCon(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        conector.crearConsulta("select v.noplaca,v.marca,v.tipo\n"
                + "FROM\n"
                + "conducvehicul cv inner join vehiculo v on cv.noplaca = v.noplaca\n"
                + "where cv.idcedulacond ='" + cedula + "'");
        ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
        Vehiculo miVehiculo;
        while (conector.getResultado().next()) {
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
