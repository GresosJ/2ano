package View;

import Controller.Controller;
import Domain.Model.Crono;
import Middleware.Domain.LoadCSVHasErrorsException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static View.MenuPrincipal.FMenuPrincipal;
import static View.View.*;


public class MenuFirstUse{
    private JPanel Menu1U;
    private JTextField fileUserB;
    private JTextField fileBus;
    private JTextField fileReview;
    private JLabel Title;
    private JButton Submit;

    public MenuFirstUse() {
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fUser = fileUserB.getText();
                String fBus = fileBus.getText();
                String fReview = fileReview.getText();

                try {
                    View.getController().loadCSVFiles(fUser,fBus,fReview);
                    destroy_window(e);
                    FMenuPrincipal();
                } catch (IOException ex) {
                    int n = PanelOption("Confirmacao","Quer trocar os nomes dos ficheiros pois deu erro");
                    if(n != 0) {
                        try {
                            View.getController().loadCSVFiles(Controller.USERS_CSV,Controller.BUS_CSV,Controller.REVIEWS_CSV);
                            destroy_window(e);
                            FMenuPrincipal();
                        } catch (IOException | LoadCSVHasErrorsException ioException) {
                            PanelErro("Load deu erro!!!");
                        }
                    }
                } catch (LoadCSVHasErrorsException loadCSVHasErrorsException) {
                    PanelErro("Load deu erro!!!");
                }
            }
        });
    }

    static public void FMenuFirstUse() {
        JFrame frame = new JFrame("GestReviews");
        frame.setContentPane(new MenuFirstUse().Menu1U);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
