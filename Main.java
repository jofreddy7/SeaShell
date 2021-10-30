import java.awt.Color;
import java.io.IOException;

//-----------------------------------------
// Class: Main 
//-----------------------------------------
class Main {

    //-----------------------------------------
    // Method: main
    //  Starting entry point of the program
    //-----------------------------------------
   public static void main(String[] args) throws InterruptedException, IOException
   {
        final int SHELL_SIZE = 20;  // size of a shell in pixels for graphics 
        final int RECT_SIZE  = 17;  // size of a rectangle within matrix for graphics 
        final int MATRIX_SIZE = 20; // square matrix of elements 20x20
        final int FRAME_SIZE = 440; // size of the entire game board for graphics 
        
        Shells shells = new Shells(MATRIX_SIZE, SHELL_SIZE, RECT_SIZE);

        // this call creates a new dataset on the game board 
        shells.FreshData(); 

        // Below is canvas & frame standard setup code, 
        // Don't alter these lines 
        shells.setSize(FRAME_SIZE, FRAME_SIZE);
        shells.setBackground(Color.gray);
        ShellsFrame frame = new ShellsFrame(shells, "Shells");
        frame.add(shells);
        frame.setLocation(0, 0);
        frame.pack();
        frame.setVisible(true);

        // Game activity is triggered from user inputs. 
        // The 'stty raw' command below allows us to process single keystrokes. 
        // Don't alter these lines 
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
        int key=0;
        while (true) 
        {
          key = System.in.read();
          System.out.println(key);
          shells.MovePlayer(key);
          shells.repaint(); // indirectly calls myTiles.Draw()
        }

  }
}