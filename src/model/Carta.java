package model;

public class Carta {
	private int id;
	private int id_jugador;
	// Guardamos todos los valores del enum en un array
	private Color[] colores = Color.values();
	private Numero[] numeros = Numero.values();
	private Numero numero;
	private Color color;

	public enum Color {
		ROJO, AMARILLO, VERDE, AZUL, NEGRO
	};

	public enum Numero {
		CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, CAMBIO, MASDOS, SALTO, CAMBIOCOL, MASCUATRO
	}

	/*
	 * Cada vez que instanciemos una carta, el numero y el color será aleatorio y
	 * corresponderá a uno de los valores del enum que hemos guardado en su
	 * respectivo array
	 */
	public Carta(int id, int id_jugador) {
		this.id = id;
		this.id_jugador = id_jugador;
		numero = numeros[(int) (Math.random() * numeros.length)];
		color = colores[(int) (Math.random() * colores.length)];
	}

	public Carta(int id, int id_jugador, Numero numero, Color color) {
		this.id = id;
		this.id_jugador = id_jugador;
		this.numero = numero;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_jugador() {
		return id_jugador;
	}

	public void setId_jugador(int id_jugador) {
		this.id_jugador = id_jugador;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Numero getNumero() {
		return numero;
	}

	public void setNumero(Numero numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Carta [id=" + id + ", id_jugador=" + id_jugador + ", color=" + color + ", numero=" + numero + "]";
	}

}
