package am.itspace.companycmployeespring.service;

import am.itspace.companycmployeespring.entity.Company;
import am.itspace.companycmployeespring.entity.Employee;
import am.itspace.companycmployeespring.repository.CompanyRepository;
import am.itspace.companycmployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static Integer COUNT = 1;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Value("${company.employee.spring.images.folder}")
    private String folderPath;

    public Page<Employee> findAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public void saveEmployee(Employee employee, MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setProfilePic(fileName);
        }
        Company company = companyRepository.getReferenceById(employee.getCompany().getId());
        company.setSize(company.getSize() + COUNT);
        employeeRepository.save(employee);
    }

    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id).get();
        employee.getCompany().setSize(employee.getCompany().getSize() - COUNT);
        employeeRepository.deleteById(id);
    }

    public byte[] getImage(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }
}
