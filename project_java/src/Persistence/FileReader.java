package Persistence;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class FileReader <T>  implements Serializable {
    private String filename;

    public FileReader(String filename) {
        this.filename = filename;
    }

    /**
     * Lê todas as linhas do ficheiro e retorna-as (remove a primeira - cabeçalho)
     * 
     * @return Linhas do ficheiro
     */
    public List<String> readLines()  {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(this.filename), StandardCharsets.UTF_8);
            lines.remove(0);
        } catch (IOException e) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    /**
     * Lê todo o ficheiro e converte para uma lista de T, utilizando a função que
     * recebe como argumento
     * 
     * @param f Função de transformação entre String e T
     * @return Lista de T
     */
    public List<T> readLinesConverted(Function<String, T> f) {
        List<T> converted;
        try {
            converted = Files.readAllLines(Paths.get(this.filename), StandardCharsets.UTF_8).stream().map(f).collect(Collectors.toList());
            converted.remove(0);
        } catch (IOException e) {
            converted = new ArrayList<>();
        }
        return converted;
    }
}
