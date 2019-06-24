
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author quynhngo
 */
public class Wall extends GameObject {
    
    private final char symbol;
    private final String position;
    
    private Terminal.Color color;
    
    public Wall(String position) {
        this.symbol = 'X';
        this.position = position;
        this.color = Terminal.Color.WHITE;
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
    public String toString() {
        return "" + symbol;
    }
    
    
}