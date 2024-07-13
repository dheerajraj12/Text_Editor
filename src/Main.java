import java.awt.*;
import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame why = new JFrame("Text Editor");
        String[] what = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox now = new JComboBox(what);
        why.add(now);
        why.setVisible(true);
        why.setSize(500, 500);
        why.setLayout(null);
        String[] what1 = {"3", "5", "9", "16", "26", "34", "41", "57", "60", "73"};
        JComboBox now1 = new JComboBox(what1);
        why.add(now1);
        now.setBounds(3, 30, 90, 25);
        now1.setBounds(100, 30, 40, 25);
        JTextArea now2 = new JTextArea();
        why.add(now2);
        now2.setBounds(3, 65, 875, 875);

    }
}