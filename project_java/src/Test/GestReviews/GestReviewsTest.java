package Test.GestReviews;

import static org.junit.Assert.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import Domain.Model.Crono;
import GestReviews.GestReviews;
import GestReviews.IGestReviews;
import GestReviews.Queries.BusInfoByMonth;
import GestReviews.Queries.DatedReviews;
import GestReviews.Queries.IResult;
import GestReviews.Queries.MostFamousBusByCity;
import GestReviews.Queries.UserInfoByMonth;
import GestReviews.Queries.UsersWithMostReviews;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.LoadCSVHasErrorsException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewNotFoundException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;

@TestMethodOrder(OrderAnnotation.class)
public class GestReviewsTest {

    private static IGestReviews gr;
    private static final String PATH = "/home/smarqito/grupo43/";

    @BeforeEach
    void set_up() throws FileNotFoundException, ClassNotFoundException, IOException {
        // if (gr == null)
        //     gr = GestReviews.loadFromBinary(PATH + "/gestReviews.dat");
    }

    @Test
    @Order(1)
    void testLoadFromCSV() throws UserNotFoundException {
        System.out.println("A iniciar carregamento de 3 CSV files no GestReviews (e respetivas stats)");
        gr = new GestReviews();
        Crono.start();
        try {
            gr.loadFromCSV(PATH + "/samples/users.csv", PATH + "/samples/business.csv", PATH + "/samples/reviews.csv");
        } catch (LoadCSVHasErrorsException e) {
            e.printStackTrace();
        }
        Crono.printElapsedTime();
        System.out.println("");

    }

    @Test
    @Order(2)
    void testSaveToBinary() throws FileNotFoundException, IOException {
        System.out.println("A gravar em ficheiro binário");
        Crono.start();
        gr.saveToBinary(PATH + "/gestReviews.dat");
        Crono.printElapsedTime();
        System.out.println("");
    }

    @Order(3)
    @Test
    void testLoadFromBinary() throws FileNotFoundException, ClassNotFoundException, IOException {
        assertThrows(FileNotFoundException.class, () -> GestReviews.loadFromBinary("cenas.dat"));
        System.out.println("A carregar de ficheiro binário");
        Crono.start();
        gr = GestReviews.loadFromBinary(PATH + "/gestReviews.dat");
        Crono.printElapsedTime();
        System.out.println("");
    }

    // query 1
    @Order(4)
    @Test
    void testGetBusNotReviewed() throws FileNotFoundException, ClassNotFoundException, IOException {
        System.out.println("\n--- Teste Query 1 ---");
        System.out.println("Verificar quantos business não têm reviews");
        Crono.start();
        gr.getBusNotReviewed();
        Crono.printElapsedTime();
        System.out.println(" a carregar. \n");
    }

    // query 2
    @Test
    @Order(5)
    void testGetDatedReviews() throws FileNotFoundException, ClassNotFoundException, IOException, UserNotFoundException,
            ReviewNotFoundException, YearIsNotValidException, MonthIsNotValidException {

        System.out.println("\n--- Teste Query 2 ---");
        System.out.println("Carregar reviews a partir de um ano e mês");
        Crono.start();
        DatedReviews dr = gr.getDatedReviews(2015, 3);
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + dr.toString());
        System.out.println("A testar um ano que não existe");
        Crono.start();
        assertThrows(YearIsNotValidException.class, () -> gr.getDatedReviews(3000, 5));
        Crono.printElapsedTime();
        System.out.println(" a verificar que ano não existe");
        System.out.println("A testar um mês que não existe");
        Crono.start();
        assertThrows(MonthIsNotValidException.class, () -> gr.getDatedReviews(2015, 30));
        Crono.printElapsedTime();
        System.out.println(" a verificar que mês não existe");
    }

    // query 3
    @Test
    @Order(6)
    void testGetUserInfoByMonth() throws UserNotFoundException, ReviewNotFoundException, BusinessNotFoundException,
            FileNotFoundException, ClassNotFoundException, IOException {
        System.out.println("\n--- Teste Query 3 ---");
        System.out.println("Carregar info por mês de user através do ID");
        Crono.start();
        UserInfoByMonth uim = gr.getUserInfoByMonth("q_QQ5kBBwlCcbL1s4NVK3g");
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + uim.toString());
        System.out.println("A testar um user que não existe.");
        Crono.start();
        assertThrows(UserNotFoundException.class, () -> gr.getUserInfoByMonth("idnaoxsieter"));
        Crono.printElapsedTime();
        System.out.println(" a verificar que não existe");
    }

    // query 4
    @Test
    @Order(7)
    void testGetBusInfoByMonth() throws ReviewNotFoundException, BusinessNotFoundException {
        System.out.println("\n--- Teste Query 4 ---");
        System.out.println("Carregar info por mês de Business através do ID");
        Crono.start();
        BusInfoByMonth uim = gr.getBusInfoByMonth("EzXlnsWtBuRJw8avEMfBqw");
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + uim.toString());
        System.out.println("A testar um business que não existe.");
        Crono.start();
        assertThrows(BusinessNotFoundException.class, () -> gr.getBusInfoByMonth("idnaoxsieter"));
        Crono.printElapsedTime();
        System.out.println(" a verificar que não existe");

    }

    // query 5
    @Test
    @Order(8)
    void testGetUserBus() throws FileNotFoundException, ClassNotFoundException, IOException, UserNotFoundException {
        System.out.println("\n--- Teste Query 5 ---");
        System.out.println("Carregar business avaliados de user através do ID");
        Crono.start();
        IResult ir = gr.getUserBus("dIIKEfOgo0KqUfGQvGikPg");
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + ir.toString());
        System.out.println("A testar um user que não existe.");
        Crono.start();
        assertThrows(UserNotFoundException.class, () -> gr.getUserBus("idnaoxsieter"));
        Crono.printElapsedTime();
        System.out.println(" a verificar que não existe");

    }

    // query 6
    @Test
    @Order(9)
    void testGetMostReviewedBusYear() {
        System.out.println("\n--- Teste Query 6 ---");
        System.out.println("Carregar as bus mais avaliadas por ano");
        Crono.start();
        IResult ir = gr.getMostReviewedBusYear(5);
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + ir.toString());

    }

    // query 7
    @Test
    @Order(10)
    void testGetMostFamousBusByCity() throws FileNotFoundException, ClassNotFoundException, IOException {
        System.out.println("--- Teste Query 7 ---");
        System.out.println("Carregar info por mês de user através do ID");
        Crono.start();
        MostFamousBusByCity mfbc = gr.getMostFamousBusByCity();
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + mfbc.toString());
        System.out.println("A testar um user que não existe.");
        Crono.start();
        assertThrows(UserNotFoundException.class, () -> gr.getUserInfoByMonth("idnaoxsieter"));
        Crono.printElapsedTime();
        System.out.println(" a verificar que não existe");
    }

    // query 8
    @Test
    @Order(11)
    void testGetMostActiveUsers() {
        System.out.println("\n--- Teste Query 8 ---");
        System.out.println("Carregar N users com mais business avaliados");
        Crono.start();
        IResult ir = gr.getMostActiveUsers(3);
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + ir.toString());
    }

    // query 9
    @Test
    @Order(12)
    void testGetXUsersWithMostRevs() throws BusinessNotFoundException {
        System.out.println("--- Teste Query 9 ---");
        System.out.println("Carregar N users que avaliaram mais o business");
        Crono.start();
        UsersWithMostReviews uwmr = gr.getXUsersWMostRevs("EzXlnsWtBuRJw8avEMfBqw", 2);
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + uwmr.toString());
        System.out.println("A testar um business que não existe.");
        Crono.start();
        assertThrows(BusinessNotFoundException.class, () -> gr.getXUsersWMostRevs("idquenaoexiste", 2));
        Crono.printElapsedTime();
        System.out.println(" a verificar que não existe");
    }

    // query 10

    @Test
    @Order(13)
    void testGetAvgStateCity() {
        System.out.println("\n--- Teste Query 10 ---");
        System.out.println("Carregar as classificações médias por cidade e estado");
        Crono.start();
        IResult ir = gr.getAvgStateCity();
        Crono.printElapsedTime();
        System.out.println(" a carregar. Encontrado:\n" + ir.toString());

    }

}
