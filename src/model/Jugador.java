package model;

public class Jugador {
	private int id;
	private String usuario;
	private String password;
	private String nombre;
	private int partidas;
	private int ganadas;
	
	
	public Jugador(int id, String usuario, String password, String nombre, int partidas, int ganadas) {
		this.id = id;
		this.usuario = usuario;
		this.password = password;
		this.nombre = nombre;
		this.partidas = partidas;
		this.ganadas = ganadas;
	}


	public Jugador() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getPartidas() {
		return partidas;
	}


	public void setPartidas(int partidas) {
		this.partidas = partidas;
	}


	public int getGanadas() {
		return ganadas;
	}


	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}


	@Override
	public String toString() {
		return "Jugador [id=" + id + ", usuario=" + usuario + ", password=" + password + ", nombre=" + nombre
				+ ", partidas=" + partidas + ", ganadas=" + ganadas + "]";
	}
	
	
}
