

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import java.util.InvalidPropertiesFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quynhngo
 * 
 * shown when player wins
 */
public class Victory extends Window {
    public Victory() {
        super("**Congrats. You Won.**");
        
        addComponent(new Button("Exit", new Action() {

          @Override
          public void doAction() {
            System.exit(0);
          }
        }));        
    }
}
