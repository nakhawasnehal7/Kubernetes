package edu.stevens.cs548.clinic.service.init;

import edu.stevens.cs548.clinic.service.IPatientService;
import edu.stevens.cs548.clinic.service.IProviderService;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.PhysiotherapyTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.SurgeryTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDtoFactory;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import org.jboss.logging.Logger;

@ApplicationScoped
@Transactional
public class InitBean {


	private static final ZoneId ZONE_ID = ZoneOffset.UTC;

	private final PatientDtoFactory patientFactory = new PatientDtoFactory();

	private final ProviderDtoFactory providerFactory = new ProviderDtoFactory();

	private final TreatmentDtoFactory treatmentFactory = new TreatmentDtoFactory();

    // TODO inject the services using constructor injection

	private final IPatientService patientService;

	private final IProviderService providerService;

    private final Logger logger;

   @Inject
   public InitBean(IPatientService patientService, IProviderService providerService, Logger logger) {
        this.patientService = patientService;
        this.providerService = providerService;
        this.logger = logger;
    }


    public void init(@Observes StartupEvent event) {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the
		 * server logs.
		 */
		logger.info("Your name here: ");

		try {

			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * Note that the service generates the external ids, when adding the entities.
			 */

			providerService.removeAll();
			patientService.removeAll();

			PatientDto john = patientFactory.createPatientDto();
			john.setName("John Doe");
			john.setDob(LocalDate.parse("1990-08-15"));
			logger.info("Adding patient John....");
			john.setId(patientService.addPatient(john));


			PatientDto snehal = patientFactory.createPatientDto();
			snehal.setName("Snehal Nakhawa");
			snehal.setDob(LocalDate.parse("1991-08-15"));
			logger.info("Adding patient snehal....");
			snehal.setId(patientService.addPatient(snehal));


			PatientDto nikita = patientFactory.createPatientDto();
			nikita.setName("Nikita Nakhawa");
			nikita.setDob(LocalDate.parse("1992-08-15"));
			logger.info("Adding patient Nikita....");
			nikita.setId(patientService.addPatient(nikita));

			/* Provider*/

			ProviderDto jane = providerFactory.createProviderDto();
			jane.setName("Jane Doe");
			jane.setNpi("1234");
			logger.info("Adding provider Jane....");
			jane.setId(providerService.addProvider(jane));

			ProviderDto pooja = providerFactory.createProviderDto();
			pooja.setName("POOJA Koli");
			pooja.setNpi("222222");
			logger.info("Adding provider POOJA....");
			pooja.setId(providerService.addProvider(pooja));

			ProviderDto aishu = providerFactory.createProviderDto();
			aishu.setName("Aishu Nakhawa");
			aishu.setNpi("33333333333333");

			logger.info("Adding provider Aishu....");
			aishu.setId(providerService.addProvider(aishu));


			/*Treatments*/

			DrugTreatmentDto drug01 = treatmentFactory.createDrugTreatmentDto();
			drug01.setPatientId(john.getId());
			drug01.setPatientName(john.getName());
			drug01.setProviderId(jane.getId());
			drug01.setProviderName(jane.getName());
			drug01.setDiagnosis("Headache");
			drug01.setDrug("Aspirin");
			drug01.setDosage(20);
			drug01.setFrequency(7);
			drug01.setStartDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));
			drug01.setEndDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));

			DrugTreatmentDto drugFolllowup = treatmentFactory.createDrugTreatmentDto();
			drugFolllowup.setPatientId(john.getId());
			drugFolllowup.setPatientName(john.getName());
			drugFolllowup.setProviderId(jane.getId());
			drugFolllowup.setProviderName(jane.getName());
			drugFolllowup.setDiagnosis("Headache followup");
			drugFolllowup.setDrug("Ibuprofen");
			drugFolllowup.setDosage(20);
			drugFolllowup.setFrequency(7);
			drugFolllowup.setStartDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));
			drugFolllowup.setEndDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));
			drug01.getFollowupTreatments().add(drugFolllowup);
			logger.info("Adding John's drug01 treatment");
			providerService.addTreatment(drug01);


			SurgeryTreatmentDto surgery = treatmentFactory.createSurgeryTreatmentDto();
			surgery.setPatientId(john.getId());
			surgery.setPatientName(john.getName());
			surgery.setProviderId(pooja.getId());
			surgery.setProviderName(pooja.getName());
			surgery.setDiagnosis("Heart Surgery");
			surgery.setSurgeryDate(LocalDate.of(2024, 5, 20));
			surgery.setDischargeInstructions("Rest for 2 weeks, avoid heavy lifting.");

			logger.info("Adding Heart's surgery treatment...");
			providerService.addTreatment(surgery);



			DrugTreatmentDto drug02 = treatmentFactory.createDrugTreatmentDto();
			drug02.setPatientId(snehal.getId());
			drug02.setPatientName(snehal.getName());
			drug02.setProviderId(pooja.getId());
			drug02.setProviderName(pooja.getName());
			drug02.setDiagnosis("Stomach");
			drug02.setDrug("DOLO");
			drug02.setDosage(20);
			drug02.setFrequency(7);
			drug02.setStartDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));
			drug02.setEndDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));

			logger.info("Adding pooja's drug01 treatment");
			providerService.addTreatment(drug02);


			RadiologyTreatmentDto radiology = treatmentFactory.createRadiologyTreatmentDto();
			radiology.setPatientId(snehal.getId());
			radiology.setPatientName(snehal.getName());
			radiology.setProviderId(pooja.getId());
			radiology.setProviderName(pooja.getName());
			radiology.setDiagnosis("Chest X ray");
			radiology.getTreatmentDates().add(LocalDate.of(2024, 3, 10));
			radiology.getTreatmentDates().add(LocalDate.of(2024, 4, 10));
			logger.info("Adding Bob's radiology treatment...");
			providerService.addTreatment(radiology);



			SurgeryTreatmentDto surgery02 = treatmentFactory.createSurgeryTreatmentDto();
			surgery02.setPatientId(snehal.getId());
			surgery02.setPatientName(snehal.getName());
			surgery02.setProviderId(jane.getId());
			surgery02.setProviderName(jane.getName());
			surgery02.setDiagnosis("Appendicitis Surgery");
			surgery02.setSurgeryDate(LocalDate.of(2024, 5, 20));
			surgery02.setDischargeInstructions("Rest for 2 weeks, avoid heavy lifting.");
			logger.info("Adding Heart's surgery treatment...");
			providerService.addTreatment(surgery02);



			PhysiotherapyTreatmentDto phy = treatmentFactory.createPhysiotherapyTreatmentDto();
			phy.setPatientId(nikita.getId());
			phy.setPatientName(nikita.getName());
			phy.setProviderId(aishu.getId());
			phy.setProviderName(aishu.getName());
			phy.setDiagnosis("Knee Pain");
			phy.getTreatmentDates().add(LocalDate.of(2022, 4, 1));
			phy.getTreatmentDates().add(LocalDate.of(2022, 4, 8));
			phy.getTreatmentDates().add(LocalDate.of(2022, 4 ,15));
			logger.info("Adding Knee's physiotherapy treatment...");
			providerService.addTreatment(phy);


			SurgeryTreatmentDto surgery03 = treatmentFactory.createSurgeryTreatmentDto();
			surgery03.setPatientId(nikita.getId());
			surgery03.setPatientName(nikita.getName());
			surgery03.setProviderId(aishu.getId());
			surgery03.setProviderName(aishu.getName());
			surgery03.setDiagnosis("Knee Replacement");
			surgery03.setSurgeryDate(LocalDate.of(2024, 5, 20));
			surgery03.setDischargeInstructions("Rest for 2 weeks, avoid heavy lifting.");



			PhysiotherapyTreatmentDto phy01 = treatmentFactory.createPhysiotherapyTreatmentDto();
			phy01.setPatientId(nikita.getId());
			phy01.setPatientName(nikita.getName());
			phy01.setProviderId(aishu.getId());
			phy01.setProviderName(aishu.getName());
			phy01.setDiagnosis("Lower back Pain");
			phy01.getTreatmentDates().add(LocalDate.of(2023, 7, 1));
			phy01.getTreatmentDates().add(LocalDate.of(2023, 7, 8));
			phy01.getTreatmentDates().add(LocalDate.of(2023, 7, 25));
			logger.info("Adding Lower back Pain's physiotherapy treatment...");
			providerService.addTreatment(phy01);



			PhysiotherapyTreatmentDto phy01FollowUp = treatmentFactory.createPhysiotherapyTreatmentDto();
			phy01FollowUp.setPatientId(nikita.getId());
			phy01FollowUp.setPatientName(nikita.getName());
			phy01FollowUp.setProviderId(aishu.getId());
			phy01FollowUp.setProviderName(aishu.getName());
			phy01FollowUp.setDiagnosis("Lower back Pain");
			phy01FollowUp.getTreatmentDates().add(LocalDate.of(2023, 7, 1));
			phy01FollowUp.getTreatmentDates().add(LocalDate.of(2023, 7, 8));
			phy01FollowUp.getTreatmentDates().add(LocalDate.of(2023, 7, 25));
			logger.info("Adding Lower back Pain's physiotherapy treatment...");
			surgery03.getFollowupTreatments().add(phy01FollowUp);

			logger.info("Adding Nikita's surgery with physiotherapy follow-up...");
			providerService.addTreatment(surgery03);

			// TODO add more testing, including treatments and providers


			// Now show in the logs what has been added

			Collection<PatientDto> patients = patientService.getPatients();
			for (PatientDto p : patients) {
				logger.info(String.format("Patient %s, ID %s, DOB %s", p.getName(), p.getId().toString(),
						p.getDob().toString()));
				logTreatments(p.getTreatments());
			}

			Collection<ProviderDto> providers = providerService.getProviders();
			for (ProviderDto p : providers) {
				logger.info(String.format("Provider %s, ID %s, NPI %s", p.getName(), p.getId().toString(), p.getNpi()));
				logTreatments(p.getTreatments());
			}

		} catch (Exception e) {

			throw new IllegalStateException("Failed to add record.", e);

		}
		
	}

	private void logTreatments(Collection<TreatmentDto> treatments) {
		for (TreatmentDto treatment : treatments) {
			switch (treatment) {
                case DrugTreatmentDto drugTreatmentDto -> logTreatment(drugTreatmentDto);
                case PhysiotherapyTreatmentDto physiotherapyTreatmentDto -> logTreatment(physiotherapyTreatmentDto);
                case RadiologyTreatmentDto radiologyTreatmentDto -> logTreatment(radiologyTreatmentDto);
                case SurgeryTreatmentDto surgeryTreatmentDto -> logTreatment(surgeryTreatmentDto);
            }
			if (!treatment.getFollowupTreatments().isEmpty()) {
				logger.info("============= Follow-up Treatments");
				logTreatments(treatment.getFollowupTreatments());
				logger.info("============= End Follow-up Treatments");
			}
		}
	}

	private void logTreatment(DrugTreatmentDto t) {
		logger.info(String.format("...Drug treatment for %s, drug %s", t.getPatientName(), t.getDrug()));
	}

	private void logTreatment(RadiologyTreatmentDto t) {
		logger.info(String.format("...Radiology treatment for %s", t.getPatientName()));
	}

	private void logTreatment(SurgeryTreatmentDto t) {
		logger.info(String.format("...Surgery treatment for %s", t.getPatientName()));
	}

	private void logTreatment(PhysiotherapyTreatmentDto t) {
		logger.info(String.format("...Physiotherapy treatment for %s", t.getPatientName()));
	}

}
