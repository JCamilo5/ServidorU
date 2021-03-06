/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

/**
 *Case que representa una bahia del parqueadero
 * @author JuanCamilo
 */

public class Bahia {
    /**
     * Idetnificador de la bahia
     */
    private String identificador;
    private String estado;
    /**
     * Contructor parametrizado que recibe el identificador de una bahia
     * @param identificador String identificador
     * @param estado estado de la bahia
     */
    public Bahia(String identificador,String estado) {
        this.identificador = identificador;
        this.estado = estado;
    }
    /**
     * Metodo que retorna el identificador de una bahia
     * @return String identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    public String getEstado() {
        return estado;
    }
    
    
}
