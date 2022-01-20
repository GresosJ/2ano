package Services.Interfaces;

import java.util.List;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import Domain.Interfaces.IClassification;
import Domain.Interfaces.IReview;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.ReviewAlreadyExistsException;

public interface IUserStats {
    /**
     * Retorna o id da instância
     * 
     * @return
     */
    public String getId();

    /**
     * Adiciona a informação estatistica relativa a uma review
     * 
     * @param r Review a adicionar
     */
    public void addReview(IReview r) throws ReviewAlreadyExistsException;

    /**
     * 
     * @return todas as reviews (ids) associadas ao user
     */
    public Set<String> getReviews();

    /**
     * 
     * @return todos os business com ordenação DESC número de reviews
     */
    public List<String> getBusiness();

    /**
     * 
     * @param busId Id do business a procurar
     * @return Tuple <BusId, número de reviews a este business>
     * @throws BusinessNotFoundException Caso o business id não exista
     */
    public SimpleEntry<String, Integer> getBusCounter(String busId) throws BusinessNotFoundException;

    /**
     * 
     * @return Retorna uma lista de tuples com busid e número de vezes que o user
     *         vez review ao bus
     */
    public List<SimpleEntry<String, Integer>> getBusCounter();

    /**
     * 
     * @return Total de reviews que o user fez
     */
    public Integer getTotalReviews();

    /**
     * 
     * @return número de business (unique) a que fez reviews
     */
    public Integer getTotalBus();

    public Set<String> getBusRevs(String busID);

    public IClassification getClassification();

    public IUserStats clone();

}
