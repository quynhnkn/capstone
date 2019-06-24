
/**
 *
 *  @author quynhngo
 * 
 * the menu runs before the game, shows which level user wants to play.
 */
import com.googlecode.lanterna.gui.*;
import com.googlecode.lanterna.gui.component.Button;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class StartMenu extends Window {

    public StartMenu(Play game) throws InvalidPropertiesFormatException {
        super("Start menu");                                                     
                                                                                
        addComponent(new Button("New game (easy, Grid 25x75 )", new Action() {

          @Override
          public void doAction() {
            try {
              GenerateEasy.createLevel();
              game.labyrinth = new Labyrinth("easy.properties");
              game.start();
            } catch (InvalidPropertiesFormatException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }));
        
        addComponent(new Button("New game (normal, Grid 50x100)", new Action() {

          @Override
          public void doAction() {
            try {
              GenerateNormal.createLevel();
              game.labyrinth = new Labyrinth("normal.properties");
              game.start();
            } catch (InvalidPropertiesFormatException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }));
        
        addComponent(new Button("New game (hard, Grid 75x150)", new Action() {

          @Override
          public void doAction() {
            try {
              GenerateHard.createLevel();
              game.labyrinth = new Labyrinth("hard.properties");
              game.start();
            } catch (InvalidPropertiesFormatException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }));
               
  
        addComponent(new Button("Exit", new Action() {

          @Override
          public void doAction() {
            System.exit(0);
          }
        }));
    }

}
