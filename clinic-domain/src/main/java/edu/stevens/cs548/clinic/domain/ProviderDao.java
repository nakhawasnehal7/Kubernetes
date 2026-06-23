package edu.stevens.cs548.clinic.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.jboss.logging.Logger;

// TODO
@ApplicationScoped
public class ProviderDao implements IProviderDao {

    // TODO inject these fields (use constructor injection)

    private final Logger logger;

    private final EntityManager em;

	private final ITreatmentDao treatmentDao;

    @Inject
	public ProviderDao(Logger logger, EntityManager em, ITreatmentDao treatmentDao) {
        this.logger = logger;
        this.em = em;
        this.treatmentDao = treatmentDao;
    }


    // private Logger logger = Logger.getLogger(ProviderDao.class.getCanonicalName());

	@Override
	public void addProvider(Provider provider) throws ProviderExn {
		// Add to database, and initialize the provider aggregate with a treatment DAO.
        UUID id = provider.getId();
		Query query = em.createNamedQuery("CountProviderByProviderId").setParameter("providerId", id);
		Long numExisting = (Long) query.getSingleResult();
		
		logger.info(String.format("Adding provider with id %s, found %d existing records", id, numExisting));
		
		if (numExisting < 1) {
			
			em.persist(provider);
			provider.setTreatmentDao(this.treatmentDao);
			
		} else {
			
			throw new ProviderExn("Insertion: Provider with Provider id (" + id + ") already exists.");

		}
	}

	@Override
	/*
	 * The boolean flag indicates if related treatments should be loaded eagerly.
	 */
	public Provider getProvider(UUID id, boolean includeTreatments) throws ProviderExn {
		/*
		 * TODO retrieve Provider using external key.
		 *
		 * See Patient::getPatient for an example.
		 */
		String queryName = "SearchProviderByProviderId";
		TypedQuery<Provider> query = em.createNamedQuery(queryName, Provider.class).setParameter("providerId",id);
		List<Provider> providers = query.getResultList();

		if (providers.size() > 1) {
			throw new IProviderDao.ProviderExn("Duplicate provider records: provider id = " + id);
		} else if (providers.isEmpty()) {
			throw new IProviderDao.ProviderExn("Provider not found: provider id = " + id);
		} else {
			Provider p = providers.getFirst();
			/*
			 * Refresh from database or we will never see new treatments.
			 */
			//em.refresh(p);
			p.setTreatmentDao(this.treatmentDao);
			return p;
		}

	}
	
	@Override
	/*
	 * By default, we eagerly load related treatments with a provider record.
	 */
	public Provider getProvider(UUID id) throws ProviderExn {
		return getProvider(id, true);
	}
	
	@Override
	public List<Provider> getProviders() {
		/*
		 * TODO Return a list of all providers (remember to set treatmentDAO)
		 *
		 * See Patient::getPatients for an example.
		 */
		TypedQuery<Provider> query = em.createNamedQuery("SearchAllProviders", Provider.class);
		List<Provider> providers = query.getResultList();

		for (Provider p : providers) {
			p.setTreatmentDao(treatmentDao);
		}

		return providers;
	}
	
	@Override
	public void deleteProviders() {
		Query update = em.createNamedQuery("RemoveAllTreatments");
		update.executeUpdate();
		update = em.createNamedQuery("RemoveAllProviders");
		update.executeUpdate();
	}

}
