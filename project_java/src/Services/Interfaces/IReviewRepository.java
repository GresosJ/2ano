package Services.Interfaces;

import java.util.List;
import java.util.function.Predicate;

import Domain.Interfaces.IReview;
import Middleware.Domain.NoReviewsWithBusinessException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.ReviewNotFoundException;


public interface IReviewRepository {

    /**
     * Adiciona um Review ao repositorio de Reviews
     * 
     * @param r IReview a ser adicionado
     * @throws ReviewAlreadyExistsException Execeção caso Review ja exista
     */
    public void addReview(IReview r) throws ReviewAlreadyExistsException;
    
    /**
     * Adiciona uma lista de Reviews ao repositorio de Reviews
     * 
     * @param lr Lista de Reviews a serem adicionada
     * @throws ReviewAlreadyExistsException Execeção caso Review ja exista
     */
    public void addReview(List<IReview> lr) throws ReviewAlreadyExistsException;

    /**
     * Verifica se existe reviews para um Business a partir do seu ID
     * 
     * @param busID ID do Business a procurar
     * @return true se houve reviews; false caso contrário.
     */
    public boolean reviewWithBusinessID(String busID);

    public Double getReviewAverageByBusOnMonth(String busID, Integer month) throws NoReviewsWithBusinessException;

    /**
     * Devolve um Review do repositorio a partir do seu ID
     * 
     * @param reId ID da Review a retornar
     * @return IReview correspondente ao ID
     * @throws ReviewNotFoundException Execeção caso a Review não exista
     */
    public IReview getReview(String reId) throws ReviewNotFoundException;

    /**
     * Devolve o IReview do repositório caso ele exista
     * 
     * @param r IReview a ser pesquisado
     * @return IReview correspondente ao r
     * @throws ReviewNotFoundException Execeção caso a Review não exista
     */
    public IReview getReview(IReview r) throws ReviewNotFoundException;

    /**
     * Devolve uma lista de todos as Reviews do repositorio
     * 
     * @return Lista de Reviews da Review Repository
     */
    public List<IReview> getAllReviews();

    /**
     * Devolve uma lista de todos as Reviews do repositorio filtrada pelo Predicado
     * 
     * @param cp Predicado para filtrar as Reviews
     * @return Lista de Reviews da Review Repository filtrada
     */
    public List<IReview> getAllReviews(Predicate<IReview> cp);

    /**
     * Remove um Review do repositorio de Reviews
     * 
     * @param reId ID a ser removida
     * @throws ReviewNotFoundException Execeção caso a Review não exista
     */
    public void removeReview(String reId) throws ReviewNotFoundException;

    /**
     * Remove um Review do repositorio de Reviews
     * 
     * @param r IReview a ser removido
     * @throws ReviewNotFoundException Execeção caso a Review não exista
     */
    public void removeReview(IReview r) throws ReviewNotFoundException;

    /**
     * Método clonado para a classe
     * 
     * @return Repositório clonado
     */
    public IReviewRepository clone();
}
