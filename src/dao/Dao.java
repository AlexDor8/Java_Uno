package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Carta;
import model.Jugador;
import model.Carta.Color;
import model.Carta.Numero;

public class Dao {
	// Nombre de la BBDD
	public static final String SCHEMA_NAME = "dam2tm06uf2p1";
	// Por defecto, el usuario es root y la contraseña está vacía"
	public static final String USER_CONNECTION = "root";
	public static final String PASS_CONNECTION = "";
	// Ruta de la BBDD
	public static final String CONNECTION = "jdbc:mysql://localhost:3306/" + SCHEMA_NAME
			+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	// Todas la consultas que haremos a la BBDD:
	public static final String INSERT_JUGADOR = "INSERT INTO JUGADOR(usuario, password, nombre, partidas, ganadas) VALUES (?,?,?,?,?)";
	public static final String SELECT_JUGADOR = "SELECT * FROM JUGADOR";
	public static final String INSERT_CARTA = "INSERT INTO CARTA (id_jugador, numero, color) VALUES (?,?,?)";
	public static final String SELECT_CARTA = "SELECT * FROM CARTA where id_jugador = ?";
	public static final String INSERT_PARTIDA = "INSERT INTO PARTIDA (id_carta) VALUES (?)";
	public static final String DELETE_CARTA = "DELETE FROM CARTA where id = ?";
	public static final String DELETE_CARTA_PARTIDA = "DELETE FROM PARTIDA where id_carta = ?";
	public static final String DELETE_ALL_CARTA = "DELETE FROM CARTA where id_jugador = ?";

	private Connection conexion;

	// Funcion para conectarnos a la BBDD
	public void connectar() throws SQLException {
		conexion = DriverManager.getConnection(CONNECTION, USER_CONNECTION, PASS_CONNECTION);
	}

	// Funcion para desconectarnos de la BBDD
	public void desconectar() throws SQLException {
		if (conexion != null) {
			conexion.close();
		}
	}

	/*
	 * Funcion para insertar un jugador, le pasomos por parametro el jugador y
	 * asignamos los atributos del PreparedStatement que corresponde con las filas
	 * de la tabla jugador haciendo gets de cada uno de los atributos
	 */
	public void insertarJugador(Jugador jugador) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(INSERT_JUGADOR)) {
			ps.setString(1, jugador.getUsuario());
			ps.setString(2, jugador.getPassword());
			ps.setString(3, jugador.getNombre());
			ps.setInt(4, jugador.getPartidas());
			ps.setInt(5, jugador.getGanadas());
			ps.execute();
		}
	}

	/*
	 * Hacemos un select de todos los jugadores de la tabla jugador y almacenamos
	 * cada uno de esos jugadores en un ArrayList. Devolvemos ese ArrauList
	 */
	public ArrayList<Jugador> todosJugadores() throws SQLException {
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

		try (Statement st = conexion.createStatement()) {
			try (ResultSet rs = st.executeQuery(SELECT_JUGADOR)) {
				while (rs.next()) {
					Jugador jugador = new Jugador(rs.getInt("id"), rs.getString("usuario"), rs.getString("password"),
							rs.getString("nombre"), rs.getInt("partidas"), rs.getInt("ganadas"));
					jugadores.add(jugador);
				}
			}
		}
		return jugadores;
	}

	// Insertamos en la tabla carta la carta que le pasamos por parámetro
	public void insertarCarta(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(INSERT_CARTA)) {
			ps.setInt(1, carta.getId_jugador());
			ps.setString(2, String.valueOf(carta.getNumero()));
			ps.setString(3, String.valueOf(carta.getColor()));
			ps.execute();
		}
	}

	/*
	 * Hacemos un select de todas las cartas por jugador, pasandole por parametro la
	 * id del jugador. Almacenamos todas las cartas es una ArrayList de carta.
	 * Delvolvemos ese ArrayList.
	 */
	public ArrayList<Carta> todasCartasPorJugador(int id_jugador) throws SQLException {
		ArrayList<Carta> cartas = new ArrayList<Carta>();

		try (PreparedStatement ps = conexion.prepareStatement(SELECT_CARTA)) {
			ps.setInt(1, id_jugador);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					// En el caso del Color y el Numero parseamos el String
					Carta carta = new Carta(rs.getInt("id"), rs.getInt("id_jugador"),
							Numero.valueOf(rs.getString("numero")), Color.valueOf(rs.getString("color")));
					cartas.add(carta);
				}
			}
		}
		return cartas;
	}

	// Insertamos la carta en la tabla partida
	public void insertarCartaPartida(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(INSERT_PARTIDA)) {
			ps.setInt(1, carta.getId());
			ps.execute();
		}
	}

	// Eliminamos de la tabla carta la carta que le pasamos por parametro
	public void eliminarCarta(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(DELETE_CARTA)) {
			ps.setInt(1, carta.getId());
			ps.execute();
		}
	}

	// Eliminamos de la tabla paritda la carta que le pasamos por parametro
	public void eliminarCartaPartida(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(DELETE_CARTA)) {
			ps.setInt(1, carta.getId());
			ps.execute();
		}
	}

	// Pasandole por parametro la id del jugador, eliminamos todas las cartas de ese
	// jugador
	public void eliminarTodasCartasJugador(int id_jugador) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(DELETE_ALL_CARTA)) {
			ps.setInt(1, id_jugador);
			ps.execute();
		}
	}

}
