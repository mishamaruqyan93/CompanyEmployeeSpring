package am.itspace.companycmployeespring.repository;

import am.itspace.companycmployeespring.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
