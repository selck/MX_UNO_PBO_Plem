package mx.com.amx.unotv.plem.util;

import mx.com.amx.unotv.plem.dto.RespuestaWSPlem;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class LlamadasWS {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private RestTemplate restTemplate;
	private String URL_WS_BASE="";
	private HttpHeaders headers = new HttpHeaders();
	
	public LlamadasWS(String urlWS) {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

	        if ( factory instanceof SimpleClientHttpRequestFactory)
	        {
	            ((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 35 * 1000 );
	            ((SimpleClientHttpRequestFactory) factory).setReadTimeout( 35 * 1000 );
	            logger.info("Inicializando rest template");
	        }
	        else if ( factory instanceof HttpComponentsClientHttpRequestFactory)
	        {
	            ((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 35 * 1000);
	            ((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 35 * 1000);
	            logger.info("Inicializando rest template");
	        }
	        restTemplate.setRequestFactory( factory );
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
			URL_WS_BASE = urlWS;
	}
	
	public RespuestaWSPlem callPlem(String idMagazine, String audiencia) {
		String metodo = "callPlem";
		String URL_WS = URL_WS_BASE + metodo;
		logger.debug("URLWS= " + URL_WS);
		RespuestaWSPlem respuestaWSPlem=new RespuestaWSPlem();
		try {
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idMagazine", idMagazine);
			parts.add("audiencia", audiencia);
						
			respuestaWSPlem=restTemplate.postForObject(URL_WS, parts, RespuestaWSPlem.class);
			
		} catch (Exception e) {
			logger.error("Error callPlem: ",e);
			respuestaWSPlem.setCodigo("-1");
			respuestaWSPlem.setRespuesta(e.getMessage());
		}
		return respuestaWSPlem;
	}
}
