/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.negocio;

import java.util.ArrayList;

/**
 *
 * @author JuanCamilo
 */
public class Conductor {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String genero;
    private String fechaNaci;
    private String rol;
    
    public Conductor(){
          
    }

    public Conductor(String cedula, String nombres, String Apellidos, String genero, String fechaNaci,String rol) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = Apellidos;
        this.genero = genero;
        this.fechaNaci = fechaNaci;
        this.rol = rol;
    }
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.apellidos = Apellidos;
    }

    public String getFechaNaci() {
        return fechaNaci;
    }

    public void setFechaNaci(String fechaNaci) {
        this.fechaNaci = fechaNaci;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    

}
