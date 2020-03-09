/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.servicio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import unicauca.negocio.*;
import utilidades.Serializar;

/**
 *
 * @author JuanCamilo
 */
public class ServidorUnicauca implements Runnable {

    private GestorUsuarioBD gestorUsuarios;
    private GestorConductorBD gestorConductor;
    private GestorVigilanteBD gestorVigilante;
    private Parqueadero parqueadero;
    private final Serializar objSerializador;

    private static ServerSocket serverSocket;
    private static Socket socket;
    private Scanner entradaDecorada;
    private PrintStream salidaDecorada;
    private static final int PUERTO = 5000;

    public ServidorUnicauca() {
        gestorUsuarios = new GestorUsuarioBD();
        gestorConductor = new GestorConductorBD();
        gestorVigilante = new GestorVigilanteBD();
        parqueadero = new Parqueadero();
        objSerializador = new Serializar();
    }

    /**
     * Logica de un servidor
     */
    public void iniciar() {
        abrirPuerto();

        while (true) {
            esperarCliente();
            lanzarHilo();
        }
    }

    /**
     * Abre el puesto para comunicarse
     */
    private static void abrirPuerto() {
        try {
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor escuchando por el puerto: " + PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(ServidorUnicauca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Espera que el cliente se conecte y le devuelve un Socket
     */
    private static void esperarCliente() {
        try {
            socket = serverSocket.accept();
            System.out.println("Cliente conectado...");
        } catch (IOException ex) {
            Logger.getLogger(ServidorUnicauca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lanza el Hilo
     */
    private static void lanzarHilo() {
        new Thread(new ServidorUnicauca()).start();
    }

    @Override
    public void run() {
        try {
            crearFlujos();
            leerFlujos();
            cerrarFlujos();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException | SQLException excep) {
            Logger.getLogger(ServidorUnicauca.class.getName()).log(Level.SEVERE, null, excep);
        }
    }

    /**
     * Crea los flujos con el Socket
     *
     * @throws IOException
     */
    private void crearFlujos() throws IOException {
        salidaDecorada = new PrintStream(socket.getOutputStream());
        entradaDecorada = new Scanner(socket.getInputStream());
    }

    private void leerFlujos() throws ClassNotFoundException, SQLException {
        if (entradaDecorada.hasNextLine()) {
            //Extrae el flujo que envia el cliente
            String peticion = entradaDecorada.nextLine();
            decodificarPeticion(peticion);
        } else {
            salidaDecorada.flush();
            salidaDecorada.println("No_Encontrado.");
        }
    }

    private void cerrarFlujos() throws IOException {
        salidaDecorada.close();
        entradaDecorada.close();
        socket.close();
    }

    /**
     * Decodifica la petición, extrayendo la acción y los parametros
     *
     * @param peticion petición completa al estilo "accion, información"
     */
    private void decodificarPeticion(String peticion) throws ClassNotFoundException, SQLException {
        StringTokenizer tokens = new StringTokenizer(peticion, ",");
        String parametros[] = new String[tokens.countTokens() + 1];

        int i = 0;
        while (tokens.hasMoreTokens()) {
            parametros[i++] = tokens.nextToken();
        }

        String accion = parametros[0];
        procesarAccion(accion, parametros);
    }

    /**
     *
     * @param accion
     * @param parametros
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void procesarAccion(String accion, String parametros[]) throws ClassNotFoundException, SQLException {
        //Informacion del usuario
        String user_user;
        String user_password;
        Usuario usuario;
        //InformacionConductor
        String con_cedula;
        Conductor conductor;
        //Informacion Vehiculo
        String cedulaDueno;


        switch (accion) {
            case "Consultar Usuario":
                user_user = parametros[1];
                user_password = parametros[2];
                usuario = gestorUsuarios.consultarUsuario(user_user, user_password);
                if (usuario == null) {
                    salidaDecorada.println("No se encontro el usuario");
                } else {
                    JsonObject objJsonUsuario = objSerializador.parseToJSONUsuario(usuario);
                    salidaDecorada.println(objJsonUsuario.toString());
                }
                break;

            case "Consultar Conductor":

                con_cedula = parametros[1];
                conductor = gestorConductor.consultarConductor(con_cedula);
                if (conductor == null) {
                    salidaDecorada.println("No se encontro a ningun conductor con esa cedula");
                } else {
                    JsonObject objJsonConductor = objSerializador.parseToJSONConductor(conductor);
                    salidaDecorada.println(objJsonConductor.toString());
                }
                break;
            case "Consultar Vehiculos de Conductor":

                cedulaDueno = parametros[1];
                ArrayList<Vehiculo> vehiculos = new ArrayList<>();
                vehiculos = gestorConductor.consultarVehiculoCon(cedulaDueno);
                if (vehiculos.isEmpty()) {

                    salidaDecorada.println("El conductor no tiene asociado ningun vehiculo");

                } else {

                    salidaDecorada.println(objSerializador.serializarVehiculos(vehiculos));
                }
                break;

            case "Agregar Vehiculo":
                String placa_ve = parametros[1];
                String marca_ve = parametros[2];
                String tipo_ve = parametros[3];
                try {
                    gestorConductor.agregarVehiculo(placa_ve, marca_ve, tipo_ve);
                    salidaDecorada.println("Vehiculo agregado con exito");
                } catch (Exception e) {
                    salidaDecorada.println("Error");
                }

                break;

            case "Registrar Conductor":
                String cedula_con = parametros[1];
                String nombres_con = parametros[2];
                String apellidos_con = parametros[3];
                String genero_con = parametros[4];
                String fechaNaci = parametros[5];
                try {
                    gestorConductor.agregarConductor(cedula_con, nombres_con, apellidos_con, genero_con, fechaNaci);
                    salidaDecorada.println("Conductor Agregado");
                } catch (Exception e) {
                    salidaDecorada.println("Error");
                }
                
                break;
            case "Asociar Rol":
                con_cedula = parametros[1];
                String rol = parametros[2];
                try {
                    gestorConductor.asociarRol(con_cedula, rol);
                    salidaDecorada.println("Exito");
                } catch (Exception e) {
                    salidaDecorada.println("Error");
                }
                break;
            case "Asociar Vehiculo":
                String ced = parametros[1];
                String ve_placa = parametros[2];
                try {
                    gestorConductor.asociarVehiculo(ced, ve_placa);
                    salidaDecorada.println("Asociacion Exitosa");
                } catch (Exception e) {
                    salidaDecorada.println("Error");
                }
                
                break;

            case "Ingresar Vehiculo":
                con_cedula = parametros[1];
                ve_placa = parametros[2];
                int bahia = Integer.parseInt(parametros[3]);
                try {
                    parqueadero.registrarIngreso(con_cedula, ve_placa, bahia);
                    salidaDecorada.println("Registro Exitoso");
                } catch (Exception e) {
                    salidaDecorada.println("Error");
                }
                break;

            case "Obtener Ocupados":
                ArrayList<Bahia> bahias = new ArrayList<>();
                bahias = parqueadero.consutarOcupados();
                if (bahias.isEmpty()) {
                    salidaDecorada.println("Parqueadero libre");
                } else {
                    salidaDecorada.println(objSerializador.serializarBahias(bahias));
                }
                break;

            case "Agregar Vigilante":
                String vig_ced = parametros[1];
                String emp = parametros[2];
                String usu = parametros[3];
                String nom = parametros[4];
                String apell = parametros[5];
                String gen = parametros[6];
                String fecNa = parametros[7];
                String cont = parametros[8];
                String pues = parametros[9];
                try {
                    gestorVigilante.agregarVigilante(vig_ced, emp, usu, nom, apell, gen, fecNa, cont, pues);
                    salidaDecorada.println("Registro Exitoso");
                } catch (Exception e) {
                    salidaDecorada.println("Error");
                }
                break;
            
            case "Registrar Salida":
                String baid = parametros[1];
                try {
                    parqueadero.registrarSalida(baid);
                    salidaDecorada.println("Registro Exitoso");
                } catch (Exception e) {
                    salidaDecorada.println("Error");
                }
                
                break;

        }
    }
}
