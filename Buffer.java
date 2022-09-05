package Caso1;

public class Buffer {
	
	private String id;

	/**
	 * Arreglo con los objetos a procesar
	 */
	private String[] memory;
	
	/**
	 * Tamanho del buffer
	 */
	private int bufferSize;
	
	/**
	 * Cantidad de datos almacenados
	 */
	private int lastLoadedIndex;
	
	/**
	 * Constructor
	 * @param pBufferSize tamanho del buffer.
	 */
	public Buffer(int pBufferSize, String id) {
		bufferSize = pBufferSize;
		memory = new String[bufferSize];
		lastLoadedIndex = -1;
		this.id = id;
//		System.out.println(id);
	}
	
	/**
	 * Metodo que carga un mensaje al buffer
	 * @param payload - mensaje a cargar
	 */
	public synchronized void load(String payload) {
//		boolean meMimi = false;
		while(lastLoadedIndex+1 == bufferSize) {
//			System.out.println("El proceso " + payload + " se mimio");
			try {
				wait();
//				meMimi = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(lastLoadedIndex == -1) {
//			if(meMimi) { 
//				System.out.println("El proceso: " + payload + " se desperto");
//			}
			memory[0] = payload;
			System.out.println("El proceso " + payload + " se cargo");
		} else {
//			if(meMimi) { 
//				System.out.println("El proceso: " + payload + " se desperto");
//			}
			memory[lastLoadedIndex+1] = payload;
			System.out.println("El proceso " + payload + " se cargo");
		}
		lastLoadedIndex++;
		notifyAll();
	}
	
	/**
	 * Metodo que descarga un mensaje del buffer
	 */
	public synchronized String unload() {
		while(lastLoadedIndex == -1) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String ans = memory[lastLoadedIndex];
		memory[lastLoadedIndex] = null;
		lastLoadedIndex--;
		notifyAll();
		return ans;
	}

	/**
	 * Metodo de apoyo
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 *  Metodo que carga un mensaje al buffer activamente
	 * @param payload - mensaje a cargar
	 */
	public synchronized boolean activeLoad(String payload) {
		if(lastLoadedIndex == bufferSize-1) {
			return false;
		}
		if(lastLoadedIndex == -1) {
			memory[0] = payload;
			System.out.println("El proceso " + payload + " se cargo");
			
		} else {
			memory[lastLoadedIndex+1] = payload;
			System.out.println("El proceso " + payload + " se cargo");
		}
		lastLoadedIndex++;
		notifyAll();
		return true;
	}
	
	/**
	 * Metodo que descarga un mensaje del buffer activamente
	 */
	public synchronized String activeUnload() {
		if(lastLoadedIndex == -1) {
			return "";
		}
		String ans = memory[lastLoadedIndex];
		memory[lastLoadedIndex] = null;
		lastLoadedIndex--;
		notifyAll();
		return ans;
	}
	
	public synchronized boolean isEmpty() {
		return lastLoadedIndex == -1;
	}
}
