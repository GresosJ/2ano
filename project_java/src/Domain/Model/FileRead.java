package Domain.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Domain.Interfaces.IFileRead;
import GestReviews.Queries.IResult;

public class FileRead implements IFileRead, Serializable, IResult {

    private List<String> fileName;
    private LocalDateTime readDate;
    private Integer wrongRev; // total reviews errados
    private Integer totalBus; // total negócios
    private Integer totalRevBus; // total diferentes negócios avaliados
    private Integer totalNotRevBus; // total negócios não avaliados
    private Integer totalUser; // total utilizadores
    private Integer totalUserWRev; // total users com reviews
    private Integer totalUserNRev; // total users sem reviews / inativos
    private Integer totalWrongUsers;
    private Integer totalReviews; // total reviews
    private Integer uselessReviews; // total reviews sem impacto

    /**
     * 
     */
    public FileRead() {
        this.fileName = new ArrayList<>();
        this.readDate = LocalDateTime.now();
        this.wrongRev = 0;
        this.totalBus = 0;
        this.totalRevBus = 0;
        this.totalNotRevBus = 0;
        this.totalUser = 0;
        this.totalUserWRev = 0;
        this.totalUserNRev = 0;
        this.totalReviews = 0;
        this.uselessReviews = 0;
    }

    /**
     * 
     * @param fileName
     */
    public FileRead(String fileName) {
        this();
        this.fileName.add(fileName);
    }

    public FileRead(Iterable<String> files) {
        this();
        this.setFileName(files);
    }

    /**
     * 
     * @param fileName
     * @param wrongRev
     * @param totalBus
     * @param totalRevBus
     * @param totalNotRevBus
     * @param totalUser
     * @param totalUserWRev
     * @param totalUserNRev
     * @param totalReviews
     * @param uselessReviews
     */
    public FileRead(List<String> fileName, Integer wrongRev, Integer totalBus, Integer totalRevBus,
            Integer totalNotRevBus, Integer totalUser, Integer totalUserWRev, Integer totalUserNRev,
            Integer totalReviews, Integer uselessReviews) {
        this();
        this.setFileName(fileName);
        this.wrongRev = wrongRev;
        this.totalBus = totalBus;
        this.totalRevBus = totalRevBus;
        this.totalNotRevBus = totalNotRevBus;
        this.totalUser = totalUser;
        this.totalUserWRev = totalUserWRev;
        this.totalUserNRev = totalUserNRev;
        this.totalReviews = totalReviews;
        this.uselessReviews = uselessReviews;
    }

    public FileRead(FileRead fr) {
        this(fr.getFileName(), fr.getWrongRev(), fr.getTotalBus(), fr.getTotalRevBus(), fr.getTotalNotRevBus(),
                fr.getTotalUser(), fr.getTotalUserWRev(), fr.getTotalUserNRev(), fr.getTotalReviews(),
                fr.getUselessReviews());
    }

    public List<String> getFileName() {
        return fileName;
    }

    public void setFileName(Iterable<String> fileName) {
        for (String file : fileName) {
            this.fileName.add(file);
        }

    }

    @Override
    public LocalDateTime getReadDate() {
        return this.readDate;
    }

    @Override
    public boolean hasFileName(String filename) {
        return this.fileName.stream().anyMatch(x -> x.equals(filename));
    }

    @Override
    public void addFileName(String filename) {
        this.fileName.add(filename);
    }

    public Integer getWrongRev() {
        return wrongRev;
    }

    public void setWrongRev(Integer wrongRev) {
        this.wrongRev = wrongRev;
    }

    public void addWrongRev() {
        this.wrongRev++;
    }

    public void addRev() {
        this.wrongRev++;
    }

    public Integer getTotalBus() {
        return totalBus;
    }

    public void setTotalBus(Integer totalBus) {
        this.totalBus = totalBus;
    }

    public void addBus() {
        this.totalBus++;
    }

    public Integer getTotalRevBus() {
        return totalRevBus;
    }

    public void setTotalRevBus(Integer totalRevBus) {
        this.totalRevBus = totalRevBus;
    }

    public void addRevBus() {
        this.totalRevBus++;
    }

    public Integer getTotalNotRevBus() {
        return totalNotRevBus;
    }

    public void setTotalNotRevBus(Integer totalNotRevBus) {
        this.totalNotRevBus = totalNotRevBus;
    }

    public void addNotRevBus() {
        this.totalNotRevBus++;
    }

    public Integer getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Integer totalUser) {
        this.totalUser = totalUser;
    }

    public void addUser() {
        this.totalUser++;
    }

    public Integer getTotalUserWRev() {
        return totalUserWRev;
    }

    public void setTotalUserWRev(Integer totalUserWRev) {
        this.totalUserWRev = totalUserWRev;
    }

    public void addUserWRev() {
        this.totalUserWRev++;
    }

    public Integer getTotalUserNRev() {
        return totalUserNRev;
    }

    public void setTotalUserNRev(Integer totalUserNRev) {
        this.totalUserNRev = totalUserNRev;
    }

    public void addUserNRev() {
        this.totalUserNRev++;
    }

    public Integer getTotalUserWrong() {
        return totalWrongUsers;
    }

    public void setTotalUserWrong(Integer totalWrongUsers) {
        this.totalWrongUsers = totalWrongUsers;
    }

    public void addUserWrong() {
        this.totalWrongUsers++;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public void addReview() {
        this.totalReviews++;
    }

    public Integer getUselessReviews() {
        return uselessReviews;
    }

    public void setUselessReviews(Integer uselessReviews) {
        this.uselessReviews = uselessReviews;
    }

    public void addUselessReview() {
        this.uselessReviews++;
    }

    @Override
    public IFileRead clone() {
        return new FileRead(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if ((obj == null) || !obj.getClass().getSimpleName().equals(this.getClass().getSimpleName()))
            return false;

        FileRead fr = (FileRead) obj;

        return this.getFileName().equals(fr.getFileName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome do ficheiro: " + this.getFileName() + "\n");
        sb.append("Total reviews errados: " + this.getWrongRev() + "\n");
        sb.append("Total business: " + this.getTotalBus() + "\n");
        sb.append("Total negócios avaliados: " + this.getTotalRevBus() + "\n");
        sb.append("Total negócios não avaliados: " + this.getTotalNotRevBus() + "\n");
        sb.append("Total users: " + this.getTotalUser() + "\n");
        sb.append("Total users com reviews: " + this.getTotalUserWRev() + "\n");
        sb.append("Total users sem reviews: " + this.getTotalUserNRev() + "\n");
        sb.append("Total reviews: " + this.getTotalReviews() + "\n");
        sb.append("Total reviews sem impacto: " + this.getUselessReviews() + "\n");
        return sb.toString();
    }
}
