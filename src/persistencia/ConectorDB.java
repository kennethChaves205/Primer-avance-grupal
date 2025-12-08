
package persistencia;

import java.sql.*;

public class ConectorDB {
    private static final String URL = "jdbc:mysql://localhost:3306/mesa_ayuda?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "admin123";
    private static Connection conexion = null;
    private static boolean mensajeMostrado = false;

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                if (!mensajeMostrado) {
                    System.out.println("Conexión exitosa a MySQL (Beekeeper)");
                    mensajeMostrado = true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
}