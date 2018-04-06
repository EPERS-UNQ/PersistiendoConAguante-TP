package ar.edu.unq.epers.woe.frontend.app;

import java.util.TimeZone;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Esto es codigo de frontend, no deberian tocar nada de aca.
 */
@Provider
public class JsonObjectMapperProvider implements ContextResolver<ObjectMapper> {

	private final ObjectMapper defaultObjectMapper;
	
	public JsonObjectMapperProvider() {
		this.defaultObjectMapper = new ObjectMapper();
		this.defaultObjectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.defaultObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
	
	@Override
	public ObjectMapper getContext(Class<?> ctx) {
		return this.defaultObjectMapper;
	}
	
}