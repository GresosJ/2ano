package GestReviews.Queries;

import java.util.ArrayList;
import java.util.List;

import Services.Interfaces.IUserStats;

public class UserMostBusDTO implements IResult {
    private Integer top;
    private List<IUserStats> busList;

    public UserMostBusDTO(Integer top) {
        this.busList = new ArrayList<>();
        this.top = top;
    }

    public void add(IUserStats b) {
        this.busList.add(b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(top + " utilizadores com mais negócios avaliados:\n");
        busList.stream().forEach(x -> sb.append(x.getId() + " - " + x.getTotalBus() + "\n"));
        sb.append("\n");
        return sb.toString();
    }

}
