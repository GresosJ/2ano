package Domain.Interfaces;

import java.util.List;

import Middleware.Domain.FriendNotFoundException;

public interface IUser {


    /**
     * Devolve o ID do User
     * @return User ID
     */
    public String getId();

    /**
     * Define o ID do User
     * @param ID Nova identificação do User
     */
    public void setId(String id);

    /**
     * Devolve o nome do User
     * @return Nome 
     */
    public String getName();

    /**
     * Define o nome do User
     * @param name Novo nome do User
     */
    public void setName(String name);

    /**
     * Devolve a lista de amigos do User
     * @return Lista de Users
     */
    public List<String> getFriends();

    /**
     * Adiciona um novo amigo ao User
     * @param friend Novo amigo do User
     */
    public void addFriend(String friend);

    /**
     * Remove um amigo do User
     * @param friend Amigo a remover
     * @throws FriendNotFoundException Exceção caso o amigo não exista 
     */
    public void removeFriend(String friend) throws FriendNotFoundException;

    /**
     * Verifica a igualdade do User com outro objeto
     * Verificação é feita comparando cada uma das variáveis de instância
     * @param o Objeto a ser comparado
     * @return boolean true se iguais; false caso contrário
     */
    public boolean equals(Object o);

    /**
     * Faz uma cópia profunda do User
     * @return User Copiado
     */
    public IUser clone(); 
}
