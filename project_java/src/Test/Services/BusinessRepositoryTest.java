package Test.Services;

import Services.Interfaces.IBusinessRepository;
import Services.Repositories.BusinessRepository;


public class BusinessRepositoryTest {
    private IBusinessRepository br;

    public BusinessRepositoryTest() {
        this.br = new BusinessRepository();
    }
}
