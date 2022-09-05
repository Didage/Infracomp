package Caso1;

import java.util.concurrent.ThreadLocalRandom;

public class StartNode extends Thread {
	
	/**
	 * Cantidad de mensajes
	 */
	private int n;
	
	/**
	 * Buffer de salida
	 */
	private Buffer deliveryBuffer;

	/**
	 * Constructor
	 * @param n
	 * @param deliveryBuffer
	 */
	public StartNode(int n, Buffer deliveryBuffer) {
		this.n = n;
		this.deliveryBuffer = deliveryBuffer;
	}
	
	@Override
	public void run() {
		int i = 0;
		while(i < n) {
			if(deliveryBuffer.activeLoad(new String("M"+i))) { 
				i++;
			}
		}
		System.out.println("Acabe de enviar");
		
		//Espera a que todo haya sido consumido para enviar los mensajes de terminacion
		while(!deliveryBuffer.estaVacio()) {
		}
		
		i = 0;
		while(i < 3) {
			if(deliveryBuffer.activeLoad(new String("FIN"))) { 
				i++;
				System.out.println("Acabe de enviar un FIN" + i);
			}
		}
		System.out.println("El start NODE termino");
	}
}
