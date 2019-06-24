
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author quynhngo
 */
public class Player extends GameObject {

    private int lives;
    
    private boolean treasure;
    private final char symbol;
    private String position;
    private int collectedTreasure;
    
    int x;
    int y;
    
    
    private final Terminal.Color color;

    
    public Player(String position, int lives) {
        symbol = '\u263A';
        this.position = position;
        
        String xx = "";
        String yy = "";
        for (int i = 0 ; i < position.indexOf(","); i++) 
            xx += position.charAt(i);
        
        for (int i = position.indexOf(",") + 1;
                i < position.length(); i++)
            yy += position.charAt(i);         
        
        x = Integer.parseInt(xx);
        y = Integer.parseInt(yy);
        
        this.lives = lives;
        treasure = false;
        
        color = Terminal.Color.GREEN;
    }

    @Override
    public Terminal.Color getColor() {
        return color;
    }    

    public int lostLife() {
        if (lives > 0) {            
            return lives--;
        } else {
            return 0;
        }
    }

    public int getLives() {
        return lives;
    }
    
    public int getCollectedTreasure() {
        return collectedTreasure;
    }
    
    public void setCollectedTreasure(int x){
        this.collectedTreasure += x;
    }
    
    
    public boolean hasTreasure() {
        return treasure;
    }

    public void gotTreasure() {
        treasure = true;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public String getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return Character.toString(symbol);
    }
    
    
    // put the character player to new position, erase old position
    public void move(int mX, int mY, Labyrinth labyrinth) {
        
        labyrinth.getTerminal().moveCursor(labyrinth.player.x % 75, labyrinth.player.y % 25);
        labyrinth.getTerminal().putCharacter(' ');
        labyrinth.getBoard()[labyrinth.player.x][labyrinth.player.y] = null;
        
        String y = Integer.toString(labyrinth.player.y + mY);
        String x = Integer.toString(labyrinth.player.x + mX);
        labyrinth.getBoard()[labyrinth.player.x][labyrinth.player.y] = null;
        

        labyrinth.player.x += mX;
        labyrinth.player.y += mY;
        
        
        labyrinth.getBoard()[labyrinth.player.x][labyrinth.player.y] = labyrinth.player;
		
        if (labyrinth.player.x % 75 < 75 && labyrinth.player.y % 25 < 25) {
            labyrinth.getTerminal().applyForegroundColor(Terminal.Color.GREEN);
            labyrinth.getTerminal().moveCursor(labyrinth.player.x % 75, labyrinth.player.y % 25);
            labyrinth.getTerminal().putCharacter('\u263A');
        }
    }    
}
