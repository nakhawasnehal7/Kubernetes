package edu.stevens.cs548.clinic.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OrderBy;
import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO JPA annotations
@Entity
public class RadiologyTreatment extends Treatment {

	/**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = -3656673416179492428L;

	/*
	 * TODO Order by date
	 */

	@OrderBy
	protected List<LocalDate> treatmentDates;

	public void addTreatmentDate(LocalDate date) {
		treatmentDates.add(date);
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		// TODO export radiology information.
		return visitor.exportRadiology(
				id,
				patient.getId(),
				patient.getName(),
				provider.getId(),
				provider.getName(),
				diagnosis,
				treatmentDates,
				() -> exportFollowupTreatments(visitor));
	}
	
	public RadiologyTreatment() {
		super();
		treatmentDates = new ArrayList<>();
	}
	
}
