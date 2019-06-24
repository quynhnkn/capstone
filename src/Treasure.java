
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author quynhngo
 * 
 * Treasures which player has to collect all before reaching exit
 */
public class Treasure extends GameObject {
    private final char symbol;
    private final String position;
    
    private Terminal.Color color;
    
    public Treasure(String position) {
        this.symbol = '$';
        this.position = position;
        
        this.color = Terminal.Color.YELLOW;
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
    public Terminal.Color getColor() {
        return color;
    }
    
    @Override
    public String toString(){
        return "" + symbol;
    }
}