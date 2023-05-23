package org.hakman;

import java.util.List;

public class CompanyService implements ICompanyService {
    @Override
    public Company getTopLevelParent(Company child) {
        return child.getParent() == null ? child : getTopLevelParent(child.getParent());
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        if (companies == null || companies.size() == 0) {
            return company.getEmployeesCount();
        }

        var children = companies.stream()
                .filter(comp -> comp.getParent() == company).toList();
        var childrenEmployeeCount = children.stream()
                .mapToLong(Company::getEmployeesCount)
                .sum();

        for (Company comp : children) {
            childrenEmployeeCount += getEmployeeCountForCompanyAndChildren(comp, companies) - comp.getEmployeesCount();
        }

        return company.getEmployeesCount() + childrenEmployeeCount;
    }
}
