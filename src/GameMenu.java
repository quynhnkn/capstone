

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quynhngo 
 * 
 * Game menu loads whenever pressing Escape
 */
public class GameMenu extends Window { 

    public GameMenu() {
        super("Game menu");

        addComponent(new Button("Continue", new Action() {

          @Override
          public void doAction() {
            Main.menuGUI.getScreen().stopScreen();
            try {
              Main.game.start();
            } catch (InvalidPropertiesFormatException | InterruptedException ex) {
              Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }));
        
        addComponent(new Button("Save game 1", new Action() {

          @Override
          public void doAction() {
            writePropFile("gameSave1.properties");
          }
        }));
        
        addComponent(new Button("Save game 2", () -> {
            writePropFile("gameSave2.properties");
        }));
 
        
        // load the properties file which is written before
        addComponent(new Button("Load game 1", new Action() {

          @Override
          public void doAction() {
            try {
              Play game = new Play();
              game.labyrinth = new Labyrinth("gameSave1.properties");
              game.start();
            } catch (InvalidPropertiesFormatException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
              Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }));
        
        
        addComponent(new Button("Load game 2", new Action() {

          @Override
          public void doAction() {
            try {
              Play game = new Play();
              game.labyrinth = new Labyrinth("gameSave2.properties");
              game.start();
            } catch (InvalidPropertiesFormatException ex) {
              Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
              Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }));
        
        
        

        addComponent(new Button("Legends", new Action() {

          public void doAction() {
            Main.menuGUI.getActiveWindow().close();
            MessageBox.showMessageBox(getOwner(), 
                    "Legends", "X = wall, A = exit, \u046E = dynamic Monsters,"
                            + "\n E = entrance, $ = treasure, \u263A = pslayer,"
                            + " \u06DE = static Monsters \u2665 = lives left");
            Main.menuGUI.getScreen().stopScreen();
            try {
              Main.game.start();
            } catch (InvalidPropertiesFormatException | InterruptedException ex) {
              Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }));
        
        addComponent(new Button("Exit", () -> {
            System.exit(0);
        }));

    }
    
    
    /* here comes the method to write a properties file, which stores the
    data of the current played game*/
    public static void writePropFile(String propName) {

        try {
            Properties properties = new Properties();

            if (Main.game.labyrinth.player.getCollectedTreasure() > 0) {
                //properties.setProperty("Treasure", "true");
                properties.setProperty("CollectedTreasure", "" + Main.game.labyrinth.player.getCollectedTreasure());
            }    
            /*else
                properties.setProperty("Treasure", "false");*/

            int currentLifeNumber = Main.game.labyrinth.player.getLives();                    

            properties.setProperty("Lives", "" + currentLifeNumber);

            
            int width = Main.game.labyrinth.getBoard().length;
            properties.setProperty("Width", "" + width);
            int height = Main.game.labyrinth.getBoard()[0].length;
            properties.setProperty("Height", "" + height);

            
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    String propKey = x + "," + y;

                    if (Main.game.labyrinth.getBoard()[x][y] != null) {
                    char propValue = Main.game.labyrinth.getBoard()[x][y].getSymbol();

                        switch(propValue) {
                            case 'X':
                                properties.setProperty(propKey, "0");
                                break;

                            case 'E':
                                properties.setProperty(propKey, "1");
                                break;

                            case 'A':
                                properties.setProperty(propKey, "2");
                                break;

                            case '\u06DE':
                                properties.setProperty(propKey, "3");
                                break;

                            case '\u046E':
                                properties.setProperty(propKey, "4");
                                break;

                            case '$':
                                properties.setProperty(propKey, "5");
                                break;

                            case '\u263A':
                                properties.setProperty(propKey, "6");
                                break;
                            default :
                                break;
                        }
                    }
                }
            }
            File file = new File(propName);
            try (FileOutputStream fileOut = new FileOutputStream(propName)) {
                properties.store(fileOut, propName);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }	


    }
}
