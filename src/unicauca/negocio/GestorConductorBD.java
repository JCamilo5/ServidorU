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
public class GestorConductorBD {

    private final ConectorJdbc conector;

    public GestorConductorBD() {
        this.conector = ConectorJdbc.getConector();
    }

    /**
     * Metodo que inserta un conductor en la base de datos
     *
     * @param cedula cedula del conductor
     * @param nombres nombres de l conductor
     * @param apellidos apellidos del conductor
     * @param genero genero del conductor
     * @param fechaNaci fecha de nacimineto del conductor
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void agregarConductor(String cedula, String nombres, String apellidos, String genero, String fechaNaci) throws ClassNotFoundException, SQLException {
        //Las columnas de tipo var charying requieren ir entre comillas dobles por eso ls backslash
        conector.conectarse();
        String sqlCo = "INSERT INTO "
                + "conductor "
                + "(idcedulacond,connombres,conapellidos,congenero,confechanacimiento)"
                + "VALUES ('" + cedula + "','" + nombres + "','" + apellidos + "','" + genero + "','" + fechaNaci + "');";
        conector.actualizar(sqlCo);
        conector.desconectarse();
    }

    public void asociarRol(String cedula, String rol) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String sql = "INSERT INTO rolconduc (idcedulacond,idrol)\n"
                + "values('" + cedula + "','" + rol + "')";
        conector.actualizar(sql);
        conector.desconectarse();
    }

    public void agregarVehiculo(String placa, String marca, String tipo) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String sqlVe = "INSERT INTO "
                + "vehiculo"
                + " (NOPLACA,MARCA,TIPO) "
                + "VALUES('" + placa + "','" + marca + "','" + tipo + "')";
        conector.actualizar(sqlVe);
        conector.desconectarse();
    }

    public void asociarVehiculo(String cedula, String placa) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String sql = "INSERT INTO conducvehicul (idcedulacond,noplaca) values "
                + "('" + cedula + "','" + placa + "');";
        conector.actualizar(sql);
        conector.desconectarse();
    }

    public Conductor consultarConductor(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();

        conector.crearConsulta("SELECT * FROM conductor WHERE idcedulacond = '" + cedula + "'");
        Conductor conductor = null;
        if (conector.getResultado().next()) {
            String con_cedula = conector.getResultado().getString("idcedulacond");
            String con_nombres = conector.getResultado().getString("connombres");
            String con_apellidos = conector.getResultado().getString("conapellidos");
            String con_genero = conector.getResultado().getString("congenero");
            String con_fechaNaci = conector.getResultado().getString("confechanacimiento");
            String con_rol = consultarRoles(cedula);
            conductor = new Conductor(con_cedula, con_nombres, con_apellidos, con_genero, con_fechaNaci, con_rol);
        }
        conector.desconectarse();
        return conductor;
    }

    public ArrayList<Vehiculo> consultarVehiculoCon(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String consulta = "SELECT v.noplaca,v.marca,v.tipo\n"
                + "FROM\n"
                + "conducvehicul cv INNER JOIN vehiculo v on cv.noplaca = v.noplaca\n"
                + "WHERE cv.idcedulacond = '" + cedula + "'";
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

    /**
     * Metodo que registra una multa
     *
     * @param placa placa del vehiculo
     * @param descripcion descripcion del porque la multa
     * @param foto direccion en el equipo donde esta la foto
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void registrarMulta(String placa, String descripcion, String foto) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String fecha = getFechaActual();
        String sql = "INSERT INTO multa(mulplaca,mulfecha,muldescripcion,mulfotografia)\n"
                + "VALUES ('" + placa + "','" + fecha + "','" + descripcion + "','" + foto + "');";
        conector.actualizar(sql);
        conector.desconectarse();
    }
    /**
     * 
     * @param placaV
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public ArrayList<Multa> consutarMulta(String placaV) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String sql = "SELECT * FROM MULTA WHERE mulplaca = '" + placaV + "';";
        conector.crearConsulta(sql);
        ArrayList<Multa> multas = new ArrayList<>();
        Multa multa;
        while (conector.getResultado().next()) {
            String fecha = conector.getResultado().getString("mulfecha");
            String descripcion = conector.getResultado().getString("muldescripcion");
            String foto = conector.getResultado().getString("mulfotografia");
            multa = new Multa(descripcion, fecha, foto);
            multas.add(multa);
        }
        conector.desconectarse();
        return multas;

    }

    /**
     * Metodo que obtiene la fecha del sistema
     *
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

    /**
     * Metodo que devuelve el rol de un conductor teniedo en cuenta su prioridad
     *
     * @param cedula cirterio de busqueda
     * @return rol
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private String consultarRoles(String cedula) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        String consulta = "SELECT r.rol\n"
                + "FROM\n"
                + "rolconduc rc INNER JOIN roles r\n"
                + "ON rc.idrol = r.idrol\n"
                + "WHERE rc.idcedulacond = '" + cedula + "'";
        conector.crearConsulta(consulta);
        ArrayList<String> lista_roles = new ArrayList<>();
        String rol;
        while (conector.getResultado().next()) {
            rol = conector.getResultado().getString("rol");
            lista_roles.add(rol);
        }
        conector.desconectarse();
        rol = "Visitante";
        if (lista_roles.contains("Docente")) {
            rol = "Docente";

        } else {
            if (lista_roles.contains("Administrativo")) {
                rol = "Administrativo";

            } else {
                if (lista_roles.contains("Estudiante")) {
                    rol = "Estudiante";

                } else {
                    if (lista_roles.contains("Trabajador")) {
                        rol = "Trabajador";
                    }
                }
            }
        }
        return rol;

    }
}
