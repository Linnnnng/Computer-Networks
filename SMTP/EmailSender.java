/*
Name: Lingjung Guan; Computing ID: lg3bv
*/

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EmailSender
{
   static private String[] commandLines = { null, "HELO alice\r\n", "MAIL FROM:<lg3bv@virginia.edu>\r\n",
                                    "RCPT TO:<bradjc@virginia.edu>\r\n", "DATA\r\n",
                                    "SUBJECT: CS4457 Hello world!!\r\nLingjun Guan\r\n.\r\n",
                                    "QUIT\r\n"};
   static private String[] replyCodes = {"220", "250", "250", "250", "354", "250", "221"};

   public static void main(String[] args) throws IOException
   {
      // Establish a TCP connection with the mail server.
      Socket socket = new Socket("mail.virginia.edu", 25);

      // Create a BufferedReader to read a line at a time.
      InputStream is = socket.getInputStream();
      OutputStream os = socket.getOutputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      // Execute one command at a time
      for(int i=0;i<commandLines.length;++i){
         String command = commandLines[i];
         String replyCode = replyCodes[i];
         System.out.print(command);
         sendCommand(br, os, command, replyCode);
      }

      is.close();
      os.close();
   }

   /**
    * Send a command to the server and check if it's executed successfully
    * @param br a buffered reader to read the input stream of the socket
    * @param os the output stream of the socket
    * @param command type of command
    * @param replyCode expected reply code of the command
    */
   private static void sendCommand(BufferedReader br, OutputStream os, String command, String replyCode) {
      try {
         if(command!=null) {
            // Get a reference to the socket's output stream.
            os.write(command.getBytes(StandardCharsets.UTF_8));
         }

         String response = br.readLine();
         System.out.println(response);

         if (!response.startsWith(replyCode)) {
            throw new IOException(replyCode + "reply not received from server.");
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
