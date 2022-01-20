package Domain.Interfaces;

import java.util.List;

import Middleware.Domain.CategoryNotFoundException;

public interface IBusiness {

    /**
     * Devolve o Id do Business
     * 
     * @return Business id
     */
    public String getId();

    /**
     * Define a identificação do business
     * 
     * @param id Nova identificação
     */
    public void setId(String id);

    /**
     * Devolve o nome do business
     * 
     * @return Nome
     */
    public String getName();

    /**
     * Define o nome do business
     * 
     * @param name Nome a definir
     */
    public void setName(String name);

    /**
     * Devolve a cidade em que o Business está sediado
     * 
     * @return Cidade
     */
    public String getCity();

    /**
     * Define a cidade em que o business está sediado
     * 
     * @param city
     */
    public void setCity(String city);

    /**
     * 
     * @return Estado onde o bus se encontra
     */
    public String getState();

    /**
     * Define o estado do business
     * @param state Estado a definir
     */
    public void setState(String state);

    /**
     * Devolve a lista de categorias que o business trabalha
     * 
     * @return Lista de categorias
     */
    public List<String> getCategories();

    /**
     * Adiciona uma nova categoria ao business
     * 
     * @param category Categoria a adicionar
     */
    public void addCategory(String category);

    /**
     * Remove uma categoria do business
     * 
     * @param category categoria a remover
     * @throws CategoryNotFoundException Exceção caso a categoria não exista
     */
    public void removeCategory(String category) throws CategoryNotFoundException;

    /**
     * Verifica a igualdade do business com outro objeto Verificação é feita
     * comparando cada uma das variáveis de instância
     * 
     * @param obj Objeto a ser comparado
     * @return boolean...
     */
    public boolean equals(Object obj);

    /**
     * Faz uma cópia profunda do Business
     * 
     * @return Cópia
     */
    public IBusiness clone();

    // public String toString();

}