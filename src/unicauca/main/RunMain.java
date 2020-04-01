/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicauca.main;

import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import unicauca.negocio.*;
import unicauca.negocio.ConectorJdbc;
import unicauca.negocio.GestorConductorBD;
import unicauca.servicio.ServidorUnicauca;
import utilidades.*;

/**
 *
 * @author JuanCamilo
 */
public class RunMain {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
      ServidorUnicauca servidor = new ServidorUnicauca();
      servidor.iniciar();

    }
}
