
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 *  @author quynhngo
 */
public class Labyrinth {

    public static Terminal terminal; // lanterna terminal, show the game
    
    private int width;  // width
    private int height; // and height of board
    
    
    
    
    private GameObject[][] board; // contains all game objects
    
    private static Entrance entrance; 
    
    private ArrayList<DynamicMonster> dynaM = new ArrayList<>(); // list contains all Monster
    
    
    public Player player;

    static int collectedTreasure; // serves the purpose of saving game
    
    
    private Properties pros;
    
    private Enumeration<?> e; // reads the properties files
    
    
    //getter methods
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public GameObject[][] getBoard() {
        return board;
    }



    public ArrayList getDynaM() {
        return dynaM;
    }

    
    // read file property, fill the board with all objects
    public Labyrinth(String levelFile) throws FileNotFoundException, IOException {
        
        
        Main.menuGUI.getScreen().stopScreen();
        
        pros = new Properties();
        FileReader in  = new FileReader(levelFile);
        
        pros.load(in);
        
        int livesLeft = 3;
        
        width = Integer.parseInt(pros.getProperty("Width"));
        height = Integer.parseInt(pros.getProperty("Height"));
        
        board = new GameObject[width][height];
        
        // the terminal display has the size of 75x27
        terminal = TerminalFacade.createSwingTerminal(75, 27);
        
        e = pros.propertyNames();
        
        while (e.hasMoreElements()) {
            
            String key = (String) e.nextElement();
            String value = pros.getProperty(key);
            
            if(key.equals("Width")) 
                width = Integer.parseInt(pros.getProperty("Width"));                        
            else if (key.equals("Height")) 
                height = Integer.parseInt(pros.getProperty("Height"));
            else if (key.equals("Lives"))
                livesLeft = Integer.parseInt(pros.getProperty("Lives"));
            else if(key.equals("CollectedTreasure")) {
                collectedTreasure = Integer.parseInt((pros.getProperty("CollectedTreasure")));
                
                terminal.moveCursor(0, 26);
                for (int i = 0; i < collectedTreasure; i++) {
                    terminal.applyForegroundColor(Terminal.Color.YELLOW);
                    terminal.putCharacter('$');                    
                }
            }
            else {                         
                    
                        
                    String x = "", y = "";
                    for (int u = 0; u < key.indexOf(","); u++) 
                        x += key.charAt(u);
                    
                    for (int u = key.indexOf(",") + 1;
                            u < key.length(); u++) 
                        y += key.charAt(u);                        
                    
                    
                    int i = Integer.parseInt(x);
                    int j = Integer.parseInt(y);
                    
                    if(i < 75 && j < 25) 
                        terminal.moveCursor(i, j);
                    
                    
                    switch (value) {
                        
                        case "0":
                            board[i][j] = new Wall(i + "," + j);                            
                            terminal.applyForegroundColor(board[i][j].getColor());
                            terminal.putCharacter(board[i][j].getSymbol());
                            break;
                            
                        case "1":    
                            board[i][j] = new Entrance(i + "," + j);   
                            entrance = new Entrance(i + "," + j);
                            terminal.applyForegroundColor(board[i][j].getColor());
                            terminal.putCharacter(board[i][j].getSymbol());
                            
                            break;

                        case "2":  
                            board[i][j] = new Exit(i + "," + j);                            
                            terminal.applyForegroundColor(board[i][j].getColor());
                            terminal.putCharacter(board[i][j].getSymbol());
                            break;
                            
                        case "3":  
                            board[i][j] = new StaticMonster(i + "," + j); 
                            //statG.add((StaticMonster) board[j][i]);
                            terminal.applyForegroundColor(board[i][j].getColor());
                            terminal.putCharacter(board[i][j].getSymbol());
                            break; 
                            
                        case "4":  
                            board[i][j] = new DynamicMonster(i + "," + j);
                            dynaM.add((DynamicMonster) board[i][j]);                            
                            terminal.applyForegroundColor(board[i][j].getColor());
                            terminal.putCharacter(board[i][j].getSymbol());
                            break;                  
                            
                        case "5":  
                            board[i][j] = new Treasure(i + "," + j);                            
                            terminal.applyForegroundColor(board[i][j].getColor());
                            terminal.putCharacter(board[i][j].getSymbol());
                            break;                            
                            
                        case "6":  
                            player = new Player(i + "," + j, livesLeft);
                            board[i][j] = player;
                            player.x = i;
                            player.y = j;
                            terminal.applyForegroundColor(board[i][j].getColor());
                            terminal.putCharacter(board[i][j].getSymbol());                                                        
                            break;  
                        
                        default:
                            terminal.putCharacter(' ');
                            
                    }
                    
                }
        }

        // when game starts from the very beginning, livesLeft = 3
        if (player == null)
            placePlayer(livesLeft);
        
        terminal.enterPrivateMode();        
        terminal.setCursorVisible(false);
        
    }    



    public void placePlayer(int lifes) throws NumberFormatException {
        
        String position = entrance.getPosition();
        
        if (player != null) {
            
            if (player.hasTreasure()) {
                player.gotTreasure();    
                player = new Player(position, lifes);            
            }
            
        } else {
            player = new Player(position, lifes);
        }
        
        String xx = "";
        String yy = "";
        for (int i = 0 ; i < position.indexOf(","); i++) 
            xx += position.charAt(i);
        
        for (int i = position.indexOf(",") + 1;
                i < position.length(); i++)
            yy += position.charAt(i);        
        
        player.x = Integer.parseInt(xx);
        player.y = Integer.parseInt(yy);        
        
        board[player.x][player.y] = player;        
            
        terminal.moveCursor(player.x, player.y);
        terminal.applyForegroundColor(Terminal.Color.GREEN);
        terminal.putCharacter(player.getSymbol());
        
       

    }

}
