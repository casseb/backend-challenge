package com.invillia.acme.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.invillia.acme.common.TestCase;
import com.invillia.acme.ds.Store;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StoreRepositoryTests extends TestCase{

	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private StoreRepository storeRepository;
	
	@Test
	public void findById() {
		//given
		Store store = getEmptyStoreTest();
		entityManager.persist(store);
		entityManager.flush();
		
		//when
		Optional<Store> found = storeRepository.findById(store.getId());
		
		//then
		assertTrue(found.isPresent());
	}
}
