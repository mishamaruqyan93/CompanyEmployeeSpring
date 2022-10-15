package am.itspace.companycmployeespring.service;

import am.itspace.companycmployeespring.entity.Company;
import am.itspace.companycmployeespring.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Page<Company> findAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public void saveCompany(Company company) {
        if (company != null) {
            companyRepository.save(company);
        }
    }

    public void deleteCompany(int id) {
        companyRepository.deleteById(id);
    }
}
