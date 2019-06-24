
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author quynhngo
 * 
 * static Monster cannot move
 */
public class StaticMonster extends GameObject {
    
    private final char symbol;
    private final String position;
    
    private Terminal.Color color;
    
    public StaticMonster(String position){
        this.symbol = '\u06DE';
        this.position = position;
        this.color = Terminal.Color.CYAN;
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
    public String toString() {
        return "" + symbol;
    }
    
    @Override
    public Terminal.Color getColor() {
        return color;
    }

}