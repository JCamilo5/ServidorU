/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

import java.sql.SQLException;

/**
 *
 * @author JuanCamilo
 */
public class GestorVigilanteBD {

    private final ConectorJdbc conector;

    public GestorVigilanteBD() {
        conector = ConectorJdbc.getConector();
    }
    /**
     * Metodo que agrega un vigilante a la base de datos
     * @param ced cedula del vigilante
     * @param emp empresa a la cual pertenece
     * @param usua usuario del vigilante
     * @param noms nombres del vigilante
     * @param apells apellidos del vigilante
     * @param genero genero del vigilante
     * @param fechaNaci fecha de nacimiento del vigilante
     * @param contra contraseña del vigilante
     * @param puesto ubicacion 
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void agregarVigilante(String ced, String emp, String usua, String noms, String apells, String genero, String fechaNaci, String contra, String puesto) throws ClassNotFoundException, SQLException {
        conector.conectarse();
        
        String sql = "INSERT INTO \n"
                + "vigilante (idcedula,vigempresa,vigusuario,connombres,conapellidos,congenero,confechanacimiento,vigcontra,vigpuesto)\n"
                + "values ("+ced+",'"+emp+"','"+usua+"','"+noms+"','"+apells+"','"+genero+"','"+fechaNaci+"','"+contra+"','"+puesto+"')";
        conector.actualizar(sql);
        conector.desconectarse();
    }
}
