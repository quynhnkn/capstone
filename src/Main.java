
import com.googlecode.lanterna.TerminalFacade;
import java.util.InvalidPropertiesFormatException;
import com.googlecode.lanterna.gui.GUIScreen;

/**
 *
 *  @author quynhngo
 * 
 * main method, from here the game runs
 */
public class Main {

    static Play game = new Play();
    static GUIScreen menuGUI;

    public static void gameStart() throws InvalidPropertiesFormatException {
        
        menuGUI = TerminalFacade.createGUIScreen();

        menuGUI.getScreen().startScreen();
        
        menuGUI.showWindow(new StartMenu(game), GUIScreen.Position.CENTER);
        
    }

    public static void main(String[] args) throws InvalidPropertiesFormatException {        
        gameStart();        
    }
    
}
