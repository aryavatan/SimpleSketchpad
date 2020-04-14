import java.awt.*;

public class DrawingObject {
    public int x1, y1, x2, y2;
    DrawMode type;
    Color color;

    // Drawing Object Constructor
    public DrawingObject(int x1, int y1, int x2, int y2, DrawMode type, Color color){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.type = type;
        this.color = color;
    }

    // The draw method for the drawing object
    public void draw(Graphics g){
        int startX, startY, width, height;  // Used for drawing modes that require width and height
        g.setColor(color);
    
        switch(type){
            case FREEHAND:
            case LINE:
                g.drawLine(x1, y1, x2, y2);
                break;
            case RECTANGLE:
                // Negative width or height causes the rectangle to be filled
                // So the logic below handles negative width and height
                startX = x2 - x1 > 0 ? x1 : x2;
                startY = y2 - y1 > 0 ? y1 : y2;
                width = x2 - x1 > 0 ? x2 - x1 : x1 - x2;
                height = y2 - y1 > 0 ? y2 - y1 : y1 - y2;
                g.drawRect(startX, startY, width, height);
                break;
            case ELLIPSE:
                // Negative width or height causes the ellipse to be invisible
                // So the logic below handles negative width and height
                startX = x2 - x1 > 0 ? x1 : x2;
                startY = y2 - y1 > 0 ? y1 : y2;
                width = x2 - x1 > 0 ? x2 - x1 : x1 - x2;
                height = y2 - y1 > 0 ? y2 - y1 : y1 - y2;
                g.drawArc(startX, startY, width, height, 0, 360);
                break;
            case SQUARE:
                width = x2 - x1;
                height = y2 - y1;
                int side = Math.abs(width) > Math.abs(height) ? Math.abs(width) : Math.abs(height);
                startX = width > 0 ? x1 : x1 - side;
                startY = height > 0 ? y1 : y1 - side;
                g.drawRect(startX, startY, side, side);
                break;
            case CIRCLE:
                width = x2 - x1;
                height = y2 - y1;
                int diameter = Math.abs(width) > Math.abs(height) ? Math.abs(width) : Math.abs(height);
                startX = width > 0 ? x1 : x1 - diameter;
                startY = height > 0 ? y1 : y1 - diameter;
                g.drawArc(startX, startY, diameter, diameter, 0, 360);
                break;
            case ERASER:
                startX = x2 - x1 > 0 ? x1 : x2;
                startY = y2 - y1 > 0 ? y1 : y2;
                width = x2 - x1 > 0 ? x2 - x1 : x1 - x2;
                height = y2 - y1 > 0 ? y2 - y1 : y1 - y2;
                g.fillArc(startX-5, startY-5, width+10, height+10, 0, 360);
                break;
            default:
        }
    }

    // String representation of drawing object, used for saving to text file
    @Override
    public String toString() { 
        return (x1 + "," + y1 + "," + x2 + "," + y2 + "," + type + "," + color.getRGB());
    } 
}