package model;

public class Carta {
	private int id;
	private int id_jugador;
	private Color[] colores = Color.values();
	private Numero[] numeros = Numero.values();
	private Color color = colores[(int) (Math.random()*colores.length-1)];
	private Numero numero = numeros[(int) (Math.random()*numeros.length-1)];

	public enum Color {
		ROJO, AMARILLO, VERDE, AZUL, NEGRO
	};

	public enum Numero {
		CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, CAMBIO, MASDOS, SALTO, CAMBIOCOLOR, MASCUATRO
	}

	public Carta() {
		
	}
	
	

	public Carta(int id, int id_jugador, Color color, Numero numero) {
		this.id = id;
		this.id_jugador = id_jugador;
		this.color = color;
		this.numero = numero;
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
	};

	
}