package GestReviews;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import Domain.Interfaces.IBusiness;
import Domain.Interfaces.IFileRead;
import Domain.Interfaces.IReview;
import Domain.Interfaces.IUser;
import GestReviews.Queries.BusInfoByMonth;
import GestReviews.Queries.BusNotReviewed;
import GestReviews.Queries.CityStateAvg;
import GestReviews.Queries.DatedReviews;
import GestReviews.Queries.MostFamousBusByCity;
import GestReviews.Queries.MostRevByYear;
import GestReviews.Queries.UserBusCounter;
import GestReviews.Queries.UserInfoByMonth;
import GestReviews.Queries.UserMostBusDTO;
import GestReviews.Queries.UsersWithMostReviews;
import Middleware.Domain.BusinessAlreadyExistsException;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.LoadCSVHasErrorsException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.ReviewNotFoundException;
import Middleware.Domain.UserAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;
import Services.Interfaces.IBusStats;
import Services.Interfaces.IUserStats;

public interface IGestReviews {

        /**
         * Adiciona um user à app
         * 
         * @param u User a adicionar
         */
        public void addUser(IUser u) throws UserAlreadyExistsException;

        /**
         * Adiciona uma lista de users à app
         * 
         * @param u User
         */
        public void addUser(List<IUser> u) throws UserAlreadyExistsException;

        /**
         * Adiciona uma review à app
         * 
         * @param r Review
         * @throws UserNotFoundException        Caso o user associado à review não
         *                                      exista no sistema
         * @throws BusinessNotFoundException    Caso o business associado à review não
         *                                      exista no sistema
         * @throws ReviewAlreadyExistsException Caso o review já exista
         */
        public void addReview(IReview r)
                        throws UserNotFoundException, BusinessNotFoundException, ReviewAlreadyExistsException;

        /**
         * Adiciona uma lista de reviews à app
         * 
         * @param lr
         * @throws ReviewAlreadyExistsException Caso o review já exista
         */
        public void addReview(List<IReview> lr) throws ReviewAlreadyExistsException;

        /**
         * Adiciona um bus ao à app
         * 
         * @param b Businessa
         * @throws BusinessAlreadyExistsException Caso já exista
         */
        public void addBusiness(IBusiness b) throws BusinessAlreadyExistsException;

        /**
         * Adiciona uma lista de business ao sistema
         * 
         * @param lb Lista de business
         * @throws BusinessAlreadyExistsException Caso haja bus que já exista
         */
        public void addBusiness(List<IBusiness> lb) throws BusinessAlreadyExistsException;

        /**
         * Retorna um business através do seu ID
         * 
         * @param id identificação do business
         * @return
         * @throws BusinessNotFoundException Caso não seja encontrado
         */
        public IBusiness getBusiness(String id) throws BusinessNotFoundException;

        /**
         * Retorna um business através do seu ID
         * 
         * @param id identificação do business
         * @return
         * @throws BusinessNotFoundException Caso não seja encontrado
         */
        public IBusStats getBusinessStats(String id) throws BusinessNotFoundException;

        /**
         * Retorna um review através do seu ID
         * 
         * @param id identificação do business
         * @return
         * @throws ReviewNotFoundException Caso não encontre a review
         */
        public IReview getReview(String id) throws ReviewNotFoundException;

        /**
         * Retorna um user através do seu ID
         * 
         * @param id identificador do user
         * @return
         * @throws UserNotFoundException Caso o user não seja encontrado
         */
        public IUser getUser(String id) throws UserNotFoundException;

        /**
         * Retorna um user através do seu ID
         * 
         * @param id identificador do user
         * @return
         * @throws UserNotFoundException Caso o user não seja encontrado
         */
        public IUserStats getUserStats(String id) throws UserNotFoundException;

        public Integer numFilesRead();

        /**
         * Query 1: Retorna uma classe com o os negócios que não foram avaliados
         * ordenados de forma alfabética e o seu total
         * 
         * @return BusNotReviewed Info sobre os negócios não avaliados
         */
        public BusNotReviewed getBusNotReviewed();

        /**
         * Query 2: Retorna uma classe com as reviews feitas dados um mês e ano Também
         * retorna o total
         * 
         * @param year  Ano a procurar
         * @param month Mês a procurar
         * 
         * @return DatedReviews com a info necessária
         * @throws UserNotFoundException    Caso um user não seja encontrado
         * @throws ReviewNotFoundException  Caso uma review não seja encontrada
         * @throws YearIsNotValidException  Caso o ano não seja válido
         * @throws MonthIsNotValidException Caso o mês não seja válido
         */
        public DatedReviews getDatedReviews(Integer year, Integer month) throws UserNotFoundException,
                        ReviewNotFoundException, YearIsNotValidException, MonthIsNotValidException;

        /**
         * Query 3: Retorna uma classe com a informação de um user organizada por mês
         * Isto inclui as suas reviews, os seus negócios e a média da classficação por
         * mês
         * 
         * @param userID User a procurar
         * 
         * @return UserInfoByMonth com a informação necessária
         * @throws UserNotFoundException     Caso um user não seja encontrado
         * @throws ReviewNotFoundException   Caso uma review não seja encontrada
         * @throws BusinessNotFoundException Caso uma business não seja encontrada
         */
        public UserInfoByMonth getUserInfoByMonth(String userID)
                        throws UserNotFoundException, ReviewNotFoundException, BusinessNotFoundException;

        /**
         * Query 4: Retorna uma classe com a informação de Business organizada por mês
         * Isto inclui o número de reviews, o número de users e a média de classificação
         * por mês
         * 
         * @param busID Business a procurar
         * 
         * @return BusInfoByMonth com a informação necessária
         * @throws ReviewNotFoundException   Caso uma review não seja encontrada
         * @throws BusinessNotFoundException Caso uma business não seja encontrada
         */
        public BusInfoByMonth getBusInfoByMonth(String busID) throws ReviewNotFoundException, BusinessNotFoundException;

        /**
         * Query 5: Dado um user, calcula todos os business a que fez review e o número
         * de vezes. Retorna por ordem decrescente do número de reviews e, caso iguais,
         * ordem de nome
         * 
         * @param userId ID do user
         * @return ver classe
         * @throws UserNotFoundException Caso o user não exista
         */
        public UserBusCounter getUserBus(String userId) throws UserNotFoundException;

        /**
         * Query 6: Calcula os N business mais avaliados em cada ano
         * @param n Máximo de bus por ano
         * @return Class agregadora
         */
        public MostRevByYear getMostReviewedBusYear(Integer n);

        /**
         * Query 7: Retorna uma classe com a informação dos 3 negócios mais famosos de
         * cada cidade São os negócios mais famosos aqueles que têm o maior número de
         * reviews
         * 
         * @return MostFamousBusByCity com a informação necessária
         */
        public MostFamousBusByCity getMostFamousBusByCity();

        /**
         * Query 8: Calcula os N users com mais negócios avaliados
         * 
         * @param n Número máximo de users a apresentar
         * @return Classe agregadora
         */
        public UserMostBusDTO getMostActiveUsers(Integer n);

        /**
         * Query 9: Calcula os X users que mais avaliaram e o valor médio das suas
         * classificações, a partir do ID de um negócio, ordenados pelo seu valor médio
         * Os que têm médias iguais são ordenados pelo seu ID
         * 
         * @param busID   ID do negócio a procurar
         * @param userNum Número máximo de users a apresentar
         * 
         * @return Classe agregadora
         * @throws BusinessNotFoundException Caso uma business não seja encontrada
         */
        public UsersWithMostReviews getXUsersWMostRevs(String busID, Integer userNum) throws BusinessNotFoundException;

        /**
         * Query 10: Calcula a média de classificação por cidade e agrupada por estado
         * 
         * @return
         */
        public CityStateAvg getAvgStateCity();

        /**
         * Popula os repositórios do Gest Reviews
         * 
         * @param users    filepath para o ficheiro csv de users
         * @param business filepath para o ficheiro csv de business
         * @param reviews  filepath para o ficheiro csv de reviews
         */
        public void loadFromCSV(String users, String business, String reviews) throws LoadCSVHasErrorsException;

        /**
         * Grava o estado da app num ficheiro de objetos
         * 
         * @param filepath Path para ficheiro
         * @throws IOException           ...
         * @throws FileNotFoundException Caso não encontro o ficheiro
         */
        public void saveToBinary(String filepath) throws IOException, FileNotFoundException;

        public IFileRead getLastFileRead() throws NoSuchElementException;

}
