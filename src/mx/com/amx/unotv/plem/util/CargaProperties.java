package mx.com.amx.unotv.plem.util;

import java.util.Properties;

import mx.com.amx.unotv.plem.dto.ParametrosDTO;

import org.apache.log4j.Logger;

public class CargaProperties {
private static Logger logger=Logger.getLogger(CargaProperties.class);
	
	public ParametrosDTO obtenerPropiedades() {
		ParametrosDTO parametrosDTO = new ParametrosDTO();		 
		try {	    		
			Properties propsTmp = new Properties();
		    propsTmp.load(this.getClass().getResourceAsStream( "/general.properties" ));
			/*String rutaProperties = propsTmp.getProperty(properties);			
			Properties props = new Properties();
			props.load(new FileInputStream(new File(rutaProperties)));*/				
			parametrosDTO.setURL_WS_BASE(propsTmp.getProperty("URL_WS_BASE"));
		} catch (Exception ex) {
			parametrosDTO = new ParametrosDTO();
			logger.error("No se encontro el Archivo de propiedades: ", ex);			
		}
		return parametrosDTO;
    }
}
