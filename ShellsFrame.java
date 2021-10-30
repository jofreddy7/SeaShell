import java.awt.*;
import java.awt.event.*;

//-----------------------------------------
// Class: ShellsFrame - don't alter this class 
//-----------------------------------------
public class ShellsFrame extends Frame implements WindowListener, KeyListener 
{
    private Shells myShells;
    
    //-----------------------------------------
    // Constructor: ShellsFrame 
    //-----------------------------------------
    public ShellsFrame(Shells sh, String title) 
    {
        super(title);
        myShells = sh; 
        this.addWindowListener(this);
        this.addKeyListener(this);
    }

    @Override 
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        // hitting space key (with focus on canvas) will exit program
        if (key == KeyEvent.VK_SPACE) 
        {
            dispose();
            System.exit(0);
        }
    }
    
    @Override
    public void windowClosing(WindowEvent e) 
    {
        // closing window will exit program
        dispose();
        System.exit(0);
    }   
    
    @Override
    public void keyReleased(KeyEvent e)       { /* not in use */  }
    @Override
    public void keyTyped(KeyEvent e)           { /* not in use */  }
    @Override
    public void windowActivated(WindowEvent e)  { /* not in use */  }
    @Override
    public void windowClosed(WindowEvent e)     { /* not in use */  }
    @Override 
    public void windowIconified(WindowEvent e)   { /* not in use */  }
    @Override
    public void windowDeiconified(WindowEvent e)  { /* not in use */  }
    @Override
    public void windowDeactivated(WindowEvent e)  { /* not in use */  }
    @Override
    public void windowOpened(WindowEvent e)       { /* not in use */  }

}