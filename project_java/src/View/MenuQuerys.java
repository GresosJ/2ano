package View;

import Domain.Model.Crono;
import GestReviews.Queries.IResult;
import Middleware.Domain.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static View.MenuPrincipal.FMenuPrincipal;
import static View.View.*;
import static View.showQuerys.FShowQ;


public class MenuQuerys {
    private JLabel Title;
    private JPanel MenuP;
    private JButton BusAlfa;
    private JButton UserMA;
    private JButton UserAv;
    private JButton BusAv;
    private JButton ReviewUser;
    private JButton BusMAv;
    private JButton BusCity;
    private JButton UserMReviews;
    private JButton MUserBus;
    private JButton MediaB;
    private JButton BackMenuP;
    private IResult result;

    public MenuQuerys() {
        //Query1
        BusAlfa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crono.start();
                result = getController().getBusNotReviewed();
                Crono.stop();
                String Tempo = Crono.getTimeAsString();
                String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                destroy_window(e);
                FShowQ(query);
            }
        });
        //Query2
        UserMA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesAno = PanelInput("Mes e Ano", "Induza o mes e o ano da seguinte forma\n" + "MM/yyyy\n" + "Por exemplo: 11/1998");
                if(mesAno != null) {

                    try {
                        String[] mesEano = mesAno.split("/", 2);
                        Integer mes = Integer.parseInt(mesEano[0]);
                        Integer ano = Integer.parseInt(mesEano[1]);
                        Crono.start();
                        result = getController().getDatedReviews(ano, mes);
                        Crono.stop();
                        String Tempo = Crono.getTimeAsString();
                        String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                        destroy_window(e);
                        FShowQ(query);
                    } catch (UserNotFoundException | ReviewNotFoundException | YearIsNotValidException | MonthIsNotValidException Ex) {
                        PanelErro(Ex.getMessage());
                    }
                }
            }
        });
        //Query3
        UserAv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = PanelInput("ID User", "Induza o ID do user\n" + "Por exemplo: ");

                if(user != null){
                    try {
                        Crono.start();
                        result = getController().getUserInfoByMonth(user);
                        Crono.stop();
                        String Tempo = Crono.getTimeAsString();
                        String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                        destroy_window(e);
                        FShowQ(query);
                    } catch (UserNotFoundException | ReviewNotFoundException | BusinessNotFoundException Ex) {
                        PanelErro(Ex.getMessage());
                    }
                }
            }
        });
        //Query4
        BusAv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bus = PanelInput("ID Bus", "Induza o ID do Negocio\n" + "Por exemplo: 6iYb2HFDywm3zjuRg0shjw");
                if(bus != null) {
                    try {
                        Crono.start();
                        result = getController().getBusInfoByMonth(bus);
                        Crono.stop();
                        String Tempo = Crono.getTimeAsString();
                        String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                        destroy_window(e);
                        FShowQ(query);
                    } catch (ReviewNotFoundException | BusinessNotFoundException Ex) {
                        PanelErro(Ex.getMessage());
                    }
                }
            }
        });
        //Query5
        ReviewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = PanelInput("ID User", "Induza o ID do user\n" + "Por exemplo: ");

                if(user != null){
                    try {
                        Crono.start();
                        result = getController().getUserMostReviewedBus(user);
                        Crono.stop();
                        String Tempo = Crono.getTimeAsString();
                        String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                        destroy_window(e);
                        FShowQ(query);
                    } catch (UserNotFoundException userNotFoundException) {
                        PanelErro(userNotFoundException.getMessage());
                    }

                }
            }
        });
        //Query6
        BusMAv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String top = PanelInput("Top Negocios", "Induza o numero do tops que quer\n" + "Por exemplo: 3");
                if(top != null){
                    Crono.start();
                    Integer nTop = Integer.parseInt(top);

                    result = getController().getXMostReviewedBusByYear(nTop);
                    Crono.stop();
                    String Tempo = Crono.getTimeAsString();
                    String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                    destroy_window(e);
                    FShowQ(query);
                }
            }
        });
        //Query7
        BusCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crono.start();
                result = getController().getMostFamousBusByCity();
                Crono.stop();
                String Tempo = Crono.getTimeAsString();
                String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                destroy_window(e);
                FShowQ(query);

            }
        });
        //Query8
        UserMReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String top = PanelInput("Top Negocios", "Induza o numero do tops que quer\n" + "Por exemplo: 3");
                if(top != null){
                    Crono.start();
                    Integer nTop = Integer.parseInt(top);

                    result = getController().getXUsersMostDifBus(nTop);
                    Crono.stop();
                    String Tempo = Crono.getTimeAsString();
                    String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                    destroy_window(e);
                    FShowQ(query);

                }
            }
        });
        //Query9
        MUserBus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busEuser = PanelInput("ID Bus", "Induza o ID do Negocio e numero de Users\n" + "Por exemplo: 6iYb2HFDywm3zjuRg0shjw,2");
                if(busEuser != null){

                    try {
                        Crono.start();
                        String [] mesEano = busEuser.split(",",2);
                        String bus = mesEano[0];
                        Integer users = Integer.parseInt(mesEano[1]);

                        result = getController().getXUsersWMostRevs(bus, users);
                        Crono.stop();
                        String Tempo = Crono.getTimeAsString();
                        String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                        destroy_window(e);
                        FShowQ(query);
                    }catch (BusinessNotFoundException businessNotFoundException) {
                        PanelErro(businessNotFoundException.getMessage());
                    }
                }
            }
        });
        //Query10
        MediaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crono.start();
                result = getController().getBusAvgByStateNCity();
                Crono.stop();
                String Tempo = Crono.getTimeAsString();
                String query = "Tempo de execucao :" + Tempo + "\n" + result.toString();

                destroy_window(e);
                FShowQ(query);
            }
        });
        BackMenuP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy_window(e);
                FMenuPrincipal();
            }
        });
    }

    static public void loadQFrame() {
        JFrame frame = new JFrame("GestReviews");
        frame.setContentPane(new MenuQuerys().MenuP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
