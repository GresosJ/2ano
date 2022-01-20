package Domain.Interfaces;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public interface IFilesRead {

    /**
     * Retorna a lista das informações de todos os ficheiros lidos até agora 
     *
     * @return Lista de IFileRead
     */
    public List<IFileRead> getFilesRead();

    /**
     * Retorna as informações de um ficheiro, caso tenha sido lido 
     *
     * @param filename Ficheiro a procurar
     * @return IFileRead com as suas informações
     * @throws FileNotFoundException Caso o ficheiro não exista
     */
    public IFileRead getFileRead(String filename) throws FileNotFoundException;

    /**
     * Adiciona a info de um ficheiro à lista de ficheiros lidos 
     *
     * @param fr Ficheiro a adicionar
     * @throws FileAlreadyExistsException Caso o ficheiro já tenha sido lido
     */
    public void addFileRead(IFileRead fr);

    /**
     * Retorna o número total de reviews, sobre todos os ficheiros
     *
     * @return Total de reviews
     */
    public Integer getTotalReviews();

    /**
     * Retorna o número total de negócios, sobre todos os ficheiros

     * @return Total de negócios
     */
    public Integer getTotalBus();

    /**
     * Retorna o número total de Users, sobre todos os ficheiros

     * @return Total de Users
     */
    public Integer getTotalUsers();

    /**
     * Cria um clone de FilesRead

     * @return clone criado
     */
    public IFilesRead clone();
}
