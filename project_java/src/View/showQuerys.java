package View;

import javax.swing.*;
import java.awt.event.*;

import static View.MenuQuerys.loadQFrame;
import static View.View.destroy_window;

public class showQuerys {
    private JPanel showP;
    private JButton Back;
    private JTextArea text;
    private JScrollPane scroll;
    private JScrollBar scrollBar1;


    public showQuerys(String texto) {
        text.setText(texto);

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy_window(e);
                loadQFrame();
            }
        });

    }

    static public void FShowQ(String texto) {
        JFrame frame = new JFrame("GestReviews");
        frame.setContentPane(new showQuerys(texto).showP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
