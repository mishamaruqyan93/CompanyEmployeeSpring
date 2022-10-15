package am.itspace.companycmployeespring.controller;

import am.itspace.companycmployeespring.entity.Company;
import am.itspace.companycmployeespring.entity.Employee;
import am.itspace.companycmployeespring.service.CompanyService;
import am.itspace.companycmployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @GetMapping("/employee")
    public String employee(@RequestParam("page") Optional<Integer> page,
                           ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = 5;
        Page<Employee> employees = employeeService.findAllEmployees(PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("employees", employees);
        int totalPages = employees.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "employees";
    }

    @GetMapping("/employee/add")
    public String addEmployeePage(ModelMap modelMap) {
        List<Company> companies = companyService.findAll();
        modelMap.addAttribute("companies", companies);
        return "addEmployee";
    }

    @PostMapping("/employee/add")
    public String addTask(@ModelAttribute Employee employee, @RequestParam("employeeImage") MultipartFile file) throws IOException {
        employeeService.saveEmployee(employee, file);
        return "redirect:/employee";
    }

    @GetMapping("/employee/delete")
    public String delete(@RequestParam("id") int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee";
    }

    @GetMapping(value = "/employees/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        return employeeService.getImage(fileName);
    }
}