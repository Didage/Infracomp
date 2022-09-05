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
		while(true) {
			payload = entryBuffer.unload();
			payload = payload + "T" + levelId + innerProcessId;
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deliveryBuffer.load(payload);
		}
	}

	public Buffer getEntryBuffer() {
		return entryBuffer;
	}

	public Buffer getDeliveryBuffer() {
		return deliveryBuffer;
	}

	public int getLevelId() {
		return levelId;
	}

	public int getInnerProcessId() {
		return innerProcessId;
	}
	
}
