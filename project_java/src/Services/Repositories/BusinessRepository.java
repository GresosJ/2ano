package Services.Repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Domain.Interfaces.IBusiness;
import Middleware.Domain.BusinessAlreadyExistsException;
import Middleware.Domain.BusinessNotFoundException;
import Services.Interfaces.IBusinessRepository;

public class BusinessRepository implements IBusinessRepository, Serializable {
    public Map<String, IBusiness> business;

    public BusinessRepository() {
        this.business = new HashMap<>();
    }

    public BusinessRepository(BusinessRepository br) {
        this();
        try {
            addBusiness(br.getAllBusiness());
        } catch (BusinessAlreadyExistsException e) {
            // unhandled exception
            // nunca vai acontecer pq o próprio repositório já teve esta exception
            // tratada
        }
    }

    public void addBusiness(IBusiness b) throws BusinessAlreadyExistsException {
        if (this.business.containsKey(b.getId())) {
            throw new BusinessAlreadyExistsException("Business já existe. Não foi substituído.");
        } else {
            this.business.put(b.getId(), b.clone());
        }
    }

    public void addBusiness(List<IBusiness> lbus) throws BusinessAlreadyExistsException {
        List<IBusiness> lb = new ArrayList<>();

        for (IBusiness business : lbus) {
            try {
                addBusiness(business);
            } catch (BusinessAlreadyExistsException e) {
                lb.add(business);
            }
        }
        if (lb.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Os business com id: ");
            for (IBusiness business : lb) {
                sb.append(business.getId() + ";");
            }
            sb.append("já existiam no repositório.\n");
            throw new BusinessAlreadyExistsException(sb.toString());
        }
    }

    @Override
    public IBusiness getBusiness(String busId) throws BusinessNotFoundException {
        IBusiness b = this.business.get(busId);
        if (b == null)
            throw new BusinessNotFoundException(busId + " não existe.");

        return b.clone();
    }

    @Override
    public IBusiness getBusiness(IBusiness b) throws BusinessNotFoundException {
        try {
            return this.business.get(b.getId());
        } catch (NullPointerException e) {
            throw new BusinessNotFoundException("Business com id " + b.getId() + "não encontrado.");
        }
    }

    @Override
    public List<IBusiness> getAllBusiness() {
        return this.business.values().stream().map(IBusiness::clone).collect(Collectors.toList());
    }

    @Override
    public List<IBusiness> getAllBusiness(Predicate<IBusiness> cp) {
        return this.business.values().stream().filter(cp).map(IBusiness::clone).collect(Collectors.toList());
    }

    @Override
    public void removeBusiness(String busId) throws BusinessNotFoundException {
        try {
            this.business.remove(busId);
        } catch (NullPointerException np) {
            throw new BusinessNotFoundException("Business " + busId + " não existe");
        }

    }

    @Override
    public void removeBusiness(IBusiness ibus) throws BusinessNotFoundException {
        this.removeBusiness(ibus.getId());

    }

    @Override
    public BusinessRepository clone() {
        return new BusinessRepository(this);
    }

}
