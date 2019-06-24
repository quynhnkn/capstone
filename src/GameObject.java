
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author quynhngo
 * 
 * Superclass whose subclass are Entrance, DynamicMonster, StaticMonster,
 * Player, Exit, Wall
 */
public class GameObject {
    private char symbol;
    private String position; // is the key in properties files 
    Terminal.Color color;
    
    public char getSymbol() {
        return symbol;
    }
    
    public String getPosition() {
        return position;
    }
    
    public Terminal.Color getColor() {
        return color;
    }
    

    @Override
    public String toString() {
        return "" + symbol;
    }
}