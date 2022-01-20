package View;

import Controller.Controller;
import Controller.IController;
import Middleware.Domain.LoadCSVHasErrorsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static View.MenuFirstUse.FMenuFirstUse;
import static View.MenuPrincipal.FMenuPrincipal;


public class View implements IView {
    private static IController controller;

    public static IController getController() {
        return controller;
    }


    public static void setController(IController controller) {
        View.controller = controller;
    }

    public View() {
    }

    public View(IController controller) {
        View.setController(controller);
    }

    public void run() {
        if (controller.isFirstUse()) {
            FMenuFirstUse();
        } else {
            try {
                controller.loadCSVFiles(Controller.USERS_CSV,Controller.BUS_CSV,Controller.REVIEWS_CSV);
                FMenuPrincipal();
            } catch (IOException | LoadCSVHasErrorsException e) {
                PanelErro("Load deu erro!!!");
            }
        }
}

    /**
     * Cria uma panel de erro
     *
     * @param msg que vai aparecer no Panel
     */
    public static void PanelErro(String msg) {
        JOptionPane.showMessageDialog(null,
                "ERRO",
                msg,
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Cria uma panel de opcao
     *
     * @param titulo do Panel
     * @param message que vai aparecer no Panel
     * @return a opcao
     */
    public static int PanelOption (String titulo, String message){
        return JOptionPane.showOptionDialog(null,
                message,
                titulo,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "");
    }

    /**
     * Cria uma panel de opcao
     *
     * @param titulo do Panel
     * @param message que vai aparecer no Panel
     * @return a opcao
     */
    public static String PanelInput (String titulo, String message){
        return (String) JOptionPane.showInputDialog(null,
                message,
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "");
    }

    /**
     * "Destroi" a pagina
     *
     * @param e o ActionEvent que esta na pagina para ser "destroida"
     */
    public static void destroy_window(ActionEvent e) {
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }
}