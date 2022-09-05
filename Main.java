package Caso1;

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
		
		cantDeNiveles = 3;
		cantDeProcesos = 3;
		bigBufferSize = 2;
		smallBufferSize = 1;
		cantMensajes = 45;
		
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
