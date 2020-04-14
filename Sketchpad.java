import java.awt.*;
import java.awt.event.*;
import java.util.*;  

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
        Color grey = new Color(245,245,245);

        // Panel Labels
        lMode = new Label("Drawing Mode: Freehand");
        lMode.setBackground(grey);
        lMode.setForeground(Color.gray);
        lMode.setBounds(47, 40, 180, 20);
        this.add(lMode);
        Label lColor = new Label("Color:");
        lColor.setBackground(grey);
        lColor.setForeground(Color.gray);
        lColor.setBounds(240, 40, 40, 20);
        this.add(lColor);
        pColor = new Panel();
        pColor.setBounds(280, 43, 15, 15);
        pColor.setBackground(this.color);
        this.add(pColor);

        // Panel
        Panel panel = new Panel();
        panel.setBounds(0, 30, this.getWidth() + 3000, 40);
        panel.setBackground(grey);
        this.add(panel);

        // Menu Bar
        MenuBar bar = new MenuBar();

        Menu fileMenu = new Menu("File");  // File Menu
        MenuItem save = new MenuItem("Save");
        save.addActionListener(this);
        MenuItem clear = new MenuItem("Clear");
        clear.addActionListener(this);
        fileMenu.add(save);
        fileMenu.add(clear);
        bar.add(fileMenu);

        Menu drawModeMenu = new Menu("Drawing Mode");  // Drawing Modes Menu
        MenuItem freehand = new MenuItem("Freehand");
        freehand.addActionListener(this);
        MenuItem line = new MenuItem("Straight Line");
        line.addActionListener(this);
        MenuItem rect = new MenuItem("Rectangle");
        rect.addActionListener(this);
        MenuItem square = new MenuItem("Square");
        square.addActionListener(this);
        MenuItem ellipse = new MenuItem("Ellipse");
        ellipse.addActionListener(this);
        MenuItem circle = new MenuItem("Circle");
        circle.addActionListener(this);
        drawModeMenu.add(freehand);
        drawModeMenu.add(line);
        drawModeMenu.add(rect);
        drawModeMenu.add(square);
        drawModeMenu.add(ellipse);
        drawModeMenu.add(circle);
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
    ArrayList<DrawingObject> drawingObjects = new ArrayList<DrawingObject>();
    DrawMode drawMode = DrawMode.FREEHAND;
    Color color = Color.BLACK;
    int x0, y0, x, y;
    Label lMode;
    Panel pColor;

    // Mouse Handler
    public class myMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x0 = e.getX();
            y0 = e.getY();
            x = x0;
            y = y0;

            switch(drawMode){
                case LINE:
                case RECTANGLE:
                case ELLIPSE:
                case SQUARE:
                case CIRCLE:
                    drawingObjects.add(new DrawingObject(x0,y0,x,y,drawMode,color));
                    break;
                default:
            }
        }

        public void mouseReleased(MouseEvent e) { }
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
                    drawingObjects.add(new DrawingObject(x0,y0,x,y,drawMode,color));
                    break;
                case LINE:
                case RECTANGLE:
                case ELLIPSE:
                case SQUARE:
                case CIRCLE:
                    x = e.getX();
                    y = e.getY();
                    drawingObjects.get(drawingObjects.size()-1).x2 = x;
                    drawingObjects.get(drawingObjects.size()-1).y2 = y;
                    repaint();
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
            drawingObjects.clear();
            repaint();
            return;
        }
        else if(e.getActionCommand().equals("Save")){
            SaveSystem.Save(this, drawingObjects);
            return;
        }

        // Drawing Mode Selection
        else if(e.getActionCommand().equals("Freehand")){
            this.drawMode = DrawMode.FREEHAND;
        }
        else if(e.getActionCommand().equals("Straight Line")){
            this.drawMode = DrawMode.LINE;
        }
        else if(e.getActionCommand().equals("Rectangle")){
            this.drawMode = DrawMode.RECTANGLE;
        }
        else if(e.getActionCommand().equals("Ellipse")){
            this.drawMode = DrawMode.ELLIPSE;
        }
        else if(e.getActionCommand().equals("Square")){
            this.drawMode = DrawMode.SQUARE;
        }
        else if(e.getActionCommand().equals("Circle")){
            this.drawMode = DrawMode.CIRCLE;
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

        updateInfoPanel();  // Update info panel if drawing mode or color changed
    }

    // Updates the info panel with the current drawing mode and color selected
    private void updateInfoPanel(){
        // Update drawing mode label
        String base = "Drawing Mode: %s";
        switch(drawMode){
            case FREEHAND:
                lMode.setText(String.format(base, "Freehand"));
                break;
            case LINE:
                lMode.setText(String.format(base, "Straight Line"));
                break;
            case RECTANGLE:
                lMode.setText(String.format(base, "Rectangle"));
                break;
            case ELLIPSE:
                lMode.setText(String.format(base, "Ellipse"));
                break;
            case SQUARE:
                lMode.setText(String.format(base, "Square"));
                break;
            case CIRCLE:
                lMode.setText(String.format(base, "Circle"));
                break;
            default:
        }

        // Update color panel
        pColor.setBackground(this.color);
    }

    // Redraws all previous drawing objects when repaint() is called
    public void paint(Graphics g){
        for(DrawingObject object : drawingObjects){
            object.draw(g);
        }
    }
}

// Drawing Modes Enum
enum DrawMode {
    FREEHAND,
    LINE,
    RECTANGLE,
    ELLIPSE,
    SQUARE,
    CIRCLE
}