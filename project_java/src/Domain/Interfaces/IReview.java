package Domain.Interfaces;

import java.time.LocalDateTime;

public interface IReview {


    /**
     * Devolve o ID do Review
     * @return ID do Review 
     */
    public String getReviewID();

    /**
     * Define o ID do Review
     * @param review_id Novo ID do Review
     */
    public void setReviewID(String review_id);
    
    /**
     * Devolve o ID do User
     * @return ID do User
     */
    public String getUserID();

    /**
     * Define o ID do User
     * @param user_id Novo ID do User
     */
    public void setUserID(String user_id);
    
    /**
     * Devolve o ID do Business
     * @return ID do Business
     */
    public String getBusID();

    /**
     * Define o ID do Business
     * @param name Novo ID do Business
     */
    public void setBusID(String bus_id);

    /**
     * Devolve o numero de stars do Review
     * @return Numero de stars
     */
    public Double getStars();

    /**
     * Define o numero de stars do Review
     * @param name Novo numero de stars
     */
    public void setStars(Double stars);

    /**
     * Devolve o numero de useful do Review
     * @return Numero de useful 
     */
    public Integer getUseful();

    /**
     * Define o numero de useful do Review
     * @param name Novo numero de useful
     */
    public void setUseful(Integer useful);
    
    /**
     * Devolve o numero de funny do Review
     * @return Numero de funny 
     */
    public Integer getFunny();

    /**
     * Define o numero de funny do Review
     * @param name Novo numero de funny
     */
    public void setFunny(Integer funny);

    /**
     * Devolve o numero de cool do Review
     * @return Numero de cool
     */
    public Integer getCool();

    /**
     * Define o numero de cool do Review
     * @param name Novo numero de cool
     */
    public void setCool(Integer cool);

    /**
     * Verifica se o somatório da avaliação é > 0
     * @return
     */
    public boolean isUsefull();
    /**
     * Devolve a data do Review
     * @return Data
     */
    public LocalDateTime getDate();

    /**
     * Define a data do Review
     * @param name Nova data do Review
     */
    public void setDate(LocalDateTime date);

    /**
     * Devolve o texto da Review
     * @return Texto
     */
    public String getText();

    /**
     * Define o texto do Review
     * @param name Novo texto do Review
     */
    public void setText(String text);

    /**
     * Adiciona um novo amigo ao Review
     * @param friend Novo amigo do Review
     */
    public boolean equals(Object obj);

    /**
     * Verifica a igualdade do Review com outro objeto
     * Verificação é feita comparando cada uma das variáveis de instância
     * @param o Objeto a ser comparado
     * @return boolean true se iguais; false caso contrário
     */
    public IReview clone();

}
