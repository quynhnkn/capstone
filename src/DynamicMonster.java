
import com.googlecode.lanterna.terminal.Terminal;
import java.util.Random;

/**
 *
 * @author quynhngo
 * 
 * Dynamic Monters can move
 */
public class DynamicMonster extends GameObject {

    private final Random random = new Random();
    private final char symbol;
    private String position;
    private int x, y; // the coordinate of each monster. x and y are extracted
                        // from position 
    
    
    private final Terminal.Color color;

    public DynamicMonster(String position) {
        
        symbol = '\u046E';
        this.position = position;
        this.color = Terminal.Color.RED;
        
        
        // take x and y from position(key) in properties file
        String xx = "";
        String yy = "";
        for (int i = 0 ; i < position.indexOf(","); i++) 
            xx += position.charAt(i);
        
        for (int i = position.indexOf(",") + 1;
                i < position.length(); i++)
            yy += position.charAt(i);
        
        // parse x and y to int
        x = Integer.parseInt(xx);
        y = Integer.parseInt(yy);                
    }
    
    @Override
    public String getPosition() {
        return position;
    }
    
    @Override
    public Terminal.Color getColor() {
        return color;
    }
    
    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "" + symbol;
    }
    
    // implementing move method for dynamic monster
    public void move(Labyrinth labyrinth) {
        
        String xx = "";
        String yy = "";
        
        for (int i = 0 ; i < position.indexOf(","); i++) 
            xx += position.charAt(i);
        
        for (int i = position.indexOf(",") + 1;
                i < position.length(); i++)
            yy += position.charAt(i);
        
        int currentX = Integer.parseInt(xx);
        int currentY = Integer.parseInt(yy);
        
        if ((Play.Ox < x && x < Play.Ox + 74) && 
                (Play.Oy < y && y < Play.Oy + 24)) {            
            
            /*if (currentX < labyrinth.player.x) 
                moveCol(1, labyrinth); //right
                
                        
            if (currentX > labyrinth.player.x) {
                moveCol(-1, labyrinth); //left
            
            if (currentY < labyrinth.player.y)
                moveRow(1, labyrinth); //down
            
            if (currentY > labyrinth.player.y)
                moveRow(-1, labyrinth); //up
            
            if (currentX > labyrinth.player.x && currentY > labyrinth.player.y) {//left up
                moveCol(-1, labyrinth);
                moveRow(-1, labyrinth);
            }
            
            if (currentX < labyrinth.player.x && currentY > labyrinth.player.y) { // right up
                moveCol(1, labyrinth);
                moveRow(-1, labyrinth);
            }
            
            if (currentY < labyrinth.player.y && currentX > labyrinth.player.x) { // down left
                moveRow(1, labyrinth);
                moveCol(-1, labyrinth);
            }
            
            if (currentY < labyrinth.player.y && currentX < labyrinth.player.x) { // down right
                moveRow(1, labyrinth);
                moveCol(1, labyrinth);
            }
                
            } else {*/  
            
            
            // move Monster randomly using switch case
            int randomMove = random.nextInt(4);
            switch (randomMove) {
                case 0:
                    moveCol(1, labyrinth); //right
                    break;
                case 1:
                    moveCol(-1, labyrinth); //left
                    break;
                case 2:
                    moveRow(1, labyrinth); //down
                    break;
                case 3:
                    moveRow(-1, labyrinth); // up

                default:    
            }
            
            
        }
    }


    
    // moveCol(...) allows Monster to move horizontally
    public void moveCol(int x, Labyrinth maze) {
                
        String xx = "";
        String yy = "";
        for (int i = 0 ; i < position.indexOf(","); i++) 
            xx += position.charAt(i);
        
        for (int i = position.indexOf(",") + 1;
                i < position.length(); i++)
            yy += position.charAt(i);
        
        int currentX = Integer.parseInt(xx);
        int currentY = Integer.parseInt(yy);
        
        if ( maze.getBoard()[currentX + x][currentY] == null ||
                maze.getBoard()[currentX + x][currentY] == maze.player) {

            if(maze.getBoard()[currentX + x][currentY] == maze.player) {
                maze.player.lostLife(); 
                if (maze.player.getLives() == 0) {
                    Main.game.gameOver();
                }
                maze.placePlayer(maze.player.getLives());
                
                
            }            
            this.position = Integer.toString(currentX + x) 
                    + "," + Integer.toString(currentY);

            maze.getBoard()[currentX][currentY] = null;
            maze.getBoard()[currentX + x][currentY] = this;            
            
            
            if ((currentX + x) % 75 < 75 && 
                currentY% 25 < 25) {
            
                maze.getTerminal().moveCursor(currentX%75, currentY%25);
                maze.getTerminal().putCharacter(' ');      

                maze.getTerminal().moveCursor((currentX + x)%75, currentY%25);
                maze.getTerminal().applyForegroundColor(Terminal.Color.RED);
                maze.getTerminal().putCharacter(symbol);            
            }
            
        } 
    }
    
    // moveRow(...) allows Monster to move vertically
    public void moveRow (int y, Labyrinth maze) {
        
        String xx = "";
        String yy = "";
        
        for (int i = 0 ; i < position.indexOf(","); i++) 
            xx += position.charAt(i);
        
        for (int i = position.indexOf(",") + 1;
                i < position.length(); i++)
            yy += position.charAt(i);
        
        int currentX = Integer.parseInt(xx);
        int currentY = Integer.parseInt(yy);
        
        
        
        if (maze.getBoard()[currentX][(currentY + y)] == null ||
                maze.getBoard()[currentX][currentY + y] == maze.player) {
            
            if(maze.getBoard()[currentX][currentY + y] == maze.player) {
                maze.player.lostLife(); 
                if (maze.player.getLives() == 0) {
                    Main.game.gameOver();
                }                
                maze.placePlayer(maze.player.getLives());
            }            
            
           
            this.position = Integer.toString(currentX) 
                     + "," + Integer.toString(currentY + y); 
            
            maze.getBoard()[currentX][currentY] = null;
            maze.getBoard()[currentX][currentY + y] = this;
            
            if ((currentX) % 75 < 75 && 
                    (currentY + y)% 25 < 25) {

                maze.getTerminal().moveCursor(currentX%75, currentY%25);
                maze.getTerminal().putCharacter(' ');

                maze.getTerminal().moveCursor(currentX%75, (currentY + y)%25);
                maze.getTerminal().applyForegroundColor(Terminal.Color.RED);
                maze.getTerminal().putCharacter(symbol);
            
            }      
        }        
    }
    


    

}
