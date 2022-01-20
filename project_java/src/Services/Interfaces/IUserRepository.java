package Services.Interfaces;

import java.util.List;
import java.util.function.Predicate;

import Domain.Interfaces.IUser;
import Middleware.Domain.UserAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;


public interface IUserRepository {
    
    /**
     * Devolve uma lista de todos os Users do repositório
     * 
     * @return Lista de Users do User Repository
     */
    public List<IUser> getAllUsers();

    /**
     * Devolve uma lista dos Users do repositório filtrados pelo Predicado
     * 
     * @param up Predicado para filtrar os Users
     * @return Lista de Users do User Repository filtrada
     */
    public List<IUser> getAllUsers(Predicate<IUser> up);

    /**
     * Devolve um User do repositório a partir do seu ID 
     * 
     * @param userId ID do User a pesquisar
     * @return IUser correspondente ao ID  
     * @throws UserNotFoundException Exceção caso o User não exista
     */
    public IUser getUser(String userId) throws UserNotFoundException;

    /**
     * Devolve o IUser do repositório caso ele exista
     * 
     * @param u IUser a pesquisar 
     * @return IUser correspondente ao passado com argumento
     * @throws UserNotFoundException Exceção caso o user não exista
     */
    public IUser getUser(IUser u) throws UserNotFoundException;
    
    /**
     * Adiciona um User ao repositório de Users
     *  
     * @param u IUser a ser adicionado 
     * @throws UserAlreadyExistsException Exceção caso o User já exista
     */
    public void addUser(IUser u) throws UserAlreadyExistsException;

    /**
     * Adiciona uma lista de Users ao repositório de Users
     * A mensagem impressa pela Exceção diz os Users que estavam repetidos, seja esse o caso
     * 
     * @param lus Lista de IUsers a serem adicionados
     * @throws UserAlreadyExistsException Exceçaõ caso um dos Users já exista
     */
    public void addUser(List<IUser> lus) throws UserAlreadyExistsException;

    /**
     * Remove um User do repositório de Users pelo seu ID
     * 
     * @param userId ID do IUser a ser removido
     * @throws UserNotFoundException Exceção caso o User já não exista no repositório
     */
    public void removeUser(String userId) throws UserNotFoundException;

    /**
     * Remove um User do Repositório de Users
     * 
     * @param u IUser a ser removido
     * @throws UserNotFoundException Exceção caso o User já não exista no repositório
     */
    public void removeUser(IUser u) throws UserNotFoundException;

    /**
     * Método clone para a classe 
     * 
     * @return IRepository clonado
     */
    public IUserRepository clone();
}
