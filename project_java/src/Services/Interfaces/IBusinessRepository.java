package Services.Interfaces;

import java.util.List;
import java.util.function.Predicate;

import Domain.Interfaces.IBusiness;
import Middleware.Domain.BusinessAlreadyExistsException;
import Middleware.Domain.BusinessNotFoundException;


public interface IBusinessRepository {
    /**
     * Retorna uma lista de todos os business
     * 
     * @return
     */
    public List<IBusiness> getAllBusiness();

    /**
     * Retorna uma lista de todos os business, aplicando um predicado como filtro
     * 
     * @param cp Predicado a ser aplicado para filtrar
     * @return
     */
    public List<IBusiness> getAllBusiness(Predicate<IBusiness> cp);

    /**
     * Procura um business a partir do id
     * 
     * @param busId Id do bus a procurar
     * @return Bus
     * @throws BusinessNotFoundException CAso o business não exista
     */
    public IBusiness getBusiness(String busId) throws BusinessNotFoundException;

    /**
     * Retorna um business, caso exista
     * 
     * @param b Business a procurar (vai ser utilizado o ID)
     * @return Business
     * @throws BusinessNotFoundException Caso o business não exista
     */
    public IBusiness getBusiness(IBusiness b) throws BusinessNotFoundException;

    /**
     * Adiciona um business ao repositório, caso ainda não exista
     * 
     * @param b Business a adicionar
     * @throws BusinessAlreadyExistsException Business já existe
     */
    public void addBusiness(IBusiness b) throws BusinessAlreadyExistsException;

    /**
     * Adiciona uma lista de business ao repositório
     * 
     * @param lbus Lista de bus
     * @throws BusinessAlreadyExistsException Caso haja algum business que já
     *                                        existia (adiciona os restantes)
     */
    public void addBusiness(List<IBusiness> lbus) throws BusinessAlreadyExistsException;

    /**
     * Remove um business do repositório, caso exista
     * @param busId Identificador do id
     * @throws BusinessNotFoundException Caso o business não exista
     */
    public void removeBusiness(String busId) throws BusinessNotFoundException;

    /**
     * Remove um business do repositório
     * @param ibus Business a remover
     * @throws BusinessNotFoundException Caso o business não exista
     */
    public void removeBusiness(IBusiness ibus) throws BusinessNotFoundException;

    /**
     * efetua clone do repositório
     * @return
     */
    public IBusinessRepository clone();
}