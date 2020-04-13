import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;

class Sketchpad extends Frame {
    private static final long serialVersionUID = 1L;

    // Main Method
    public static void main(String args[]) {
        new Sketchpad().setVisible(true);
    }

    // Sketchpad Constructor
    Sketchpad() {
        setSize(1000, 800);
        addMouseListener(new myMouseHandler());
        addMouseMotionListener(new myMouseMotionHandler());
    }

    // Global Variables
    DrawMode drawMode = DrawMode.FREEHAND;
    int x0, y0, x, y;

    // Mouse Handler
    public class myMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x0 = e.getX(); 
            y0 = e.getY(); 
            x = x0;
            y = y0;
        }

        public void mouseReleased(MouseEvent e) { }
    }

    // Mouse Motion Handler
    public class myMouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved (MouseEvent e) { }

        public void mouseDragged (MouseEvent e) {

            switch(drawMode){
                case FREEHAND:
                    x0 = x;
                    y0 = y;
                    x = e.getX();
                    y = e.getY();
                    Graphics g = getGraphics();
                    g.setColor(Color.blue);
                    g.drawLine(x0, y0, x, y);
                    break;
                default:
                    break;
            }
        }
    }
}

// Drawing Modes Enum
enum DrawMode {
    FREEHAND
}