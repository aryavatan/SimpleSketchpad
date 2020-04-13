import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;

class Sketchpad extends Frame {

    public static void main(String args[]) {
        new Sketchpad().setVisible(true);
    }

    Sketchpad() {
        // Button b = new Button("click me");
        // b.setBounds(30, 100, 80, 30);// setting button position
        // add(b);// adding button into frame
        // setLayout(null);// no layout manager

        setSize(1000, 800);
        addMouseListener(new myMouseHandler());
        addMouseMotionListener(new myMouseMotionHandler());
    }

    DrawMode drawMode = DrawMode.FREEHAND;
    int x0, y0, x, y;

    public class myMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x0 = e.getX(); 
            y0 = e.getY(); 
            x = x0;
            y = y0;
        }

        public void mouseReleased(MouseEvent e) { }
    }

    public class myMouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved (MouseEvent e) { }

        public void mouseDragged (MouseEvent e) {

            switch(drawMode){
                case FREEHAND:
                    x0 = x;
                    y0 = y;
                    x = e.getX();
                    y = e.getY();
                    getGraphics().drawLine(x0, y0, x, y);
                    break;
                default:
                    break;
            }
        }
    }

    public void paint(Graphics g){
        g.drawLine(x0, y0, x, y);
    }

}

enum DrawMode {
    FREEHAND
}
