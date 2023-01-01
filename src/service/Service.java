package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dao.Dao;
import model.Carta;
import model.Carta.Numero;
import model.Jugador;

public class Service {
	Dao dao;

	public Service() {
		dao = new Dao();
	}

	public void registrar() throws SQLException {
		dao.connectar();
		System.out.println("REGISTRO");
		Scanner entrada = new Scanner(System.in);
		System.out.println("Introduce tu usuario:");
		String usuario = entrada.nextLine();
		System.out.println("Introduce tu contraseña:");
		String contraseña = entrada.nextLine();
		System.out.println("Introduce tu nombre:");
		String nombre = entrada.nextLine();
		Jugador jugador = new Jugador(0, usuario, contraseña, nombre, 0, 0);
		dao.insertarJugador(jugador);
		dao.desconectar();

		login();
	}

	public void login() throws SQLException {
		dao.connectar();
		System.out.println("LOGIN");
		Scanner entrada = new Scanner(System.in);

		ArrayList<Jugador> jugadores = dao.todosJugadores();
		if (jugadores.isEmpty()) {
			System.out.println("No hay nigun jugdaor registrado.");
		} else {
			System.out.println("Introduce tu nombre de usuario:");
			String usuario = entrada.nextLine();
			System.out.println("Introduce tu contraseña:");
			String contraseña = entrada.nextLine();
			for (int i = 0; i < jugadores.size(); i++) {
				if (jugadores.get(i).getUsuario().equals(usuario)
						&& jugadores.get(i).getPassword().equals(contraseña)) {
					System.out.println("Te has logueado con exito.");
					inicioPartida(jugadores.get(i).getId());
				} else {
					System.out.println("El usuario o la contraseña son incorrectos.");
				}
			}
		}

		dao.desconectar();
	}

	public void inicioPartida(int id_jugador) throws SQLException {
		dao.connectar();
		ArrayList<Carta> cartas = dao.todasCartasPorJugador(id_jugador);
		if (cartas.isEmpty()) {
			System.out.println("No tienes cartas en tu mano. Has robado 7 cartas.");
			for (int i = 0; i < 7; i++) {
				Carta carta = new Carta(0, id_jugador);
				dao.insertarCarta(carta);
				cartas.add(carta);
			}
		}
		System.out.println("HA EMPEZADO LA PARTIDA");
		while(cartas != null) {
			int carta_aleatoria_mazo = (int) (Math.random() * cartas.size());
			Carta carta = cartas.get(carta_aleatoria_mazo);
			dao.insertarCartaPartida(carta);
			System.out.println("Has utilizado la " + carta);
			if (carta.getNumero().equals(Numero.MASDOS)) {
				for (int i = 0; i < 2; i++) {
					Carta nuevaCarta = new Carta(0, id_jugador);
					dao.insertarCarta(nuevaCarta);
					System.out.println("Has robado la " + nuevaCarta);
				}
			} else if (carta.getNumero().equals(Numero.MASCUATRO)) {
				for (int i = 0; i < 4; i++) {
					Carta nuevaCarta = new Carta(0, id_jugador);
					dao.insertarCarta(nuevaCarta);
					System.out.println("Has robado la " + nuevaCarta);
				}
			} else if (carta.getNumero().equals(Numero.SALTO) || carta.getNumero().equals(Numero.CAMBIO)) {
				dao.eliminarCartaPartida(carta);
				dao.eliminarCarta(carta);
				break;
			} 
			dao.eliminarCarta(carta);
			
			if (cartas.size() > 25) {
				System.out.println("Has perdido, tienes demasiadas cartas en tu mano");
				dao.eliminarTodasCartasJugador(id_jugador);
				break;
			}
			
		}

		dao.desconectar();
	}
}
