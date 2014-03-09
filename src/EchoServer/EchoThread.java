package EchoServer;


import java.net.Socket;
import java.io.*;

public class EchoThread implements Runnable
{
	private Socket client = null;
	private String clientIp = null;
        private boolean mas = false;

	public EchoThread(Socket client)
	{
		this.client = client;
		clientIp = this.client.getInetAddress().getHostAddress();
		System.out.println("[" + clientIp + "] " +
			">> Connessione in ingresso <<");
	}

	public void run()
	{
		BufferedReader reader = null;
		Writer writer = null;

		try
		{
			reader = new BufferedReader(
				new InputStreamReader(this.client.getInputStream()));
			writer = new OutputStreamWriter(this.client.getOutputStream());

			while(true)
			{
				String response = reader.readLine();

				if(response == null ||
				response.equalsIgnoreCase("fine")){
					break;
                        }
                                
                                if(response.equals("maiuscole on")){
                                    System.out.println("ON");
                                    mas=true;
                                }
					
                                if(response.equals("maiuscole off")){
                                    System.out.println("OFF");
					mas=false;
                                }
				String output = "[" + clientIp + "] " + response;
				System.out.println(output);
                                if(mas==false){
                                    writer.write(response + "\n");
                                }else{
                                    writer.write(response.toUpperCase() + "\n");
                                }
                                
				writer.flush();
			}
		}
		catch(IOException ex)
		{
			// possibile disconnessione del nodo
			// ignoro
		}
		finally
		{
			// Clean
			try
			{
				reader.close();
				writer.close();
				this.client.close();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}		

			System.out.println("[" + clientIp + "] " +
				">> Connessione terminata <<");
		}
	}
}
