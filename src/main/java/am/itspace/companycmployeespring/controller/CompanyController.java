package am.itspace.companycmployeespring.controller;

import am.itspace.companycmployeespring.entity.Company;
import am.itspace.companycmployeespring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/company")
    public String company(@RequestParam("page") Optional<Integer> page,
                          ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = 5;
        Page<Company> companies = companyService.findAllCompany(PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("company", companies);
        int totalPages = companies.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "company";
    }

    @GetMapping("/company/add")
    public String addCompany() {
        return "addCompany";
    }

    @PostMapping("/company/add")
    public String add(@ModelAttribute Company company) {
        companyService.saveCompany(company);
        return "redirect:/company";
    }

    @GetMapping("/company/delete")
    public String delete(@RequestParam("id") int id) {
        companyService.deleteCompany(id);
        return "redirect:/company";
    }
}
