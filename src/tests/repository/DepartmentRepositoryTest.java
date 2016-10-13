package tests.repository;

import domain.Candidate;
import domain.Department;
import exception.CandidateException;
import exception.DepartmentException;
import exception.DuplicateIdException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.DepartmentRepository;
import util.helper.loader.DepartmentLoader;
import util.helper.loader.LoaderInterface;

import static org.junit.Assert.*;

/**
 * Created by marius on 10/13/16.
 */
public class DepartmentRepositoryTest {
    private DepartmentRepository repository;
    private LoaderInterface      loader;

    @Before
    public void setUp() throws Exception {
        this.repository = new DepartmentRepository();
        this.loader     = new DepartmentLoader();
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
        this.loader     = null;
    }

    @Test
    public void insert() throws Exception {
        Department d1 = new Department(1, "dep1", 10);
        this.repository.insert(d1);
        assertEquals(1, this.repository.getItems().getSize().intValue());

        Department d2 = new Department(2, "dep2", 9);
        this.repository.insert(d2);
        assertEquals(2, this.repository.getItems().getSize().intValue());

        assertSame(this.repository.findById(d1.getId()), d1);
        assertSame(this.repository.findById(d2.getId()), d2);
    }

    @Test(expected = DuplicateIdException.class)
    public void insertThrowsDuplicatedIdException() throws Exception {
        Department d1 = new Department(1, "dep1", 10);
        Department d2 = new Department(1, "dep2", 9);

        this.repository.insert(d1);
        this.repository.insert(d2);
    }

    @Test(expected = DepartmentException.class)
    public void insertThrowsCandidateException() throws Exception {
        this.repository.insert(new Candidate(1, "name1", "12431", "address1"));
    }

    @Test
    public void delete() throws Exception {
        int nrOfDepartments = 50;

        this.loader.load(this.repository, nrOfDepartments);
        assertEquals(nrOfDepartments, this.repository.getItems().getSize().intValue());

        for(int i = nrOfDepartments; i >= 1; i--) {
            this.repository.delete(this.repository.getLastId());
            assertEquals(i - 1, this.repository.getItems().getSize().intValue());
        }
    }

    @Test
    public void findById() throws Exception {
        Department d1 = new Department(1, "dep1", 11);
        Department d2 = new Department(2, "dep2", 12);
        Department d3 = new Department(3, "dep3", 13);

        this.repository.insert(d1);
        this.repository.insert(d2);
        this.repository.insert(d3);

        assertSame(d2, this.repository.findById(2));
        assertSame(d1, this.repository.findById(1));
        assertSame(d3, this.repository.findById(3));
    }


    @Test
    public void update() throws Exception {
        Department d1 = new Department(1, "dep1", 11);
        Department d2 = new Department(2, "dep2", 12);
        Department d3 = new Department(3, "dep3", 13);

        this.repository.insert(d1);
        this.repository.insert(d2);
        this.repository.insert(d3);

        d2.setName("abcd1234");
        d2.setNumberOfSeats(100);
        this.repository.update(d2);

        assertEquals("abcd1234", this.repository.findById(2).getName());
        assertEquals(100, this.repository.findById(2).getNumberOfSeats().intValue());
        assertEquals(2, this.repository.findById(2).getId().intValue());
    }

    @Test
    public void getLastId() throws Exception {
        Department d1 = new Department(1, "dep1", 11);
        Department d2 = new Department(2, "dep2", 12);
        Department d3 = new Department(3, "dep3", 13);

        this.repository.insert(d1);
        assertEquals(1, this.repository.getLastId().intValue());
        this.repository.insert(d2);
        assertEquals(2, this.repository.getLastId().intValue());
        this.repository.insert(d3);
        assertEquals(3, this.repository.getLastId().intValue());

        this.repository.delete(1);
        assertEquals(3, this.repository.getLastId().intValue());

        this.repository.delete(2);
        assertEquals(3, this.repository.getLastId().intValue());

        this.repository.delete(3);
        assertEquals(null, this.repository.getLastId());

    }

    @Test
    public void getNextId() throws Exception {
        Department d1 = new Department(1, "dep1", 11);
        Department d2 = new Department(2, "dep2", 12);
        Department d3 = new Department(3, "dep3", 13);

        this.repository.insert(d1);
        assertEquals(2, this.repository.getNextId().intValue());
        this.repository.insert(d2);
        assertEquals(3, this.repository.getNextId().intValue());
        this.repository.insert(d3);
        assertEquals(4, this.repository.getNextId().intValue());

        this.repository.delete(1);
        assertEquals(4, this.repository.getNextId().intValue());

        this.repository.delete(2);
        assertEquals(4, this.repository.getNextId().intValue());

        this.repository.delete(3);
        assertEquals(0, this.repository.getNextId().intValue());
    }

    @Test
    public void getItems() throws Exception {
        assertTrue(this.repository.getItems() != null);
    }

}