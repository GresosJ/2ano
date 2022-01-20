package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import Domain.Interfaces.IFileRead;
import GestReviews.Queries.IResult;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.LoadCSVHasErrorsException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewNotFoundException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;

public interface IController {
        //
        /**
         * Verifica se é o primerio ficheiro a ser lido
         * 
         * @return true se for o primeiro; false caso contrário
         */
        public boolean isFirstUse();

        /**
         * Carrega para a memória a partir dos ficheiros CSV
         * 
         * @param users    Filepath para o ficheiro csv dos users
         * @param business Filepath para o ficheiro csv dos business
         * @param reviews  Filepath para o ficheiro csv dos reviews
         * @throws IOException
         * @throws FileNotFoundException
         * @throws LoadCSVHasErrorsException Caso ocorra um erro no carregamento dos
         *                                   ficheiros
         */
        public void loadCSVFiles(String users, String business, String reviews)
                        throws IOException, FileNotFoundException, LoadCSVHasErrorsException;

        /**
         * Carrega o SGR a partir de um ficheiro de objetos default: gestReviews.dat
         * 
         * @param filepath Filepath para o ficheiro default
         * @throws IOException
         */
        public void saveState(String filepath) throws IOException;

        /**
         * Grava o estado do SGR em ficheiro de objetos default: gestReviews.dat
         * 
         * @param filePath
         * @throws IOException
         * @throws FileNotFoundException
         * @throws ClassNotFoundException
         */
        public void loadState(String filePath) throws IOException, FileNotFoundException, ClassNotFoundException;

        /**
         * Retorna a lista de ficheiros lidos
         * 
         * @return
         */
        public List<IResult> getFilesRead();

        /**
         * Retorna ficheiro lido
         * 
         * @param fileName Nome do ficheiro a procurar
         * @return
         */
        public IFileRead getFileRead(String fileName) throws FileNotFoundException;

        /**
         * Retorna último ficheiro lido (atual)
         * 
         * @return
         */
        public IResult getFileRead();

        /**
         * Query 1: Retorna a String com os Business que nunca foram avaliados ordenadas
         * alfabeticamente e o total
         * 
         * @return IResult interface com o método toString()
         */
        public IResult getBusNotReviewed();

        /**
         * Query 2: Retorna a String com o número de Reviews e Users distintos que as
         * realizaram dado um ano e mês
         * 
         * @param year  Ano a procurar
         * @param month Mês a procurar
         * 
         * @return IResult interface com o método toString()
         * @throws UserNotFoundException    Caso um User não exista
         * @throws ReviewNotFoundException  Caso uma Review não exista
         * @throws YearIsNotValidException  Caso o ano não seja válido
         * @throws MonthIsNotValidException Caso o mês não seja válido
         */
        public IResult getDatedReviews(Integer year, Integer month) throws UserNotFoundException,
                        ReviewNotFoundException, YearIsNotValidException, MonthIsNotValidException;

        /**
         * Query 3: Retorna a String com o número de reviews, negócios distintos e a
         * nota média que o User dado atribui, para cada mês
         * 
         * @param userID User a procurar
         * 
         * @return IResult interface com o método toString()
         * @throws UserNotFoundException     Caso um User não exista
         * @throws ReviewNotFoundException   Caso uma Review não exista
         * @throws BusinessNotFoundException Caso um Business não exista
         */
        public IResult getUserInfoByMonth(String userID)
                        throws UserNotFoundException, ReviewNotFoundException, BusinessNotFoundException;

        /**
         * Query 4: Retorna a String com o número de vezes que um Business dado foi
         * avaliado, por quantos users diferentes e a média de classificação, por cada
         * mês
         * 
         * @param busID Business a procurar
         * 
         * @return IResult interface com método toString()
         * @throws ReviewNotFoundException   Caso uma Review não exista
         * @throws BusinessNotFoundException Caso um Business não exista
         */
        public IResult getBusInfoByMonth(String busID) throws ReviewNotFoundException, BusinessNotFoundException;

        /**
         * Query 5: Retorna a String com a lista de nomes de negócios que mais avaliou
         * (e quantos), dado um User ID, ordenada por ordem decrescente de quantidade e,
         * para quantidades iguais, por ordem alfabética dos negócios
         * 
         * @param userID User a procurar
         * 
         * @return IResult interface com método toString()
         * @throws UserNotFoundException Caso um User não exista
         */
        public IResult getUserMostReviewedBus(String userID) throws UserNotFoundException;

        /**
         * Query 6: Retorna a String com o conjunto dos X negócios mais avaliados (com
         * mais reviews) em cada ano, indicando o número total de distintos utilizadores
         * que o avaliaram
         * 
         * @param busNum Número máximo de Business
         * 
         * @return IResult interface com método toString()
         */
        public IResult getXMostReviewedBusByYear(Integer busNum);

        /**
         * Query 7: Retorna a String a lista dos três mais famosos negócios em termos de
         * número de reviews, de cidade para cidade
         * 
         * @return IResult interface com método toString()
         */
        public IResult getMostFamousBusByCity();

        /**
         * Query 8: Retorna a String os códigos dos X utilizadores que avaliaram mais
         * negócios diferentes, indicando quantos, sendo o critério de ordenação a ordem
         * decrescente do número de negócios
         * 
         * @param userNum Número máximo de Users
         * 
         * @return IResult interface commétodo toString()
         */
        public IResult getXUsersMostDifBus(Integer userNum);

        /**
         * Query 9: Retorna a String com o conjunto dos X users que mais 
         * avaliaram o Business dado e, para cada um, qual o valor médio de classificação
         * 
         * @param busID Business a procurar
         * @param userNum Número máximo de Users
         * 
         * @return IResult interface commétodo toString()
         * @throws BusinessNotFoundException Caso um Business não exista
         */
        public IResult getXUsersWMostRevs(String busID, Integer userNum) throws BusinessNotFoundException;

        /**
         * Query 10: Calcula a média de classificação por cidade, agrupado por ano
         * 
         * @return IResult interface commétodo toString()
         */
        public IResult getBusAvgByStateNCity();
}
