package Domain.Model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import Domain.Interfaces.IFileRead;
import Domain.Interfaces.IFilesRead;

public class FilesRead implements IFilesRead, Serializable {

    private List<IFileRead> filesRead;

    public FilesRead() {
        this.filesRead = new ArrayList<>();
    }

    public FilesRead(List<IFileRead> filesRead) {
        this.filesRead = filesRead.stream().map(IFileRead::clone).collect(Collectors.toList());
    }

    public FilesRead(FilesRead fr) {
        this(fr.getFilesRead());
    }

    @Override
    public List<IFileRead> getFilesRead() {
        return this.filesRead.stream().map(IFileRead::clone).collect(Collectors.toList());
    }

    @Override
    public IFileRead getFileRead(String filename) throws FileNotFoundException {
        try {
            return this.filesRead.stream().filter(x -> x.hasFileName(filename)).findFirst().get().clone();
        } catch (NoSuchElementException e) {
            throw new FileNotFoundException("O ficheiro procurado não existe.");
        }
    }

    @Override
    public void addFileRead(IFileRead fr) {
        this.filesRead.add(fr.clone());
    }

    @Override
    public Integer getTotalReviews() {
        return this.filesRead.stream().mapToInt(IFileRead::getTotalReviews).sum();
    }

    @Override
    public Integer getTotalBus() {
        return this.filesRead.stream().mapToInt(IFileRead::getTotalBus).sum();
    }

    @Override
    public Integer getTotalUsers() {
        return this.filesRead.stream().mapToInt(IFileRead::getTotalUser).sum();
    }

    @Override
    public IFilesRead clone() {
        return new FilesRead(this);
    }
}
