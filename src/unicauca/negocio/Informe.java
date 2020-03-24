/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

/**
 *
 * @author JuanCamilo
 */
public class Informe {
    private String dia;
    private String canti_entrada;

    public Informe() {
    }

    public Informe(String dia, String canti_entrada) {
        this.dia = dia;
        this.canti_entrada = canti_entrada;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getCanti_entrada() {
        return canti_entrada;
    }

    public void setCanti_entrada(String canti_entrada) {
        this.canti_entrada = canti_entrada;
    }
    
    
}
