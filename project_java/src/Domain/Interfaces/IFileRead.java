package Domain.Interfaces;

import java.time.LocalDateTime;
import java.util.List;

public interface IFileRead {

    public LocalDateTime getReadDate();

    public boolean hasFileName(String filename);

    public List<String> getFileName();

    public void setFileName(Iterable<String> fileName);

    public void addFileName(String filename);

    public Integer getWrongRev();

    public void setWrongRev(Integer wrongRev);

    public void addWrongRev();

    public void addRev();

    public Integer getTotalBus();

    public void setTotalBus(Integer totalBus);

    public void addBus();

    public Integer getTotalRevBus();

    public void setTotalRevBus(Integer totalRevBus);

    public void addRevBus();

    public Integer getTotalNotRevBus();

    public void setTotalNotRevBus(Integer totalNotRevBus);

    public void addNotRevBus();

    public Integer getTotalUser();

    public void setTotalUser(Integer totalUser);

    public void addUser();

    public Integer getTotalUserWRev();

    public void setTotalUserWRev(Integer totalUserWRev);

    public void addUserWRev();

    public Integer getTotalUserNRev();

    public void setTotalUserNRev(Integer totalUserNRev);

    public void addUserNRev();

    public Integer getTotalUserWrong();

    public void addUserWrong();

    public Integer getTotalReviews();

    public void setTotalReviews(Integer totalReviews);

    public void addReview();

    public Integer getUselessReviews();

    public void setUselessReviews(Integer uselessReviews);

    public void addUselessReview();

    public IFileRead clone();

    public boolean equals(Object obj);

    public String toString();
}
