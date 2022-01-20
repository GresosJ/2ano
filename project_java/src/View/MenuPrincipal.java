package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static View.MenuFirstUse.FMenuFirstUse;
import static View.MenuQuerys.loadQFrame;
import static View.View.*;


public class MenuPrincipal {

    private JLabel Title;
    private JButton saveFile;
    private JButton loadFile;
    private JButton querys;
    private JButton exitB;
    private JPanel MenuP;
    private JButton MetodosE;

    public MenuPrincipal() {

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String file = PanelInput("FilePath", "Induza o filePath a ser gravado\n");
                    View.getController().saveState(file);
                } catch (IOException e1) {
                    PanelErro("Ficheiros dao erro a carregar");
                }
            }
        });
        loadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy_window(e);
                FMenuFirstUse();
            }
        });
        querys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy_window(e);
                loadQFrame();
            }
        });
        MetodosE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //IResul result = getController.getMetodosEstatisco();
                //String text = result.toString();
                //JOptionPane.showMessageDialog(null, text);
            }
        });
        exitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Obrigado pelo 20 :)");
                System.exit(0);

            }
        });
    }

    static public void FMenuPrincipal() {
        JFrame frame = new JFrame("GestReviews");
        frame.setContentPane(new MenuPrincipal().MenuP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
