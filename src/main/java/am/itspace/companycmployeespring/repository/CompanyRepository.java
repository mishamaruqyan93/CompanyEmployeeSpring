package am.itspace.companycmployeespring.repository;

import am.itspace.companycmployeespring.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
