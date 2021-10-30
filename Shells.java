import java.awt.*;
import java.lang.Math;

//-----------------------------------------
// Class: Shells 
//-----------------------------------------
public class Shells extends Canvas 
{
    private int matrixSize;
    private Color bgColor = Color.green; 
    private ShellsPlayer player;
    private ShellsPlayer computer; 
    private int maxGlossX; // tracks the x position of max gloss in matrix
    private int maxGlossY; // tracks the y position of max gloss in matrix
    private int tileSize;  // pixel size of one shell in matrix 
    private int recSize;   // pixel size of one square in matrix 
    private int [][] shellsMatrix ; //  matrix data structure for glow values
    // below are the character codes for keys that move the player
    //TODO: add the j key code 106
    private final int   PLAYER_UP    = 119 ;  //  the w key
    private final int   PLAYER_DOWN  = 122 ;  //  the z key
    private final int   PLAYER_LEFT  = 97 ;   //  the a key
    private final int   PLAYER_RIGHT = 115 ;  //  the s key

    //-----------------------------------------
    // Constructor: Shells 
    //-----------------------------------------
    public Shells(int msize, int shellsize, int recsize) 
    {
        super();
        matrixSize = msize;
        player    = new ShellsPlayer(0, msize-1, Color.ORANGE, msize, this);
        computer  = new ShellsPlayer(msize-1, 0, Color.BLUE, msize, this);
        maxGlossX=0;
        maxGlossY=0;
        tileSize=shellsize; 
        recSize=recsize; 
        shellsMatrix = new int [msize][msize];
    }
    
    //-----------------------------------------
    // Method: getRand
    // simple utility function : makes a random 
    // number between zero and max
    //-----------------------------------------
    private int getRand(int max)
    {
        return (int)(Math.random() * (max+1));
    }
    
    //-----------------------------------------
    // Method: FreshData
    // Generates the initial game data for the game board. 
    // The values in matrix should range from 0 to 4.
    // If the values exceed 4, we can have problems with
    // the current coloring formulas used in the DrawShells method.
    //-----------------------------------------
    public void FreshData()
    {
        //TODO: use the getRand method to insert random data 
        //TODO:   into the matrix here. 
        //TODO:   Max value is 4, min is zero 
        for (int x = 3; x < 6; x++) 
            for (int y = 0; y < matrixSize; y++) 
                shellsMatrix[x][y] = 2;

        for (int x = 8; x < 11; x++) 
            for (int y = 0; y < matrixSize; y++) 
                shellsMatrix[x][y] = 4;
     }
    
    //-----------------------------------------
    // Method: GetMaxGlossX
    //-----------------------------------------
    public int GetMaxGlossX()
    {
        return maxGlossX; 
    }
    
    //-----------------------------------------
    // Method: GetMaxGlossY
    //-----------------------------------------
    public int GetMaxGlossY()
    {
        return maxGlossY; 
    }
    
    //-----------------------------------------
    // Method: MovePlayer
    // Maps the key inputs to the player movements.
    // After processing the user selection, the computer Auto movement
    // engages to allow computer to take a turn.
    //-----------------------------------------
    public void MovePlayer(int key)
    {
        if (key == PLAYER_RIGHT) 
            player.MoveRight();

        if (key == PLAYER_LEFT) 
            player.MoveLeft();

        if (key == PLAYER_UP) 
           player.MoveUp();

        if (key == PLAYER_DOWN) 
            player.MoveDown();
        
        // now move the 2nd player - handles computer automated moves 
        computer.AutoMove(); 
    }
    
    //-----------------------------------------
    // Method: SetColor
    //-----------------------------------------
    public void SetColor(Color col)
    {
        bgColor = col;
    }

    //-----------------------------------------
    // Method: UpdateMaxValues
    // Scans the matrix to find maximum glow value    
    //-----------------------------------------
    private int  UpdateMaxValues()
    {
      maxGlossX=0; 
      maxGlossY=0; 
      int maxGlossFound=0; 
      for (int x = 0; x < matrixSize; x++) 
        {
            for (int y = 0; y < matrixSize; y++) 
            {
                int glow = shellsMatrix[x][y];
                if (glow > maxGlossFound)
                {
                    maxGlossFound=glow;
                    maxGlossX=x;
                    maxGlossY=y;
                }
            }
        }
        return maxGlossFound; 
     }

    //-----------------------------------------
    // Method: UpdateMaxValues
    // When the board is clear, displays the final scores of each player.
    //-----------------------------------------
    private void DisplayGameOver(Graphics g)
    {
        g.setFont(new Font("Arial",  Font.BOLD, 16));
        g.setColor(Color.WHITE);
        String playerScore = player.GetScore().toString();
        String computerScore = computer.GetScore().toString();
        g.drawString("YOUR SCORE: " + playerScore, 40,150);
        g.drawString("COMPUTER SCORE: " + computerScore, 40,200);
    }

    //-----------------------------------------
    // Method: DrawShells
    // This method is the main worker handling all details of 
    // rendering the matrix after the turn is processed. 
    // It has too much functionality and needs to be slimmer. 
    //-----------------------------------------
    public void DrawShells(Graphics g) 
    {
        // When the UpdateMaxValues method returns zero, it means
        // all elements of the matrix are zero and the game is over
        if (UpdateMaxValues()==0) 
        {
            DisplayGameOver(g);
        }

        g.setFont(new Font("Arial",  Font.BOLD, 14));
        int playerX = player.getX();
        int playerY = player.getY();
        int computerX = computer.getX();
        int computerY = computer.getY();
        boolean foundComputer;
        boolean foundPlayer; 

        // Scan the entire matrix to determine player positions
        // and render correct colors of each matrix element.
        for (int x = 0; x < this.matrixSize; x++) 
        {
            for (int y = 0; y < this.matrixSize; y++) 
            {
                foundComputer = false; 
                foundPlayer = false; 
                g.setColor(bgColor);
                Integer glow = shellsMatrix[x][y]; 
                // When matrix scan reaches x-y location of 
                // player, use player color to paint rectangle.
                if ((x == playerX) && (y == playerY))  
                {
                    g.setColor(player.GetColor());
                    foundPlayer=true; 
                }
                // When matrix scan reaches x-y location of 
                // computer, use computer color to paint rectangle.
                else if ((x == computerX) && (y == computerY))
                {
                    g.setColor(computer.GetColor());
                    foundComputer=true;
                }
                // this block uses the player color to fill in 
                // the element occupied by either the player or the 
                // computer opponent
                if (foundPlayer || foundComputer )
                {
                    g.fillOval(x*tileSize, y*tileSize, recSize, recSize);
                    g.setColor(Color.BLACK);
                    g.drawString(glow.toString(), x*tileSize+3, y*tileSize+13);
                }
                else if (glow>0)
                {
                    // NOTE: The glow values in matrix only range from 0 to 4. 
                    // Based on this range, the following scale factors 
                    // generate colors that look good.
                    int red   = glow*16;
                    int green = glow*62; 
                    int blue  = glow*16+50; 
                    // The lines below render the graphics for each element 
                    // of the matrix.
                    g.setColor(new Color(red,green,blue));
                    g.fillOval(x*tileSize, y*tileSize, recSize, recSize);
                    g.setColor(Color.BLACK);
                    g.drawString(glow.toString(), x*tileSize+3, y*tileSize+13);
                }
               
            }
        }

        // Below block renders the game status info at the bottom 
        // of the game board. It may not show up if the rendering window 
        // in Replit.com is too small - just expand the window in that case. 
        g.setColor(Color.ORANGE);
        g.drawLine(0, 400, 400, 400);
        g.drawLine(400, 0, 400, 400);
        g.drawString("you: "+player.GetScore().toString(), 10, 420);
        g.drawString("other: "+computer.GetScore().toString(), 320, 420); 

    }

    // Method: ClearGloss
    public void ClearGloss(int x, int y) 
    {
        shellsMatrix[x][y]=0;
    }
    
    //-----------------------------------------
    // Method: WaterWave
    // move all the values in the matrix one position to right. 
    // This will simulate a water wave hitting the shells and moving them :-) 
    //-----------------------------------------
    public void WaterWave() 
    {
       // NOTE: x is first index (column), y is second index (row) 
       // in the rendering of shellsMatrix[x][y]. 
      for (int x = matrixSize-1; x >= 0; x--) // must scan columns backwards
      {
            for (int y = 0; y < matrixSize; y++) 
            {   // Element at column zero is overwitten with zeros,
                // otherwise, value from column to left [x-1][y]
                // is copied to [x][y], this will shift  
                // values in matrix right one position 
                if (x==0)
                    shellsMatrix[x][y] = 0;
                else
                    shellsMatrix[x][y] = shellsMatrix[x-1][y];
            }
        }
    }

    //-----------------------------------------
    // Method: GetGloss
    // Returns the gloss value from the matrix position and 
    // gives a zero if the x-y values are located off of the valid
    // board area. 
    //-----------------------------------------
    public int GetGloss(int x, int y)
    {
        // checks to ensure coordinates are on the game board 
        if ( ( x >= 0 ) && ( x < matrixSize ) && 
             ( y >= 0 ) && ( y < matrixSize ) )
        {
            return shellsMatrix[x][y];
        }
        return 0; // returns zero if off-board
    }

    @Override
    public void paint(Graphics g) 
    {
        DrawShells(g);
    }

}