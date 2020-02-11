/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

import java.sql.*;

/**
 *
 * @author JuanCamilo
 */
public class ConectorJdbc {
    
    private static ConectorJdbc conector;

    private Connection conexion;
    private ResultSet resultado;
    private Statement estamento;
    private final String url = "jdbc:postgresql://localhost:5432/Unicauca";
    private final String usuario = "postgres";
    private final String contrasenia = "system";

    /**
     * Constructor privado para evitar que los crientes creen varias instancias
     */
    private  ConectorJdbc() {
        
    }
    /**
     * Metodo estatico que devuelve una sola instancia de la clase
     * @return unica instancia de la clase
     */
    public synchronized static ConectorJdbc getConector(){
        if(conector == null){
            conector = new ConectorJdbc();
        }
        return conector;
    }

    public void conectarse() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            if (conexion != null) {
                System.out.println("Conecto!!!!!!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("No conecto");
        }
    }

    public void crearConsulta(String sql) throws SQLException {
        estamento = conexion.createStatement();
        resultado = estamento.executeQuery(sql);
    }

    /**
     * * Ejecuta una consulta de tipo insert, update o delete      
     * @param sql 
     *    
     * @throws SQLException     
     */
    public void actualizar(String sql) throws SQLException {
        estamento = conexion.createStatement();
        estamento.executeUpdate(sql);
    }

    public void desconectarse() throws SQLException {
        if (resultado != null) {
            resultado.close();
        }
        estamento.close();
        conexion.close();
    }

    /**
     * Devuelve todo el conjunto de resultados.
     *
     * @return ResultSet
     */
    public ResultSet getResultado() {
        return this.resultado;
    }

}
