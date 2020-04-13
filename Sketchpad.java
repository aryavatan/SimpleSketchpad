import java.awt.*;
import java.awt.event.*;

class Sketchpad extends Frame implements ActionListener {
    private static final long serialVersionUID = 1L;

    // Main Method
    public static void main(String args[]) {
        new Sketchpad();
    }

    // Sketchpad Constructor
    Sketchpad() {
        // Frame
        setTitle("Simple Sketchpad");
        setSize(1000, 800);
        setLayout(null);
        setVisible(true);

        // Panel
        Panel panel = new Panel();
        panel.setBounds(0, 30, this.getWidth() + 3000, 40);
        panel.setBackground(Color.lightGray);
        this.add(panel);

        // Menu Bar
        MenuBar bar = new MenuBar();

        Menu fileMenu = new Menu("File");  // File Menu
        MenuItem clear = new MenuItem("Clear");
        clear.addActionListener(this);
        fileMenu.add(clear);
        bar.add(fileMenu);

        Menu drawModeMenu = new Menu("Drawing Mode");
        MenuItem freehand = new MenuItem("Freehand");
        freehand.addActionListener(this);
        MenuItem line = new MenuItem("Straight Line");
        line.addActionListener(this);
        drawModeMenu.add(freehand);
        drawModeMenu.add(line);
        bar.add(drawModeMenu);

        Menu colorMenu = new Menu("Color");  // Color Menu
        MenuItem black = new MenuItem("Black");
        black.addActionListener(this);
        MenuItem blue = new MenuItem("Blue");
        blue.addActionListener(this);
        MenuItem green = new MenuItem("Green");
        green.addActionListener(this);
        MenuItem red = new MenuItem("Red");
        red.addActionListener(this);
        MenuItem yellow = new MenuItem("Yellow");
        yellow.addActionListener(this);
        colorMenu.add(black);
        colorMenu.add(red);
        colorMenu.add(green);
        colorMenu.add(blue);
        colorMenu.add(yellow);
        bar.add(colorMenu);
        setMenuBar(bar);

        // Event Listeners
        addMouseListener(new myMouseHandler());
        addMouseMotionListener(new myMouseMotionHandler());
        addWindowListener(new WindowAdapter() { // Closing the window
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Global Variables
    DrawMode drawMode = DrawMode.FREEHAND;
    Color color = Color.BLACK;
    int x0, y0, x, y;

    // Mouse Handler
    public class myMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x0 = e.getX();
            y0 = e.getY();
            x = x0;
            y = y0;
        }

        public void mouseReleased(MouseEvent e) { 
            Graphics g = getGraphics();
            g.setColor(color);

            switch(drawMode){
                case LINE:
                    x = e.getX();
                    y = e.getY();
                    g.drawLine(x0, y0, x, y);
                    break;
            }
        }
    }

    // Mouse Motion Handler
    public class myMouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) { }

        public void mouseDragged(MouseEvent e) {
            Graphics g = getGraphics();
            g.setColor(color);

            switch (drawMode) {
                case FREEHAND:
                    x0 = x;
                    y0 = y;
                    x = e.getX();
                    y = e.getY();
                    g.drawLine(x0, y0, x, y);
                    break;
                default:
                    break;
            }
        }
    }

    // Action Listener
    public void actionPerformed(ActionEvent e) {
        // Clear Canvas
        if(e.getActionCommand().equals("Clear")){
            repaint();
            return;
        }

        // Drawing Mode Selection
        if(e.getActionCommand().equals("Freehand")){
            this.drawMode = DrawMode.FREEHAND;
        }
        else if(e.getActionCommand().equals("Straight Line")){
            this.drawMode = DrawMode.LINE;
        }

        // Color Selection
        else if(e.getActionCommand().equals("Black")){
            this.color = Color.BLACK;
        }
        else if(e.getActionCommand().equals("Blue")){
            this.color = Color.BLUE;
        }
        else if(e.getActionCommand().equals("Green")){
            this.color = Color.GREEN;
        }
        else if(e.getActionCommand().equals("Red")){
            this.color = Color.RED;
        }
        else if(e.getActionCommand().equals("Yellow")){
            this.color = Color.YELLOW;
        }
    }
}

// Drawing Modes Enum
enum DrawMode {
    FREEHAND,
    LINE
}