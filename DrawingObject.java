import java.awt.*;

public class DrawingObject {
    int x1, y1, x2, y2;
    DrawMode type;
    Color color;

    public DrawingObject(int x1, int y1, int x2, int y2, DrawMode type, Color color){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.type = type;
        this.color = color;
    }

    public void draw(Graphics g){
        g.setColor(color);
        switch(type){
            case FREEHAND:
            case LINE:
                g.drawLine(x1, y1, x2, y2);
                break;
            default:
        }
    }
}