package edu.stevens.cs548.clinic.micro.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.stevens.cs548.clinic.service.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ObjectMapperFactory;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.quarkus.rest.client.reactive.jackson.ClientObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

// TODO annotate
@RegisterRestClient(configKey = "clinic-domain-api")
@Path("/provider")
@Produces("application/json")
@Consumes("application/json")
public interface IProviderMicroService {

    /*
     * Customized the object mapper using customizations from DTO factory.
     */
    @ClientObjectMapper
    static ObjectMapper objectMapper(ObjectMapper defaultObjectMapper) {
        return IPatientMicroService.objectMapper(defaultObjectMapper);
    }

	// TODO

    @POST
    Response addProvider(ProviderDto dto);

	// TODO

    @GET
    List<ProviderDto> getProviders();

	// TODO

    @GET
    @Path("/{id}")
    ProviderDto getProvider(@PathParam("id") String id, @QueryParam("treatments") String includeTreatments);

	// TODO

    @POST
    @Path("/{id}/treatment")
    Response addTreatment(@PathParam("id") String id, TreatmentDto dto);

	// TODO

    @GET
    @Path("/{id}/treatment/{tid}")
    TreatmentDto getTreatment(@PathParam("id") String providerId, @PathParam("tid") String treatmentId);

	// TODO

    @DELETE
    void removeAll();
		
}
