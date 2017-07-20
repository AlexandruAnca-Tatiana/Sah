import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
public class ChessMain
{
    public static void main( String[] args )
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add( new ChessPanel() );
        frame.pack();
        frame.setVisible( true );
        try
		{
			Socket s = new Socket("127.0.0.1", 9999);
			BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter w = new PrintWriter(s.getOutputStream(), true);
			BufferedReader con = new BufferedReader(new InputStreamReader(System.in));
			String line;
			do
			{
				line = r.readLine();
				if ( line != null )
					System.out.println(line);
				line = con.readLine();
				w.println(line);
			}
			while ( !line.trim().equals("bye") );
		}
		catch (Exception err)
		{
			System.err.println(err);
		}
    }

}
