package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {

		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
	}

	@After
	public void destroy() {

		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh18202Test() throws Exception {

		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {

			int resultCount = entityManager.createQuery("select distinct MIN(b.id), a.id "
							+ "from org.hibernate.bugs.TypeA as a "
							+ "left join a.refTypeB as b "
							+ "where b.id IN (:p_0) "
							+ "group by a.id "
							+ "order by MIN(b.id) asc, a.id asc")
					.setParameter("p_0", 1L)
					.getResultList()
					.size();

			Assertions.assertEquals(0, resultCount);
		}
	}
}
