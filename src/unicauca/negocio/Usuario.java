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
public class Usuario {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String genero;
    private String fechaNaci;
    private String user;
    private String password;
    private String privilegio;
    
    public Usuario(){
        
    }

    public Usuario(String cedula, String nombres, String apellidos,String genero, String fechaNaci, String user, String password, String privilegio) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaNaci = fechaNaci;
        this.user = user;
        this.password = password;
        this.privilegio = privilegio;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
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

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNaci() {
        return fechaNaci;
    }

    public void setFechaNaci(String fechaNaci) {
        this.fechaNaci = fechaNaci;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
}
