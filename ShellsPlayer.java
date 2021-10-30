import java.awt.*;

//-----------------------------------------
// Class: ShellsPlayer 
//-----------------------------------------
public class ShellsPlayer 
{
    private Color myColor;   // current player color 
    private int x;            // current player x position
    private int y;            // current player y position
    private int maxIndex;     // range max used for validation 
    private Integer score;    // current player running score 
    private Shells myShells;   // a reference to the primary Shell object for the game
    private int runningCount; // count of number of player moves so far

    // Constants below needed only for automatic player mode
    private final int UP_MOVE = 0;
    private final int DOWN_MOVE = 1;
    private final int LEFT_MOVE = 2;
    private final int RIGHT_MOVE = 3;

    // number of turns before next water wave, its arbitrary, change if you want. 
    private final int WAVE_INTERVAL = 11; 

    //-----------------------------------------
    // Constructor: ShellsPlayer 
    //-----------------------------------------
    public ShellsPlayer(int x, int y, Color col, int m, Shells t)
    {
        myColor = col; 
        this.x = x;
        this.y = y; 
        maxIndex = m-1; 
        score=0;
        myShells = t; 
        runningCount=0; 
    }
    
    //-----------------------------------------
    // Method: getX
    //-----------------------------------------
    public int getX()
    {
        return x;
    }
    //-----------------------------------------
    // Method: getY
    //-----------------------------------------
    public int getY()
    {
        return y; 
    }

    //-----------------------------------------
    // Method: setX
    // TODO: add setX here to support jump command
    //-----------------------------------------
    
    //-----------------------------------------
    // Method: setY
    // TODO: add setY here to support jump command
    //-----------------------------------------

    //-----------------------------------------
    // Method: MoveRight
    //-----------------------------------------
    public void MoveRight()
    {
        if ( x < maxIndex ) x++;
        runningCount++ ;
        score += myShells.GetGloss(x,y);
        myShells.ClearGloss(x,y);
        if ((runningCount % WAVE_INTERVAL) == 0)  
            myShells.WaterWave();
    }

    //-----------------------------------------
    // Method: MoveLeft
    //-----------------------------------------
    public void MoveLeft()
    {
        if (x > 0) x--;
        runningCount++ ;
        score += myShells.GetGloss(x,y);
        myShells.ClearGloss(x,y);
        if ((runningCount % WAVE_INTERVAL) == 0)  
            myShells.WaterWave();

    }

    //-----------------------------------------
    // Method: MoveUp
    //-----------------------------------------        
    public void MoveUp()
    {
        if (y > 0) y--;
        runningCount++ ;
        score += myShells.GetGloss(x,y);
        myShells.ClearGloss(x,y);
        if ((runningCount % WAVE_INTERVAL) == 0)  
            myShells.WaterWave();
    }

    //-----------------------------------------
    // Method: MoveDown
    //-----------------------------------------            
    public void MoveDown()
    {
        if (y < maxIndex) y++;
        runningCount++ ;
        score += myShells.GetGloss(x,y);
        myShells.ClearGloss(x,y);
        if ((runningCount % WAVE_INTERVAL) == 0)  
            myShells.WaterWave();
    }

    //-----------------------------------------
    // Method: AutoMove
    // This is only used by the computer player. The algorithm 
    // is to try looking at nearby glow values in matrix up to 
    // four elements away. If none are availble, then start
    // movement towards the maximum glow value in the matrix. 
    //-----------------------------------------
    public void AutoMove() 
    {
        int m = GetChoice(x,y,1);  // try neighbor cells
        if (m == -1)  
            m = GetChoice(x,y,2);  // try 2 cells out
        if (m == -1)  
            m = GetChoice(x,y,3);  // try 3 cells out
        if (m == -1)  
            m = GetChoice(x,y,4);  // try 4 cells out

        // The m is -1 indicates auto player is in a blank area.
        // In this case, movement is made towards maximum
        // glow value currently seen in the matrix.
        if (m == -1)  
            m = GetChoiceMaxGloss(x,y);  
        switch (m)  {
            case UP_MOVE:   MoveUp();
                break;
            case DOWN_MOVE: MoveDown();
                break;
            case LEFT_MOVE: MoveLeft();
                break;
            case RIGHT_MOVE: MoveRight();
                break;
        }
    }

    //-----------------------------------------
    // Method: GetColor
    //-----------------------------------------   
    public Color GetColor() 
    { 
        return myColor; 
    }

     //-----------------------------------------
    // Method: GetScore
    //-----------------------------------------   
    public Integer GetScore()
    {
        return score;
    }

    //-----------------------------------------
    // Method: GetChoice
    // For a player that is using Auto movement, 
    // find choice for movement is by scanning nearby gloss values
    //-----------------------------------------
    private int GetChoice(int x, int y, int i)
    {
        int choice=-1; // the -1 indicates no good local choice 
        int [] dirSum = {0,0,0,0};
        dirSum[0]= myShells.GetGloss(x,y-i) ;
        dirSum[1]= myShells.GetGloss(x,y+i) ;
        dirSum[2]= myShells.GetGloss(x-i,y) ;
        dirSum[3]= myShells.GetGloss(x+i,y) ;
        int  myMax = 0;
        for (int m=0; m<4; m++)
        {
            if (dirSum[m] > myMax)
            {
                myMax = dirSum[m];
                choice = m;
            }
        }
        return choice;
     }

    //-----------------------------------------
    // Method: GetChoiceMaxGloss
    // For a player that is using Auto movement, 
    // find choice for movement is by largest gloss value
    // currently in the matrix 
    //-----------------------------------------
    private int GetChoiceMaxGloss(int x, int y)
    {
        if (x < myShells.GetMaxGlossX())
           return RIGHT_MOVE;
        if (x > myShells.GetMaxGlossX())
           return LEFT_MOVE;
        if (y < myShells.GetMaxGlossY())
           return DOWN_MOVE;
        return UP_MOVE;
     }


}