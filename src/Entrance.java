
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author quynhngo
 * 
 */
public class Entrance extends GameObject {
    private final char symbol;
    private String position;
    
    private Terminal.Color color;
    
    public Entrance (String position) {
        symbol = 'E';
        this.position = position;
        this.color = Terminal.Color.BLUE;
    }
    
    @Override
    public char getSymbol() {
        return symbol;
    }
    
    public void setPosition(String position){
        this.position = position;
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
    public String toString() {
        return "" + symbol;
    }
}
