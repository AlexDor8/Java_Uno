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

	/*
	 * Si es la primera vez que el usario accede, tendrá que registrarse
	 * introduciendo su usuario, contraseña y nombre. Una vez se haya registrado
	 * accederá al login
	 */
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
		// En la id pondremos un 0 ya que es autoincrementable
		Jugador jugador = new Jugador(0, usuario, contraseña, nombre, 0, 0);
		// Insertamos ese usuario en la BBDD
		dao.insertarJugador(jugador);
		dao.desconectar();
		// Una vez se haya registrado accederá al login
		login();
	}

	public void login() throws SQLException {
		dao.connectar();
		System.out.println("LOGIN");
		Scanner entrada = new Scanner(System.in);
		/*
		 * Guardamos en un ArrayList de jugadores el select con todos los judadores de
		 * la BBDD
		 */
		ArrayList<Jugador> jugadores = dao.todosJugadores();
		/*
		 * En caso de que no haya no haya ningun jugador le mostramos el siguiente
		 * mensaje y acaba el programa
		 */
		if (jugadores.isEmpty()) {
			System.out.println("No hay nigun jugdaor registrado.");
			/*
			 * En caso de que si haya jugadores, comprobamos que los datos sean correctos y
			 * en el caso de que lo sean, empezamos la partida
			 */
		} else {
			System.out.println("Introduce tu nombre de usuario:");
			String usuario = entrada.nextLine();
			System.out.println("Introduce tu contraseña:");
			String contraseña = entrada.nextLine();
			for (int i = 0; i < jugadores.size(); i++) {
				if (jugadores.get(i).getUsuario().equals(usuario)
						&& jugadores.get(i).getPassword().equals(contraseña)) {
					System.out.println("Te has logueado con exito.");
					// Iniciamos la paritda pasandole la id del jugador por parámetro
					inicioPartida(jugadores.get(i).getId());
				} else {
					System.out.println("El usuario o la contraseña son incorrectos.");
				}
			}
		}

		dao.desconectar();
	}

	/*
	 * En el caso de que el jugador no tenga cartas en su mano, ya sea porque es la
	 * primera vez que juega o porque ha terminado su anterior partida, roba 7
	 * cartas. Como le hemos pasado por parametro la id del jugador, guardamos en un
	 * ArrayList de cartas el select con todas las cartas de ese jugador.
	 */
	public void inicioPartida(int id_jugador) throws SQLException {
		dao.connectar();
		ArrayList<Carta> cartas = dao.todasCartasPorJugador(id_jugador);
		if (cartas.isEmpty()) {
			System.out.println("No tienes cartas en tu mano. Has robado 7 cartas.");
			for (int i = 0; i < 7; i++) {
				Carta carta = new Carta(0, id_jugador);
				// Insert de la nueva carta que hemos generado
				dao.insertarCarta(carta);
				cartas.add(carta);
			}
		}
		// Una vez el jugador tiene las cartas en su mano, empezamos la partida
		partida(id_jugador);

		dao.desconectar();
	}

	public void partida(int id_jugador) throws SQLException {
		ArrayList<Carta> cartas = dao.todasCartasPorJugador(id_jugador);
		System.out.println("HA EMPEZADO LA PARTIDA");
		/*
		 * La partida durará hasta que el jugador se quede sin cartas o tenga mala
		 * suerte y le toquen muchos MASDOS Y MASCUATRO y llegué a 25 cartas, en cuyo
		 * caso, perderá la partida. Si saca un CAMBIO o SALTO finalizará el programa,
		 * pero no acabará la partida
		 */
		while (!cartas.isEmpty()) {
			// Generamos un número aleatorio entre 0 y el tamaño de nuestro ArrayList de
			// cartas
			int carta_aleatoria_mazo = (int) (Math.random() * cartas.size());
			// Esta será la carta aleatoria de sus mano que el jugador lanzará
			Carta carta = cartas.get(carta_aleatoria_mazo);
			// Insertamos esa carta en la tabla partida
			dao.insertarCartaPartida(carta);
			/*
			 * En el caso de que salga un MASDOS, robamos dos cartas (insertamos dos cartas
			 * en la tabla carta. Eliminamos esa carta que hemos jugado de la mano
			 */
			if (carta.getNumero().equals(Numero.MASDOS)) {
				System.out.println("Has utilizado la " + carta);
				for (int i = 0; i < 2; i++) {
					Carta nuevaCarta = new Carta(0, id_jugador);
					dao.insertarCarta(nuevaCarta);
					System.out.println("Has robado la " + nuevaCarta);
					dao.eliminarCarta(carta);
				}
				cartas = dao.todasCartasPorJugador(id_jugador);
				/*
				 * En el caso de que salga un MASCUATRO, robamos cuatro cartas (insertamos
				 * cuatro cartas en la tabla carta. Eliminamos esa carta que hemos jugado de la
				 * mano (eliminamos de la tabla carta la carta que hemos jugado)
				 */
			} else if (carta.getNumero().equals(Numero.MASCUATRO)) {
				System.out.println("Has utilizado la " + carta);
				for (int i = 0; i < 4; i++) {
					Carta nuevaCarta = new Carta(0, id_jugador);
					dao.insertarCarta(nuevaCarta);
					System.out.println("Has robado la " + nuevaCarta);
					dao.eliminarCarta(carta);
					
				}
				cartas = dao.todasCartasPorJugador(id_jugador);
				/*
				 * En el caso de que salga SALTO O CAMBIO, acabamos el programa. Eleminamos esa
				 * carta de la tabla partida y carta
				 */
			} else if (carta.getNumero().equals(Numero.SALTO) || carta.getNumero().equals(Numero.CAMBIO)) {
				System.out.println("Has utilizado la " + carta);
				dao.eliminarCartaPartida(carta);
				dao.eliminarCarta(carta);
				cartas = dao.todasCartasPorJugador(id_jugador);
				break;
				/*
				 * En el caso de que no salga una de las opciones anteriores, es decir, el resto
				 * de cartas. Eliminamos esa carta de la mano, ya que es la carta que hemos
				 * jugado, pero no ocurre nada adicional
				 */
			} else {
				System.out.println("Has utilizado la " + carta);
				dao.eliminarCarta(carta);
				cartas = dao.todasCartasPorJugador(id_jugador);
			}

			ArrayList<Carta> cartas1 = dao.todasCartasPorJugador(id_jugador);

			// En el caso de que el jugador tenga en la mano mas de 25 cartas, habrá perdido
			// y acaba la paritda
			if (cartas1.size() > 25) {
				System.out.println("Has perdido, tienes demasiadas cartas en tu mano");
				// Eliminamos todas las cartas de la tabla para volver a empezar
				dao.eliminarTodasCartasJugador(id_jugador);
				break;
			}

		}
	}
}
