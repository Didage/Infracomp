package Caso1;

import java.util.concurrent.ThreadLocalRandom;

public class InnerNode extends Thread {
	
	/**
	 * Identificador del nivel del nodo
	 */
	private int levelId;
	
	/**
	 * Identificador del proceso
	 */
	private int innerProcessId;

	/**
	 * Mensaje a procesar
	 */
	private String payload;
	
	/**
	 * Buffer de entrada
	 */
	private Buffer entryBuffer;
	
	/**
	 * Buffer de salida
	 */
	private Buffer deliveryBuffer;

	/**
	 * Constructor
	 * @param levelId
	 * @param innerProcessId
	 */
	public InnerNode(Buffer entryBuffer, Buffer deliveryBuffer, int levelId, int innerProcessId) {
		this.levelId = levelId;
		this.innerProcessId = innerProcessId;
		this.entryBuffer = entryBuffer;
		this.deliveryBuffer = deliveryBuffer;
		this.payload = null;
	}
	
	@Override
	public void run() {
		boolean end = false;
		while(!end) {
			payload = entryBuffer.unload();
			if(!payload.equals("FIN")) {
				payload = payload + "T" + levelId + innerProcessId;
				try {
					Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				deliveryBuffer.load(payload);
			} else {
				end = true;
				while(!deliveryBuffer.isEmpty()) {
				}
				deliveryBuffer.load(payload);
			}
		}
//		System.out.println("El proceso " + levelId + innerProcessId + " terminó.");
	}

	/**
	 * metodo de apoyo
	 * @return
	 */
	public Buffer getEntryBuffer() {
		return entryBuffer;
	}

	/**
	 * metodo de apoyo
	 * @return
	 */
	public Buffer getDeliveryBuffer() {
		return deliveryBuffer;
	}

	/**
	 * metodo de apoyo
	 * @return
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * metodo de apoyo
	 * @return
	 */
	public int getInnerProcessId() {
		return innerProcessId;
	}
	
}
