import org.hakman.Company;
import org.hakman.CompanyService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CompanyServiceTest {
    CompanyService underTest = new CompanyService();
    Company single = new Company(null, 5);
    Company root = new Company(null, 7);
    Company accounting = new Company(root, 3);
    Company development = new Company(root, 11);
    Company frontEnd = new Company(development, 5);
    Company graphDesign = new Company(frontEnd, 3);
    Company backend = new Company(development, 3);
    Company bd = new Company(development, 3);
    Company devops = new Company(development, 3);
    List<Company> companies = List.of(new Company[] {
            root,
            accounting,
            development,
            frontEnd,
            graphDesign,
            backend,
            bd,
            devops
    });

    @Test
    public void whenCompanyIsSingleThenParentisTheSameCompany() {
        Company result = underTest.getTopLevelParent(single);

        assertEquals(result, single);
    }

    @Test
    public void whenCompanyIsRootThenParentisTheSameCompany() {
        Company result = underTest.getTopLevelParent(root);

        assertEquals(result, root);
    }

    @Test
    public void whenCompanyInChainBottomThenTopIsCompanyWithParentNull() {
        Company result = underTest.getTopLevelParent(accounting);

        assertEquals(result, root);
    }

    @Test
    public void whenCompanyInChainLowBottomThenTopIsCompanyWithParentNull() {
        Company result = underTest.getTopLevelParent(graphDesign);

        assertEquals(result, root);
    }

    @Test
    public void whenCompanyIsSingleThenEmployeesCountIsOwn() {
        long result = underTest.getEmployeeCountForCompanyAndChildren(single, new ArrayList<>());

        assertEquals(result, 5);
    }

    @Test
    public void whenCompanyIsRootThenEmployeesCountIsAll() {
        long result = underTest.getEmployeeCountForCompanyAndChildren(root, companies);

        assertEquals(result, 38);
    }

    @Test
    public void whenCompanyInChainBottomThenEmployeesCountIsOwn() {
        long result = underTest.getEmployeeCountForCompanyAndChildren(accounting, companies);

        assertEquals(result, 3);
    }

    @Test
    public void whenCompanyInChainMiddleThenEmployeesCountIsOwnAndChildrens() {
        long result = underTest.getEmployeeCountForCompanyAndChildren(development, companies);

        assertEquals(result, 28);
    }

    @Test
    public void otherTest() {
        long result = underTest.getEmployeeCountForCompanyAndChildren(devops, companies);

        assertEquals(result, 3);
    }
}
