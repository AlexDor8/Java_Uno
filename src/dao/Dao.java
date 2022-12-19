package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
	public static final String SCHEMA_NAME = "dam2tm06uf2p1";
	public static final String USER_CONNECTION = "root";
	public static final String PASS_CONNECTION = "";
	public static final String CONNECTION = "jdbc:mysql://localhost:3306/" + SCHEMA_NAME
			+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String INSERT_JUGADOR = "INSERT INTO JUGADOR(usuario, password, nombre, partidas, ganadas) VALUES (?,?,?,?,?)";
	public static final String SELECT_JUGADOR_BY_USUARIO_CONTRASENA = "SELECT * FROM JUGADOR WHERE usuario = ? && password = ?";
	
	private Connection conexion;

	public void connectar() throws SQLException {
		conexion = DriverManager.getConnection(CONNECTION, USER_CONNECTION, PASS_CONNECTION);
	}

	public void desconectar() throws SQLException {
		if (conexion != null) {
			conexion.close();
		}
	}
	
}
