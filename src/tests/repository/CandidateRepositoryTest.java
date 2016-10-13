package tests.repository;

import domain.Candidate;
import domain.Department;
import exception.CandidateException;
import exception.DuplicateIdException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.CandidateRepository;
import util.helper.loader.CandidateLoader;
import util.helper.loader.LoaderInterface;

import static org.junit.Assert.*;

/**
 * Created by marius on 10/13/16.
 */
public class CandidateRepositoryTest {
    private CandidateRepository repository;
    private LoaderInterface     loader;

    @Before
    public void setUp() throws Exception {
        this.repository = new CandidateRepository();
        this.loader     = new CandidateLoader();
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
        this.loader     = null;
    }

    @Test
    public void insert() throws Exception {
        Candidate c1 = new Candidate(1, "name1", "12431", "address1");
        this.repository.insert(c1);
        assertEquals(1, this.repository.getItems().getSize().intValue());

        Candidate c2 = new Candidate(2, "name2", "12433431", "address2");
        this.repository.insert(c2);
        assertEquals(2, this.repository.getItems().getSize().intValue());

        assertSame(this.repository.findById(c1.getId()), c1);
        assertSame(this.repository.findById(c2.getId()), c2);
    }

    @Test(expected = DuplicateIdException.class)
    public void insertThrowsDuplicatedIdException() throws Exception {
        Candidate c1 = new Candidate(1, "name1", "12431", "address1");
        Candidate c2 = new Candidate(1, "name2", "234234234", "address2");

        this.repository.insert(c1);
        this.repository.insert(c2);
    }

    @Test(expected = CandidateException.class)
    public void insertThrowsCandidateException() throws Exception {
        this.repository.insert(new Department(1, "123123", 34));
    }

    @Test
    public void delete() throws Exception {
        this.loader.load(this.repository, 10);
        assertEquals(10, this.repository.getItems().getSize().intValue());

        for(int i = 10; i >= 1; i--) {
            this.repository.delete(this.repository.getLastId());
            assertEquals(i - 1, this.repository.getItems().getSize().intValue());
        }
    }

    @Test
    public void findById() throws Exception {
        Candidate c1 = new Candidate(1, "name1", "12431", "address1");
        Candidate c2 = new Candidate(2, "name2", "234234234", "address2");
        Candidate c3 = new Candidate(3, "name3", "23333", "xxvxvx");

        this.repository.insert(c1);
        this.repository.insert(c2);
        this.repository.insert(c3);

        assertSame(c2, this.repository.findById(2));
        assertSame(c1, this.repository.findById(1));
        assertSame(c3, this.repository.findById(3));
    }


    @Test
    public void update() throws Exception {
        Candidate c1 = new Candidate(1, "name1", "12431", "address1");
        Candidate c2 = new Candidate(2, "name2", "234234234", "address2");
        Candidate c3 = new Candidate(3, "name3", "23333", "xxvxvx");

        this.repository.insert(c1);
        this.repository.insert(c2);
        this.repository.insert(c3);

        c2.setName("newname");
        c2.setPhone("0000000");
        c2.setAddress("aaaaaa");
        this.repository.update(c2);

        assertEquals("newname", this.repository.findById(2).getName());
        assertEquals("0000000", this.repository.findById(2).getPhone());
        assertEquals("aaaaaa", this.repository.findById(2).getAddress());
    }

    @Test
    public void getLastId() throws Exception {
        Candidate c1 = new Candidate(1, "name1", "12431", "address1");
        Candidate c2 = new Candidate(2, "name2", "234234234", "address2");
        Candidate c3 = new Candidate(3, "name3", "23333", "xxvxvx");

        this.repository.insert(c1);
        assertEquals(1, this.repository.getLastId().intValue());
        this.repository.insert(c2);
        assertEquals(2, this.repository.getLastId().intValue());
        this.repository.insert(c3);
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
        Candidate c1 = new Candidate(1, "name1", "12431", "address1");
        Candidate c2 = new Candidate(2, "name2", "234234234", "address2");
        Candidate c3 = new Candidate(3, "name3", "23333", "xxvxvx");

        this.repository.insert(c1);
        assertEquals(2, this.repository.getNextId().intValue());
        this.repository.insert(c2);
        assertEquals(3, this.repository.getNextId().intValue());
        this.repository.insert(c3);
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