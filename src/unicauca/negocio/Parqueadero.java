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

    public ArrayList<Bahia> consutarOcupados() throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String sql = "SELECT * FROM bahia \n"
                + "WHERE baestado = 'Ocupado' ";
        conector.crearConsulta(sql);
        ArrayList<Bahia> bahias = new ArrayList<>();
        while(conector.getResultado().next()){
            String id = conector.getResultado().getString("baid");
            Bahia bahia= new Bahia(id);
            bahias.add(bahia);
        }
        return bahias;
    }

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
