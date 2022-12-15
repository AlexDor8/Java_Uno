package model;

public class Partida {
	private int id;
	private int id_carta;
	
	public Partida() {
		
	}

	public Partida(int id, int id_carta) {
		this.id = id;
		this.id_carta = id_carta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_carta() {
		return id_carta;
	}

	public void setId_carta(int id_carta) {
		this.id_carta = id_carta;
	}
}
