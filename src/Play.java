
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.InvalidPropertiesFormatException;
import com.googlecode.lanterna.gui.GUIScreen;

/**
 *
 *  @author quynhngo
 */
public class Play {

                   // Use Ox, Oy to change screen     
    static int Ox; // Ox and Oy will be assigned 
    static int Oy; // to another values, depends on the movement of Player
                    // Ox can equal 0 or 75
                    // Oy can equal 0 or 25 or 50, because the maximum size of level difficult is 150x75     
    
    static int livesLeft = 3;
        
    
    Labyrinth labyrinth;
    
    private boolean isRunning;
    
    

    public Play() {
        isRunning = true;
    }

    void start()
            throws InvalidPropertiesFormatException, InterruptedException {
        
        if (Main.menuGUI != null) {
            Main.menuGUI.getScreen().stopScreen();
        }
        isRunning = true;
        userInput();
    }

    private void userInput() 
            throws InvalidPropertiesFormatException, InterruptedException {
        
        
        long x = 0;
        
        while (true) {
            
            // check and change screen to position of player
            changeScreen();
            
            // show Lives and Collected Treasure
            playerInfo();
            
            
            navigatePlayer();

            
            // slow down the monster
            Thread.sleep(70);
            
            x++;      
            
            if (x % 5 == 0) {
                for (int i = 0; i < labyrinth.getDynaM().size(); i++) {
                    DynamicMonster monster = (DynamicMonster) labyrinth.getDynaM().get(i);
                    monster.move(labyrinth);
                }
            }
            
            // remove heart everytime player dies
            labyrinth.getTerminal().moveCursor(0, 25);
            
            for (int i = 0; i < 4; i++) {
                labyrinth.getTerminal().applyForegroundColor(Terminal.Color.BLACK);
                labyrinth.getTerminal().putCharacter(' ');
            }
            
        }


    }

 
    // this method serves the purpose of scrolling
    private void changeScreen() {
        
        // check if the player figure doesnt stays on the display screen
        
        if (labyrinth.getWidth() > 75 || labyrinth.getHeight() > 25) {
            
            while (labyrinth.player.x < Ox || 
                    labyrinth.player.x > Ox + 74 ||
                    labyrinth.player.y < Oy || 
                    labyrinth.player.y > Oy + 24) {
                
                if (labyrinth.player.x > Ox + 74) {
                    Ox += 75;
                }
                if (labyrinth.player.x < Ox) {
                    Ox = 0;                   
                }

                
                    
                
                if (labyrinth.player.y > Oy + 24) {
                    Oy += 25;
                }
                
                if (labyrinth.player.y < Oy) {
                    if(Oy == 50)
                        Oy = 25;                    
                    else
                        Oy = 0;
                }
                
                
                // load terminal
                for (int i = 0; i < 25; i++) {
                    for (int j = 0; j < 75; j++) {
                        
                        if (Ox + j< labyrinth.getWidth() && Oy + i< labyrinth.getHeight()) {
                            
                            labyrinth.getTerminal().moveCursor(j, i);
                            
                            if (labyrinth.getBoard()[j + Ox][i + Oy] != null) {
                                
                                labyrinth.getTerminal().applyForegroundColor(labyrinth.getBoard()[j + Ox][i + Oy].getColor());
                                labyrinth.getTerminal().putCharacter(labyrinth.getBoard()[j + Ox][i + Oy].getSymbol());
                                
                            } else {
                                labyrinth.getTerminal().putCharacter(' ');
                            }
                        } else {
                            labyrinth.getTerminal().putCharacter(' ');
                        }
                    }
                }

            }
        }            
    }
        
    // shows what happens when player meets the monster or collects the treasures
    private void interact(int mX, int mY) throws InvalidPropertiesFormatException, InterruptedException {
        
        if (labyrinth.getBoard()[labyrinth.player.x + mX][labyrinth.player.y + mY] != null) {
            
            if (labyrinth.getBoard()[labyrinth.player.x + mX][labyrinth.player.y + mY] 
                    instanceof Treasure) {
                
                labyrinth.player.setCollectedTreasure(1);
                
                labyrinth.player.move(mX, mY, labyrinth);                

            }            
            
            if (labyrinth.getBoard()[labyrinth.player.x + mX][labyrinth.player.y + mY] 
                    instanceof DynamicMonster ||
                labyrinth.getBoard()[labyrinth.player.x + mX][labyrinth.player.y + mY] 
                    instanceof StaticMonster) {
                
                labyrinth.player.lostLife();
                
                labyrinth.getTerminal().moveCursor(labyrinth.player.x % 75, labyrinth.player.y % 25);
                labyrinth.getTerminal().putCharacter(' ');
                labyrinth.getBoard()[labyrinth.player.x][labyrinth.player.y] = null;
                
                labyrinth.placePlayer(livesLeft);
                
                if (labyrinth.player.getLives() == 0) 
                    gameOver();                                                                         
                return;
            }

            

            if (labyrinth.getBoard()[labyrinth.player.x + mX][labyrinth.player.y + mY] 
                    instanceof Exit) {
                
                if (labyrinth.player.getCollectedTreasure() 
                        + Labyrinth.collectedTreasure == 5){
                    gameWin();
                }
            }
            
        } else {            
            labyrinth.player.move(mX, mY, labyrinth);
        }

    }
    
    
    private void playerInfo() {
        
        labyrinth.getTerminal().moveCursor(0, 25);        
        
        for (int i = 0; i < labyrinth.player.getLives(); i++) {
            labyrinth.getTerminal().applyForegroundColor(Terminal.Color.MAGENTA);
            labyrinth.getTerminal().putCharacter('\u2665');
        }
          
        
            
        labyrinth.getTerminal().moveCursor(0, 26);


        for (int i = 0; i < Labyrinth.collectedTreasure; i++) {
            labyrinth.getTerminal().applyForegroundColor(Terminal.Color.YELLOW);
            labyrinth.getTerminal().putCharacter('$');
        }


        for (int i = 0; i < labyrinth.player.getCollectedTreasure(); i++) {
            labyrinth.getTerminal().applyForegroundColor(Terminal.Color.YELLOW);
            labyrinth.getTerminal().putCharacter('$');
        }
        
            
    }
    
    public void gameOver() {        
        isRunning = false;
        Main.menuGUI = TerminalFacade.createGUIScreen();
        Main.menuGUI.getScreen().startScreen();
        Main.menuGUI.showWindow(new GameOver(), GUIScreen.Position.CENTER);        
    }
    
    private void gameWin() {
        isRunning = false;
        Main.menuGUI = TerminalFacade.createGUIScreen();
        Main.menuGUI.getScreen().startScreen();
        Main.menuGUI.showWindow(new Victory(), GUIScreen.Position.CENTER);
        
    }
    
    private void navigatePlayer() throws InvalidPropertiesFormatException, InterruptedException {
        
        Key key = labyrinth.getTerminal().readInput();
        
        if (key != null) {
            if (labyrinth.player.y < labyrinth.getBoard()[0].length - 1) {
                if (key.getKind() == Key.Kind.ArrowDown) 
                    interact(0, 1);
                
            }
            if (labyrinth.player.y != 0) {
                if (key.getKind() == Key.Kind.ArrowUp) 
                    interact(0, -1);
                
            }
            if (labyrinth.player.x != 0) {
                if (key.getKind() == Key.Kind.ArrowLeft) 
                    interact(-1, 0);
                
            }
            if (labyrinth.player.x != labyrinth.getBoard().length - 1) {
                if (key.getKind() == Key.Kind.ArrowRight) 
                    interact(1, 0);
                
            }
            
            // when pressing escape, show game menu
            if (key.getKind() == Key.Kind.Escape) {                
                Main.menuGUI = TerminalFacade.createGUIScreen();
                Main.menuGUI.getScreen().startScreen();
                Main.menuGUI.showWindow(new GameMenu(), GUIScreen.Position.CENTER);
            }
        }        
    }
    

}

