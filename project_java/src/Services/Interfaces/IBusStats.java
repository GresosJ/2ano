package Services.Interfaces;

import java.util.List;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;

public interface IBusStats {
    /**
     * Retorna o Id do business relativo a esta estatística
     * 
     * @return
     */
    public String getBusId();

    /**
     * Adiciona stats de relação review com business e user
     * 
     * @param r Review
     * @throws ReviewAlreadyExistsException Caso a review já tenha sido adicionada!
     */
    public void addReview(IReview r) throws ReviewAlreadyExistsException;

    /**
     * Retorna todas as reviews associadas ao business
     * 
     * @return
     */
    public Set<String> getReviews();

    /**
     * retorna todos os users associado ao business (únicos)
     * 
     * @return ordenado de forma decrescente por número de reviews a este business
     */
    public List<String> getUsers();

    /**
     * Retorna um tuple (userId, #vezes que fez review)
     * @param userId User a procurar
     * @return Tuple
     * @throws UserNotFoundException Caso o utilizador procurado não exista
     */
    public SimpleEntry<String, Integer> getUserCounter(String userId) throws UserNotFoundException ;

    /**
     * Calcula a lista de tuples (userId, #vezes que fez review) associadas a este business
     * @return lista de tuples
     */
    public List<SimpleEntry<String, Integer>> getUsersCounter();

    /**
     * Retorna o total de users que fizeram review ao business
     * 
     * @return
     */
    public Integer getTotalUsers();

    /**
     * retorna o total de reviews associadas ao business
     * 
     * @return
     */
    public Integer getTotalReviews();

    public IClassification getClassification();

    public IBusStats clone();

}
