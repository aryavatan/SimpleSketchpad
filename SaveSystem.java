import java.util.ArrayList;
import java.awt.Container;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveSystem extends JFrame {
    private static final long serialVersionUID = 1L;

    // Save the sketchpad to a text file
    public static void Save(Container parent, ArrayList<DrawingObject> drawingObjects) {
        String filePath;

        // Save File Dialog Window
        final JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Sketchpad Text File", "txt");
        fc.setFileFilter(filter);
        int response = fc.showSaveDialog(parent);
        if (response == JFileChooser.APPROVE_OPTION) {
            filePath = fc.getSelectedFile().toString() + ".txt";
        } 
        else {
            return;
        }

        // Write to text file
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write("Save File");

            for (DrawingObject drawing : drawingObjects) {
                bufferedWriter.newLine();
                bufferedWriter.write(drawing.toString());
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}