package Services.Interfaces;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import Domain.Interfaces.IBusiness;
import Domain.Interfaces.IKeyValueSet;
import Domain.Interfaces.IReview;
import Domain.Interfaces.IUser;
import Domain.Interfaces.IClassification;
import Domain.Interfaces.IFileRead;
import Middleware.CityNotFoundException;
import Middleware.StateNotFoundException;
import Middleware.Domain.BusinessAlreadyExistsException;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.UserAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;

public interface IStatsRepository {

        /**
         * Adiciona os stats relacionados a um file read (CSV)
         * 
         * @param fr Stats do ficheiro lido
         */
        public void addFileRead(IFileRead fr);

        /**
         * Retorna o ficheiro lido, a partir do nome
         * 
         * @param fileName Ficheiro lido
         * @return Stats do ficheiro
         */
        public IFileRead getFileRead(String fileName) throws FileNotFoundException;

        /**
         * 
         * @return Ultimo ficheiro lido
         */
        public IFileRead getFileRead() throws NoSuchElementException;

        /**
         * 
         * @return Todos os ficheiros lidos
         */
        public List<IFileRead> getFilesRead();

        /**
         * Adiciona um user para armazenar as suas estatísticas
         * 
         * @param u User
         * @throws UserAlreadyExistsException Caso o user já tenha sido adicionado
         */
        public void addStatsUser(IUser u) throws UserAlreadyExistsException;

        /**
         * Adiciona um Business para armazenar as suas estatísticas
         * 
         * @param b Business
         * @throws BusinessAlreadyExistsException Caso o business já exista
         */
        public void addStatsBus(IBusiness b) throws BusinessAlreadyExistsException;

        /**
         * Adiciona uma review e as respetivas relações: user->review; user->bus;
         * bus->review; bus->user
         * 
         * Caso o id do business ou do user não existam, a review é ignorada e não é
         * adicionada!
         * 
         * @param r Review
         * @throws UserNotFoundException        User não encontrado
         * @throws BusinessNotFoundException    Business não encontrada
         * @throws ReviewAlreadyExistsException Caso a review já tenha sido adicionada
         */
        public void addStatsReview(IReview r)
                        throws UserNotFoundException, BusinessNotFoundException, ReviewAlreadyExistsException;

        /**
         * Retorna as estatísticas relativas a um business
         * 
         * @param busId Identificação do business
         * @return Estatísticas de um business
         * @throws BusinessNotFoundException Caso o business não exista
         */
        public IBusStats getBusStats(String busId) throws BusinessNotFoundException;

        /**
         * Retorna as estatísticas relativas a um user
         * 
         * @param userId Identificação do user
         * @return Estatísticas do user
         * @throws UserNotFoundException Caso o user não exista
         */
        public IUserStats getUserStats(String userId) throws UserNotFoundException;

        /**
         * Retorna um set de reviews associados a um user
         * 
         * @param userId ID do user
         * @return Set de review ids
         * @throws UserNotFoundException Caso o user procurado não exista
         */
        public Set<String> getUserReviews(String userId) throws UserNotFoundException;

        /**
         * Retorna um set de reviews associados a um user
         * 
         * @param user user
         * @return Set de review ids
         * @throws UserNotFoundException Caso o user procurado não exista
         */
        public Set<String> getUserReviews(IUser user) throws UserNotFoundException;

        /**
         * Retorna um set de business ids associados a um user id
         * 
         * @param userId User id a procurar
         * @return Set de bus ids
         * @throws UserNotFoundException Caso o utilizador procurado não exista
         */
        public List<String> getUserBus(String userId) throws UserNotFoundException;

        /**
         * Retorna um set de business ids associados a um user id
         * 
         * @param user User a procurar
         * @return Set de bus ids
         * @throws UserNotFoundException Caso o utilizador procurado não exista
         */
        public List<String> getUserBus(IUser user) throws UserNotFoundException;

        /**
         * Calcula uma lista de pares com business a que fez review e quantas vezes o
         * fez; ordenado por número de reviews
         * 
         * @param userId Id do user
         * @return Lista de pares <busid, num_reviews_feitas>
         * @throws UserNotFoundException Caso o user não exista
         */
        public List<SimpleEntry<String, Integer>> getUserBusTuple(String userId) throws UserNotFoundException;

        /**
         * Retorna um set de review ids associados a um business id
         * 
         * @param busId bus id a procurar
         * @return Set de review ids
         * @throws BusinessNotFoundException Caso o business procurado não exista
         */
        public Set<String> getBusReviews(String busId) throws BusinessNotFoundException;

        /**
         * Retorna um set de review ids associados a um business id
         * 
         * @param bus bus id a procurar
         * @return Set de review ids
         * @throws BusinessNotFoundException Caso o business procurado não exista
         */
        public Set<String> getBusReviews(IBusiness bus) throws BusinessNotFoundException;

        /**
         * Retorna um set de user ids associados a um business id
         * 
         * @param busId business a procurar
         * @return List de user ids
         * @throws BusinessNotFoundException Caso o business procurado não exista
         */
        public List<String> getBusUsers(String busId) throws BusinessNotFoundException;

        /**
         * Retorna um set de user ids associados a um business id
         * 
         * @param bus business a procurar
         * @return List de user ids
         * @throws BusinessNotFoundException Caso o business procurado não exista
         */
        public List<String> getBusUsers(IBusiness bus) throws BusinessNotFoundException;

        /**
         * Calcula uma lista de todos os users que fizeram review ao busId; ordenado por
         * ordem de reviews
         * 
         * @param busId Identificador do business
         * @return Lista de pares <user_id , num_reviews> associados ao busId
         * @throws BusinessNotFoundException Caso o business não existe
         */
        public List<SimpleEntry<String, Integer>> getBusUsersTuple(String busId) throws BusinessNotFoundException;

        /**
         * Retorna todas as cidades associadas a um estado
         * 
         * @param state Estado procurado
         * @return Cidades
         * @throws StateNotFoundException Caso o estado não exista
         */
        public List<String> getCities(String state) throws StateNotFoundException;

        /**
         * 
         * @return Lista de key value set com state na key e cidades nos values
         */
        public List<IKeyValueSet<String, String>> getStateCities();

        /**
         * Calcula os business associados a uma cidade
         * 
         * @param city Cidade a procurar
         * @return List de business Ids da cidade
         * @throws CityNotFoundException Caso a cidade não exista
         */
        public List<String> getCityBusiness(String city) throws CityNotFoundException;

        /**
         * 
         * @return Lista de pares com cidade e set de bus Id
         */
        public List<IKeyValueSet<String, String>> getCityBusiness();

        /**
         * Calcula as reviews ordenadas por ano
         * 
         * @return
         */
        public Set<IKeyValueSet<Integer, String>> getReviewsByYear();

        /**
         * Pesquisa os reviews dado um ano e mês
         * 
         * @param year  Ano
         * @param month Mês
         * @return Lista de ids de reviews num dado ano e mês
         * @throws YearIsNotValidException  Caso não se encontre nenhuma review para o
         *                                  ano
         * @throws MonthIsNotValidException Caso não se encontre nenhuma review para um
         *                                  mês
         */
        public List<String> getReviewsYearMonth(Integer year, Integer month)
                        throws YearIsNotValidException, MonthIsNotValidException;

        /**
         * @return total de reviews
         */
        public Integer getTotalReviews();

        /**
         * @return total de users
         */
        public Integer getTotalUsers();

        /**
         * @return total de business
         */
        public Integer getTotalBus();

        /**
         * 
         * @return total de reviews com funny, cool e usefull a zero
         */
        public Integer getUselessReviews();

        /**
         * 
         * @return total de reviews erradas
         */
        public Integer getWrongReviews();

        /**
         * 
         * @return Set de classificações médias por mês
         */
        public Set<Entry<Integer, IClassification>> getStatsByMonth();

        /**
         * Calcula as classificações
         * 
         * @param month
         * @return
         * @throws MonthIsNotValidException
         */
        public IClassification getStatsByMonth(Integer month) throws MonthIsNotValidException;

        /**
         * Calcula o top N bus stats por ano
         * @param n Máximo de bus por ano
         * @return Mapa com ano e lista de bus mais reviewed nesse ano
         */
        public Map<Integer, List<IBusStats>> getStatsbyYear(Integer n);

        /**
         * 
         * @return Classificações globais do repositório
         */
        public IClassification getGlobalStats();

        /**
         * 
         * @return Mapa de cidades e respetiva classificação média
         */
        public Map<String, IClassification> getClassfCities();
        
        /**
         * 
         * @return Mapa de classificação média de cidades, agregada por state
         */
        public Map<String, Map<String, IClassification>> getClassfStates();

        /**
         * Itera todos os busStats aplicado a função recebida como argumento
         * 
         * @param <T> Tipo de saída
         * @param f   Função a ser aplciada
         * @return Lista de T
         */
        public <T> List<T> iterateBusStats(Function<IBusStats, T> f);

        /**
         * Itera todos os UserStats aplicado a função recebida como argumento
         * 
         * @param <T> Tipo de saída
         * @param f   Função a ser aplciada
         * @return Lista de T
         */
        public <T> List<T> iterateUsersStats(Function<IUserStats, T> f);

        /**
         * Itera todos os busStats aplicado o predicado como filtro recebida como
         * argumento
         * 
         * @param <T> Tipo de saída
         * @param f   Função a ser aplciada
         * @return Lista de T
         */
        public List<IBusStats> iterateBusStats(Predicate<IBusStats> f);

        /**
         * Itera todos os UserStats aplicado o predicado como filtro recebida como
         * argumento
         * 
         * @param <T> Tipo de saída
         * @param f   Função a ser aplciada
         * @return Lista de T
         */
        public List<IUserStats> iterateUsersStats(Predicate<IUserStats> f);

        /**
         * Retorna todos os bustats, ordenados pelo comparator
         * 
         * @param c Comparador a ser aplicado
         * @return Todos os busStats ordenados pelo comparador indicado
         */
        public List<IBusStats> iterateBusStats(Comparator<IBusStats> c);

        /**
         * Retorna todos os userStats, ordenados pelo comparator
         * 
         * @param c Comparador a ser aplicado
         * @return Todos os userStats ordenados pelo comparador indicado
         */
        public List<IUserStats> iterateUsersStats(Comparator<IUserStats> c);

}
