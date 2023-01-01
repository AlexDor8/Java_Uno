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
	public static final String SCHEMA_NAME = "dam2tm06uf2p1";
	public static final String USER_CONNECTION = "root";
	public static final String PASS_CONNECTION = "";
	public static final String CONNECTION = "jdbc:mysql://localhost:3306/" + SCHEMA_NAME
			+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String INSERT_JUGADOR = "INSERT INTO JUGADOR(usuario, password, nombre, partidas, ganadas) VALUES (?,?,?,?,?)";
	public static final String SELECT_JUGADOR = "SELECT * FROM JUGADOR";
	public static final String INSERT_CARTA = "INSERT INTO CARTA (id_jugador, numero, color) VALUES (?,?,?)";
	public static final String SELECT_CARTA = "SELECT * FROM CARTA where id_jugador = ?";
	public static final String INSERT_PARTIDA = "INSERT INTO PARTIDA (id_carta) VALUES (?)";
	public static final String DELETE_CARTA = "DELETE FROM CARTA where id = ?";
	public static final String DELETE_CARTA_PARTIDA = "DELETE FROM PARTIDA where id_carta = ?";
	
	private Connection conexion;

	public void connectar() throws SQLException {
		conexion = DriverManager.getConnection(CONNECTION, USER_CONNECTION, PASS_CONNECTION);
	}

	public void desconectar() throws SQLException {
		if (conexion != null) {
			conexion.close();
		}
	}

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

	public void insertarCarta(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(INSERT_CARTA)) {
			ps.setInt(1, carta.getId_jugador());
			ps.setString(2, String.valueOf(carta.getNumero()));
			ps.setString(3, String.valueOf(carta.getColor()));
			ps.execute();
		}
	}

	public ArrayList<Carta> todasCartasPorJugador(int id_jugador) throws SQLException {
		ArrayList<Carta> cartas = new ArrayList<Carta>();

		try (PreparedStatement ps = conexion.prepareStatement(SELECT_CARTA)) {
			ps.setInt(1, id_jugador);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Carta carta = new Carta(rs.getInt("id"), rs.getInt("id_jugador"), Numero.valueOf(rs.getString("numero")) ,
							Color.valueOf(rs.getString("color")));
					cartas.add(carta);
				}
			}
		}
		return cartas;
	}
	
	public void insertarCartaPartida(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(INSERT_PARTIDA)) {
			ps.setInt(1, carta.getId());
			ps.execute();
		}
	}
	
	public void eliminarCarta(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(DELETE_CARTA)) {
			ps.setInt(1, carta.getId());
			ps.execute();
		}
	}
	
	public void eliminarCartaPartida(Carta carta) throws SQLException {
		try (PreparedStatement ps = conexion.prepareStatement(DELETE_CARTA)) {
			ps.setInt(1, carta.getId());
			ps.execute();
		}
	}

}
