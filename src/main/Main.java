package main;

import java.sql.SQLException;
import java.util.Scanner;

import service.Service;

public class Main {

	public static void main(String[] args) {
		inicio();
	}
	
	public static void inicio() {
		Service service = new Service();
		Scanner entrada = new Scanner(System.in);
		System.out.println("UNO");
		System.out.println("Introduce el numero que corresponda con la accion que quieres realizar:");
		System.out.println("1. Login");
		System.out.println("2. Registro");
		int accion = entrada.nextInt();
		switch(accion) {
		case 1:
			try {
				service.login();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				service.registrar();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
