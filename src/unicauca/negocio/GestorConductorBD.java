/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author JuanCamilo
 */
public class GestorConductorBD {

    private final ConectorJdbc conector;

    public GestorConductorBD() {
        this.conector = new ConectorJdbc();
    }

    public Conductor consultarConductor(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();

        conector.crearConsulta("select * from conductor where idcedulacond = '" + cedula + "'");
        Conductor conductor = null;
        if (conector.getResultado().next()) {
            String con_cedula = conector.getResultado().getString("idcedulacond");
            String con_nombres = conector.getResultado().getString("connombres");
            String con_apellidos = conector.getResultado().getString("conapellidos");
            String con_genero = conector.getResultado().getString("congenero");
            String con_fechaNaci = conector.getResultado().getString("conFechaNaci");
            conductor = new Conductor(con_cedula, con_nombres, con_apellidos, con_genero, con_fechaNaci);
        }
        conector.desconectarse();
        return conductor;
    }

    public ArrayList<Vehiculo> consultarVehiculoCon(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String consulta = "select v.noplaca,v.marca,v.tipo\n"
                + "FROM\n"
                + "conducvehicul cv inner join vehiculo v on cv.noplaca = v.noplaca\n"
                + "where cv.idcedulacond = '" + cedula + "'";
        conector.crearConsulta(consulta);
        ArrayList<Vehiculo> mis_vehiculos = new ArrayList<>();
        Vehiculo miVehiculo;
        while (conector.getResultado().next()) {
            String placa = conector.getResultado().getString("noplaca");
            String marca = conector.getResultado().getString("marca");
            String tipo = conector.getResultado().getString("tipo");
            miVehiculo = new Vehiculo(placa, marca, tipo);
            mis_vehiculos.add(miVehiculo);
        }
        conector.desconectarse();
        return mis_vehiculos;
    }

    public String consultarRoles(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String consulta = "select r.rol\n"
                + "FROM\n"
                + "rolconduc rc inner join roles r\n"
                + "on rc.idrol = r.idrol\n"
                + "where rc.idcedulacond = '"+ cedula +"'";
        conector.crearConsulta(consulta);
        ArrayList<String> lista_roles = new ArrayList<>();
        String rol;
        while (conector.getResultado().next()) {            
            rol = conector.getResultado().getString("rol");
            lista_roles.add(rol);
        }
        conector.desconectarse();
        rol = "visitante";
        if (lista_roles.contains("docente             ")) {
            rol = "docente";

        } else {
            if (lista_roles.contains("administrativo      ")) {
                rol = "administrativo";

            } else {
                if (lista_roles.contains("estudiante          ")) {
                    rol = "estudiante";

                } else {
                    if (lista_roles.contains("trabajador          ")) {
                        rol = "trabajador";
                    }
                }
            }
        }
        return rol;
        
    }
}
