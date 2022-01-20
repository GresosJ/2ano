package Services.Repositories;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import Domain.Interfaces.IBusiness;
import Domain.Interfaces.IClassification;
import Domain.Interfaces.IFileRead;
import Domain.Interfaces.IFilesRead;
import Domain.Interfaces.IKeyValueSet;
import Domain.Interfaces.IReview;
import Domain.Interfaces.IUser;
import Domain.Model.Classification;
import Domain.Model.FilesRead;
import Domain.Model.KeyValueSet;
import Domain.Model.KeyValueSetKeyComparator;
import Middleware.CityNotFoundException;
import Middleware.StateNotFoundException;
import Middleware.Domain.BusinessAlreadyExistsException;
import Middleware.Domain.BusinessNotFoundException;
import Middleware.Domain.MonthIsNotValidException;
import Middleware.Domain.ReviewAlreadyExistsException;
import Middleware.Domain.UserAlreadyExistsException;
import Middleware.Domain.UserNotFoundException;
import Middleware.Domain.YearIsNotValidException;
import Services.Interfaces.IBusStats;
import Services.Interfaces.IStatsRepository;
import Services.Interfaces.IUserStats;

public class StatsRepository implements IStatsRepository, Serializable {

    private IFilesRead filesRead;
    private Map<String, IBusStats> busStatsById;
    private Map<String, IUserStats> userStatsById;
    private Map<String, Set<String>> cityBusiness;
    private Map<String, Set<String>> stateCities;
    private Map<Integer, Set<IKeyValueSet<Integer, String>>> revByYear;
    private Set<Entry<Integer, IClassification>> starsByMonth;
    private Map<Integer, Map<String, IBusStats>> classBusByYear;
    private IClassification starsGlobal;
    private Integer totalReviews, totalUsers, totalBus;
    private Integer uselessReviews, wrongReviews;

    public StatsRepository() {
        this.filesRead = new FilesRead();
        this.busStatsById = new HashMap<>();
        this.userStatsById = new HashMap<>();
        this.cityBusiness = new HashMap<>();
        this.stateCities = new HashMap<>();
        this.revByYear = new HashMap<>();
        this.starsByMonth = new TreeSet<>(new EntryClassificationComparator());
        this.starsGlobal = new Classification();
        this.classBusByYear = new HashMap<>();
        this.totalReviews = 0;
        this.totalUsers = 0;
        this.totalBus = 0;
        this.uselessReviews = 0;
        this.wrongReviews = 0;
    }

    @Override
    public void addFileRead(IFileRead fr) {
        filesRead.addFileRead(fr);
    }

    @Override
    public IFileRead getFileRead(String filename) throws FileNotFoundException {
        return filesRead.getFileRead(filename);
    }

    @Override
    public List<IFileRead> getFilesRead() {
        return filesRead.getFilesRead();
    }

    @Override
    public IFileRead getFileRead() throws NoSuchElementException {
        List<IFileRead> fr = this.filesRead.getFilesRead();
        if (fr.size() == 0)
            throw new NoSuchElementException("Ainda não foram lidos ficheiros!");
        return fr.get(fr.size() - 1);
    }

    private void addRevYearClass(IReview r) {
        Integer yr = r.getDate().getYear();
        Map<String, IBusStats> clmap = this.classBusByYear.get(yr);
        if (clmap == null) {
            clmap = new HashMap<>();
            this.classBusByYear.put(yr, clmap);
        }

        IBusStats bs = clmap.get(r.getBusID());
        if (bs == null) {
            bs = new BusStats(r.getBusID());
            clmap.put(r.getBusID(), bs);
        }
        try {
            bs.addReview(r);
        } catch (ReviewAlreadyExistsException e) {
            // ignora
        }
    }

    private void addMonthStats(IReview r) {
        Entry<Integer, IClassification> kvs;

        try {
            kvs = starsByMonth.stream().filter(x -> x.getKey().compareTo(r.getDate().getMonthValue()) == 0).findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            kvs = new SimpleEntry<>(r.getDate().getMonthValue(), new Classification());
            starsByMonth.add(kvs);
        }

        kvs.getValue().addClassification(r);
    }

    private void addRevByDate(IReview r) {
        LocalDateTime ldt = r.getDate();

        Set<IKeyValueSet<Integer, String>> ano = revByYear.get(ldt.getYear());

        if (ano == null) {
            revByYear.put(ldt.getYear(), new TreeSet<>(new KeyValueSetKeyComparator<>()));
        }
        ano = revByYear.get(ldt.getYear());
        IKeyValueSet<Integer, String> mes;
        try {
            mes = ano.stream().filter(x -> x.getKey().compareTo(ldt.getMonthValue()) == 0).findFirst().get();
        } catch (NoSuchElementException e) {
            ano.add(new KeyValueSet<Integer, String>(ldt.getMonthValue()));
            mes = ano.stream().filter(x -> x.getKey().compareTo(ldt.getMonthValue()) == 0).findFirst().get();
        }

        mes.addValue(r.getReviewID());

    }

    private void addCityState(String city, String state) {
        if (!stateCities.containsKey(state)) {
            stateCities.put(state, new TreeSet<>());
        }
        stateCities.get(state).add(city);
    }

    private void addCityBusiness(String city, String busId) {
        if (!cityBusiness.containsKey(city)) {
            cityBusiness.put(city, new TreeSet<>());
        }
        cityBusiness.get(city).add(busId);

    }

    @Override
    public void addStatsUser(IUser u) throws UserAlreadyExistsException {
        if (userStatsById.containsKey(u.getId())) {
            throw new UserAlreadyExistsException("User já existe: " + u.toString());
        }

        userStatsById.put(u.getId(), new UserStats(u.getId()));
        addTotalUsers();
    }

    @Override
    public void addStatsBus(IBusiness b) throws BusinessAlreadyExistsException {
        if (busStatsById.containsKey(b.getId()))
            throw new BusinessAlreadyExistsException("O utilizador" + b.toString() + "já existe");

        busStatsById.put(b.getId(), new BusStats(b.getId()));
        addCityState(b.getCity(), b.getState());
        addCityBusiness(b.getCity(), b.getId());
        addTotalBus();
    }

    @Override
    public void addStatsReview(IReview r)
            throws UserNotFoundException, BusinessNotFoundException, ReviewAlreadyExistsException {
        IUserStats us = userStatsById.get(r.getUserID());
        if (us == null) {
            this.addWrongReview();
            throw new UserNotFoundException("O utilizador não existe. A review é falsa!");
        }

        IBusStats bs = busStatsById.get(r.getBusID());

        if (bs == null) {
            this.addWrongReview();
            throw new BusinessNotFoundException("A business não existe. Review falsa!");
        }

        bs.addReview(r);
        us.addReview(r);
        addRevByDate(r);
        addMonthStats(r);
        starsGlobal.addClassification(r);
        addRevYearClass(r);
        if (!r.isUsefull())
            this.addUselessReview();
        addTotalReviews();
    }

    @Override
    public IBusStats getBusStats(String busId) throws BusinessNotFoundException {
        try {
            return this.busStatsById.get(busId).clone();
        } catch (NullPointerException e) {
            throw new BusinessNotFoundException("Business não encontrado: " + busId + "\n");
        }
    }

    @Override
    public IUserStats getUserStats(String userId) throws UserNotFoundException {
        try {
            return this.userStatsById.get(userId).clone();
        } catch (NullPointerException e) {
            throw new UserNotFoundException("O utilizador não foi encontrado: " + userId);
        }
    }

    @Override
    public Set<String> getUserReviews(String userId) throws UserNotFoundException {
        try {
            return userStatsById.get(userId).getReviews();
        } catch (NullPointerException e) {
            throw new UserNotFoundException("O user com id" + userId + "não foi encontrado");
        }
    }

    @Override
    public Set<String> getUserReviews(IUser user) throws UserNotFoundException {
        return getUserReviews(user.getId());
    }

    @Override
    public List<String> getUserBus(String userId) throws UserNotFoundException {
        try {
            return userStatsById.get(userId).getBusiness();
        } catch (NullPointerException e) {
            throw new UserNotFoundException("O user com id" + userId + "não foi encontrado");
        }
    }

    @Override
    public List<String> getUserBus(IUser user) throws UserNotFoundException {
        return getUserBus(user.getId());
    }

    @Override
    public List<SimpleEntry<String, Integer>> getUserBusTuple(String userId) throws UserNotFoundException {
        try {
            return userStatsById.get(userId).getBusCounter();
        } catch (NullPointerException e) {
            throw new UserNotFoundException("User com id " + userId + " não existe");
        }
    }

    @Override
    public Set<String> getBusReviews(String busId) throws BusinessNotFoundException {
        try {
            return busStatsById.get(busId).getReviews();
        } catch (NullPointerException e) {
            throw new BusinessNotFoundException("O business com id" + busId + "não foi encontrado");
        }
    }

    @Override
    public Set<String> getBusReviews(IBusiness bus) throws BusinessNotFoundException {
        return getBusReviews(bus.getId());
    }

    @Override
    public List<String> getBusUsers(String busId) throws BusinessNotFoundException {
        try {
            return busStatsById.get(busId).getUsers();
        } catch (NullPointerException e) {
            throw new BusinessNotFoundException("O business com id" + busId + "não foi encontrado");
        }
    }

    @Override
    public List<String> getBusUsers(IBusiness bus) throws BusinessNotFoundException {
        return getBusUsers(bus.getId());
    }

    @Override
    public List<SimpleEntry<String, Integer>> getBusUsersTuple(String busId) throws BusinessNotFoundException {
        try {
            return userStatsById.get(busId).getBusCounter();
        } catch (NullPointerException e) {
            throw new BusinessNotFoundException("User com id " + busId + " não existe");
        }
    }

    @Override
    public List<String> getCities(String state) throws StateNotFoundException {
        if (!stateCities.containsKey(state)) {
            throw new StateNotFoundException("O estado " + state + " não existe.");
        }
        return stateCities.get(state).stream().collect(Collectors.toList());
    }

    @Override
    public List<IKeyValueSet<String, String>> getStateCities() {
        return stateCities.entrySet().stream().map(x -> new KeyValueSet<>(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getCityBusiness(String city) throws CityNotFoundException {
        if (!cityBusiness.containsKey(city)) {
            throw new CityNotFoundException("O estado " + city + " não existe.");
        }
        return cityBusiness.get(city).stream().collect(Collectors.toList());
    }

    @Override
    public Set<IKeyValueSet<Integer, String>> getReviewsByYear() {
        Set<IKeyValueSet<Integer, String>> new_return = new TreeSet<>(new KeyValueSetKeyComparator<>());
        for (Map.Entry<Integer, Set<IKeyValueSet<Integer, String>>> iteraAno : this.revByYear.entrySet()) {
            IKeyValueSet<Integer, String> ano = new KeyValueSet<>(iteraAno.getKey());
            for (IKeyValueSet<Integer, String> iteraMes : iteraAno.getValue()) {
                ano.addValue(iteraMes.getValues());
            }
            new_return.add(ano);
        }
        return new_return;
    }

    @Override
    public List<String> getReviewsYearMonth(Integer year, Integer month)
            throws YearIsNotValidException, MonthIsNotValidException {
        Set<IKeyValueSet<Integer, String>> ks = this.revByYear.get(year);
        if (ks == null)
            throw new YearIsNotValidException("O ano que procura não existe");
        try {
            return ks.stream().filter(x -> x.getKey().compareTo(month) == 0).findFirst().get().getValues();
        } catch (NoSuchElementException e) {
            throw new MonthIsNotValidException("O mês que procura não existe");
        }
    }

    @Override
    public List<IKeyValueSet<String, String>> getCityBusiness() {
        Function<Entry<String, Set<String>>, KeyValueSet<String, String>> f = x -> new KeyValueSet<>(x.getKey(),
                x.getValue());
        return cityBusiness.entrySet().stream().map(f).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<IBusStats>> getStatsbyYear(Integer n) {
        return this.classBusByYear.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey,
                        x -> x.getValue().values().stream()
                                .sorted((y1, y2) -> y2.getTotalReviews().compareTo(y1.getTotalReviews())).limit(n)
                                .collect(Collectors.toList())));
    }

    @Override
    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void addTotalReviews() {
        this.totalReviews++;
    }

    @Override
    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void addTotalUsers() {
        this.totalUsers++;
    }

    @Override
    public Integer getTotalBus() {
        return totalBus;
    }

    public void addTotalBus() {
        this.totalBus++;
    }

    @Override
    public Integer getUselessReviews() {
        return uselessReviews;
    }

    public void addUselessReview() {
        this.uselessReviews++;
    }

    @Override
    public Integer getWrongReviews() {
        return wrongReviews;
    }

    public void addWrongReview() {
        this.wrongReviews++;
    }

    @Override
    public IClassification getGlobalStats() {
        return this.starsGlobal.clone();
    }

    @Override
    public Set<Entry<Integer, IClassification>> getStatsByMonth() {
        return this.starsByMonth.stream().map(x -> new SimpleEntry<>(x.getKey(), x.getValue().clone()))
                .collect(Collectors.toSet());
    }

    @Override
    public IClassification getStatsByMonth(Integer month) throws MonthIsNotValidException {
        try {
            return this.starsByMonth.stream().filter(x -> x.getKey().compareTo(month) == 0).findFirst().get().getValue()
                    .clone();
        } catch (NoSuchElementException e) {
            throw new MonthIsNotValidException("O mês que procura não tem estatísticas associadas!");
        }
    }

    @Override
    public Map<String, IClassification> getClassfCities() {

        return this.cityBusiness.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, x -> x.getValue().stream().map(t -> {
                    try {
                        return getBusStats(t).getClassification();
                    } catch (BusinessNotFoundException e) {
                        return null;
                    }
                }).filter(y -> y != null).reduce(new Classification(), (acc, cur) -> acc.addClassification(cur))));
    }

    @Override
    public Map<String, Map<String, IClassification>> getClassfStates() {
        Map<String, IClassification> citClass = getClassfCities();
        return stateCities.entrySet().stream().collect(Collectors.toMap(Entry::getKey,
                x -> x.getValue().stream().collect(Collectors.toMap(y -> y, y -> citClass.get(y)))));

    }

    public <T> List<T> iterateBusStats(Function<IBusStats, T> f) {
        return this.busStatsById.values().stream().map(f).collect(Collectors.toList());
    }

    @Override
    public <T> List<T> iterateUsersStats(Function<IUserStats, T> f) {
        return this.userStatsById.values().stream().map(f).collect(Collectors.toList());
    }

    @Override
    public List<IBusStats> iterateBusStats(Predicate<IBusStats> f) {
        return this.busStatsById.values().stream().filter(f).collect(Collectors.toList());
    }

    @Override
    public List<IUserStats> iterateUsersStats(Predicate<IUserStats> f) {
        return this.userStatsById.values().stream().filter(f).collect(Collectors.toList());
    }

    @Override
    public List<IBusStats> iterateBusStats(Comparator<IBusStats> f) {
        return this.busStatsById.values().stream().sorted(f).collect(Collectors.toList());
    }

    @Override
    public List<IUserStats> iterateUsersStats(Comparator<IUserStats> f) {
        return this.userStatsById.values().stream().sorted(f).collect(Collectors.toList());
    }

}
