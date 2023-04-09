import java.net.*;

import java.io.*;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
 
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");


 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
 
            String line = "";
            String str = "";
            
            Dictionary<String, String> dict = new Hashtable<>();

            
            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {

                try
                {
                    line = in.readUTF();

                    str = line;
                    //Dictionary<String, Integer> dict1 = new Hashtable<>();

                    HashMap<String, String> result = new HashMap<String, String>();

                    

                    //PUT
                    if(line.contains("PUT"))
                    {
                        str = str.replaceAll("PUT", "");

                        String[] parts = str.split("=");
                        if (parts.length == 2) {

                        dict.put(parts[0].trim(), parts[1].trim());
                        }

                    }

                    //GET
                    if(line.contains("GET"))
                    {
                        str = str.replaceAll("GET", "");
                        str = str.replaceAll("\\s", "");
                        str = dict.get(str);
                        System.out.println("Output: "+str);
                    }
                    
                    //DUMP
                    if(line.contains("DUMP"))
                    {
                    	Enumeration<String> enu = dict.keys();

                        System.out.println("Output: ");
                    	while (enu.hasMoreElements()) {
                            System.out.print(enu.nextElement());
                            System.out.print(" ");
                        }
                    	
                    }

 
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
 
            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}