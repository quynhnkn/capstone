
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import java.util.InvalidPropertiesFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quynhngo
 * When player loses all his lives before reaching Exit, show this Window
 */
public class GameOver extends Window {
    
    public GameOver() {
        super("**Game Over**");
        
        addComponent(new Button("Exit", new Action() {

          @Override
          public void doAction() {
            System.exit(0);
          }
        }));
    }
}
