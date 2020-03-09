/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author JuanCamilo
 */
public class Parqueadero {

    private final ConectorJdbc conector;

    public Parqueadero() {
        conector = ConectorJdbc.getConector();
    }

    /**
     * Metodo que manda a la base de datos a registra un ingreso
     *
     * @param cedula cedula del conductor
     * @param placa placa del vehiculo
     * @param bahia bahia asignada
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void registrarIngreso(String cedula, String placa, int bahia) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String fecha = getFechaActual();

        String sql = "INSERT INTO INGRESO(idcedulacond,placa,baid,fecha_entrada)"
                + " VALUES ('" + cedula + "','" + placa + "'," + bahia + ",'" + fecha + "');";
        conector.actualizar(sql);
        conector.desconectarse();
    }

    /**
     * Consulta los las bahias cuyo estado sea ocupado
     *
     * @return Arreglo con las bahias
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Bahia> consutarOcupados() throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String sql = "SELECT * FROM bahia \n"
                + "WHERE baestado = 'Ocupado' ";
        conector.crearConsulta(sql);
        ArrayList<Bahia> bahias = new ArrayList<>();
        while (conector.getResultado().next()) {
            String id = conector.getResultado().getString("baid");
            Bahia bahia = new Bahia(id);
            bahias.add(bahia);
        }
        conector.desconectarse();
        return bahias;
    }
    /**
     * Metodo que registra la salida de un vehiculo
     * @param bahia bahia a liberar
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void registrarSalida(String bahia) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String fecha = getFechaActual();
        String sql = "UPDATE ingreso set fecha_salida = '"+fecha+"'"
                + " where baid = "+bahia+" and fecha_salida is null;";
        conector.actualizar(sql);
        conector.desconectarse();
    }
    /**
     * Metodo que obtiene la fecha del sistema
     * @return fecha actual
     */
    private String getFechaActual() {
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);

        String fActual = dia + "-" + (mes + 1) + "-" + año + " " + hora + ":" + minuto;

        return fActual;
    }
}
