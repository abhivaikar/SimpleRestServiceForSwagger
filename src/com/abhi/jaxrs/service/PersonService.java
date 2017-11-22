package com.abhi.jaxrs.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.abhi.jaxrs.model.CustomResponse;

import com.abhi.jaxrs.model.Person;



@Path("/person")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class PersonService {
	
	private static Map<Integer,Person> persons = new HashMap<Integer,Person>();

	
	
	@POST
    @Path("/add")
	public Response addPerson(Person p) {
		
		CustomResponse cResponse = new CustomResponse();
		if(persons.get(p.getId()) != null){
			cResponse.setStatus(false);
			cResponse.setMessage("Person already exists!");
			return Response.status(Response.Status.OK).entity(cResponse).build();
		}
		persons.put(p.getId(), p);
		cResponse.setStatus(true);
		cResponse.setMessage("Person created successfully");
		return Response.status(Response.Status.OK).entity(cResponse).build();
	}

	
	@GET
    @Path("/{id}/delete")
	public Response deletePerson(@PathParam("id") int id) {
		CustomResponse cResponse = new CustomResponse();
		if(persons.get(id) == null){
			cResponse.setStatus(false);
			cResponse.setMessage("Person doesn't exist");
			return Response.status(Response.Status.NOT_FOUND).entity(cResponse).build();
		}
		persons.remove(id);
		cResponse.setStatus(true);
		cResponse.setMessage("Person deleted successfully");
		return Response.status(Response.Status.OK).entity(cResponse).build();
	}

	
	@GET
	@Path("/{id}/get")
	public Response getPerson(@PathParam("id") int id) {
		Person p = persons.get(id);
		if(p==null)
		{
			CustomResponse cResponse = new CustomResponse();
			cResponse.setMessage("Person not found!");
			cResponse.setStatus(false);
			return Response.status(Response.Status.NOT_FOUND).entity(cResponse).build();
		}
		return Response.status(Response.Status.OK).entity(p).build();
	}
	

	@GET
	@Path("/{id}/getDummy")
	public Response getDummyPerson(@PathParam("id") int id) {
		Person p = new Person();
		p.setAge(99);
		p.setName("Dummy");
		p.setId(id);
		return Response.status(Response.Status.OK).entity(p).build();
	}

	
	@GET
	@Path("/getAll")
	public Response getAllPersons() {
		Set<Integer> ids = persons.keySet();
		Person[] p = new Person[ids.size()];
		int i=0;
		for(Integer id : ids){
			p[i] = persons.get(id);
			i++;
		}
		return Response.status(Response.Status.OK).entity(p).build();
	}

}
