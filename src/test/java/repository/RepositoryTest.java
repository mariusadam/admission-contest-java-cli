package repository;

import domain.Candidate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by marius on 10/28/16.
 */
public class RepositoryTest {
    private RepositoryInterface<Integer, Candidate> repo;

    @Before
    public void setUp() throws Exception {
        repo = new Repository<>();
        Candidate c1 = new Candidate(1, "Candidate1", "123451", "Address1");
        Candidate c2 = new Candidate(2, "Candidate2", "123452", "Address2");
        Candidate c3 = new Candidate(3, "Candidate3", "123453", "Address3");
        Candidate c4 = new Candidate(4, "Candidate4", "123454", "Address4");

        repo.insert(c1);
        repo.insert(c2);
        repo.insert(c3);
        repo.insert(c4);
    }

    @After
    public void tearDown() throws Exception {
        repo = null;
    }

    @Test
    public void insert() throws Exception {
        assertEquals(4, repo.size());

        assertSame(1, repo.findById(1).getId());
        assertSame(2, repo.findById(2).getId());
        assertSame(3, repo.findById(3).getId());
        assertSame(4, repo.findById(4).getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(4, repo.size());

        assertEquals(3, (int)repo.delete(3).getId());
        assertEquals(3, repo.size());

        assertEquals(2, (int)repo.delete(2).getId());
        assertEquals(2, repo.size());

        assertEquals(4, (int)repo.delete(4).getId());
        assertEquals(1, repo.size());

        assertEquals(1, (int)repo.delete(1).getId());
        assertEquals(0, repo.size());
    }

    @Test
    public void update() throws Exception {
        Candidate c3 = repo.findById(3);

        c3.setName("newname");
        c3.setAddress("newaddress");
        c3.setPhone("0000");

        this.repo.update(c3);

        assertEquals("newname", repo.findById(3).getName());
        assertEquals("newaddress", repo.findById(3).getAddress());
        assertEquals("0000", repo.findById(3).getPhone());
    }

    @Test
    public void findById() throws Exception {
        for(int i = 1; i < 5; i++) {
            assertEquals(i, (int)repo.findById(i).getId());
        }
    }

    @Test
    public void getAll() throws Exception {
        Collection<Candidate> all =repo.getAll();

        assertEquals(4, all.size());
    }

}