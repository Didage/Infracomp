package Caso1;

public class EndNode extends Thread {

	/**
	 * Mensaje recibido final
	 */
	private String message;
	
	/**
	 * Cantidad de mensajes a recibir
	 */
	private int n;
	
	/**
	 * Buffer de entrada
	 */
	private Buffer entryBuffer;
	
	/**
	 * Constructor
	 * @param message
	 * @param n
	 * @param entryBuffer
	 */
	public EndNode(int n, Buffer entryBuffer) {
		this.message = "";
		this.n = n;
		this.entryBuffer = entryBuffer;
	}



	@Override
	public void run() {
		while(n!=0) {
				String downloadedMessage = entryBuffer.activeUnload();
				if(downloadedMessage.equals("FIN")) {
					System.out.println("me llego un FIN");
				} else if(!downloadedMessage.equals("")) {
					message = message + downloadedMessage + "\n";
					n--;
					System.out.println("Baje algo: "+ message);
				}			
		}
		System.out.println("El mensaje es: " + message);
	}
}
