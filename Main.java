package Caso1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	/**
	 * Cantidad de Niveles
	 */
	private static int cantDeNiveles;
	
	/**
	 * Cantidad de procesos por nivel
	 */
	private static int cantDeProcesos;
	
	/**
	 * Cantidad de mensajes a enviar
	 */
	private static int cantMensajes;
	
	/**
	 * Tamanho para los buffer grandes
	 */
	private static int bigBufferSize;
	
	/**
	 * Tamanho para los buffer pequenhos
	 */
	private static int smallBufferSize;
	
	public static void main(String[] args) {
		
		//Lectura de los datos
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String entrada = "";
	 
		System.out.println("Ingrese el número de subconjuntos N: ");
		
        try {
			entrada = reader.readLine();
			cantMensajes = Integer.parseInt(entrada);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Ingrese el tamaño de los buzones grandes: ");
		
        try {
			entrada = reader.readLine();
			bigBufferSize = Integer.parseInt(entrada);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Ingrese el tamaño de los buzones chiquitos: ");
		
        try {
			entrada = reader.readLine();
			smallBufferSize = Integer.parseInt(entrada);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cantDeNiveles = 3;
		cantDeProcesos = 3;
		
		Buffer startBuffer = new Buffer(bigBufferSize, "Start Buffer");
		StartNode startNode = new StartNode(cantMensajes, startBuffer);
		System.out.println("Se cre[o el start Node");
		
		Buffer endBuffer = new Buffer(bigBufferSize, "End Buffer");
		EndNode endNode = new EndNode(cantMensajes, endBuffer);
		System.out.println("Se cre[o el end Node");
		
		ArrayList<InnerNode> nodos = new ArrayList<InnerNode>();
		
		for(int i = 0; i < cantDeNiveles; i++) {
			Buffer anterior = startBuffer;
			for(int j = 0; j < cantDeProcesos; j++) {
				if(j == cantDeProcesos-1) {
					nodos.add(new InnerNode(anterior, endBuffer, i, j));
				}  else {				
					Buffer siguiente = new Buffer(smallBufferSize, "buffer id: " + i + " " + j);
					nodos.add(new InnerNode(anterior, siguiente, i, j));
					anterior = siguiente;
				}				
			}
		}
		for(int i = 0; i < nodos.size(); i++) {
			System.out.println("nodo" + nodos.get(i).getLevelId() + "-" + nodos.get(i).getInnerProcessId() + " Entry Buff: " + nodos.get(i).getEntryBuffer().getId());
			System.out.println("nodo" + nodos.get(i).getLevelId() + "-" + nodos.get(i).getInnerProcessId() + " Delivery Buff: " + nodos.get(i).getDeliveryBuffer().getId() + "\n");
			nodos.get(i).start();
		}
		endNode.start();
		System.out.println("Voy a iniciar el start node");
		startNode.start();
		
	}

}
