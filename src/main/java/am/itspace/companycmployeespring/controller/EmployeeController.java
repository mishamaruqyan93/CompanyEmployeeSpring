package am.itspace.companycmployeespring.controller;

import am.itspace.companycmployeespring.entity.Company;
import am.itspace.companycmployeespring.entity.Employee;
import am.itspace.companycmployeespring.repository.CompanyRepository;
import am.itspace.companycmployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class EmployeeController {
    private static Integer COUNT = 1;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Value("${company.employee.spring.images.folder}")
    private String folderPath;


    @GetMapping("/employee")
    public String employee(ModelMap modelMap) {
        List<Employee> employees = employeeRepository.findAll();
        modelMap.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employee/add")
    public String addEmployeePage(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "addEmployee";
    }

    @PostMapping("/employee/add")
    public String addTask(@ModelAttribute Employee employee, @RequestParam("employeeImage") MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setProfilePic(fileName);
        }
        Company company = companyRepository.getReferenceById(employee.getCompany().getId());
        company.setSize(company.getSize() + COUNT);
        employeeRepository.save(employee);
        return "redirect:/employee";
    }


    @GetMapping("/employee/delete")
    public String delete(@RequestParam("id") int id) {
        Employee employee = employeeRepository.findById(id).get();
        employee.getCompany().setSize(employee.getCompany().getSize() - COUNT);
        employeeRepository.deleteById(id);
        return "redirect:/employee";
    }


    @GetMapping(value = "/employees/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }
}