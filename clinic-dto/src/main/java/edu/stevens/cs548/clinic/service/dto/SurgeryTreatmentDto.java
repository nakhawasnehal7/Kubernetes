package edu.stevens.cs548.clinic.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;

// TODO annotate for type tag
@JsonTypeName("surgery")

public final class SurgeryTreatmentDto extends TreatmentDto {

	/*
	 * TODO add two fields:
	 *  surgeryDate (type LocalDate)
	 *  dischargeInstructions (type String)
	 * Also add getter and setter methods for these properties.
	 */
	@JsonProperty("surgeryDate")
	private  LocalDate surgeryDate;
	@JsonProperty("dischargeInstructions")
	private String dischargeInstructions;

	public LocalDate getSurgeryDate() {
		return surgeryDate;
	}

	public void setSurgeryDate(LocalDate surgeryDate) {
		this.surgeryDate = surgeryDate;
	}

	public String getDischargeInstructions() {
		return dischargeInstructions;
	}

	public void setDischargeInstructions(String dischargeInstructions) {
		this.dischargeInstructions = dischargeInstructions;
	}
}
