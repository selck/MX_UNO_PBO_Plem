package mx.com.amx.unotv.plem.portlet;

import java.io.*;

import javax.portlet.*;

import mx.com.amx.unotv.plem.dto.ParametrosDTO;
import mx.com.amx.unotv.plem.dto.RespuestaWSPlem;
import mx.com.amx.unotv.plem.util.LlamadasWS;
import mx.com.amx.unotv.plem.util.CargaProperties;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;


public class MX_UNO_PBO_PlemPortlet extends javax.portlet.GenericPortlet {

	private Logger logger=Logger.getLogger(MX_UNO_PBO_PlemPortlet.class);
	
	public void init() throws PortletException{
		super.init();
	}
	/*
	public static void main(String [] args){
		String respuesta="<html><head><title>502 Bad Gateway</title></head><body><center><h1>502 Bad Gateway</h1></center><hr><center>nginx</center></body></html>";
		try {
			if(respuesta.startsWith("{")){
				JSONObject res=new JSONObject(respuesta);
				System.out.println("Respuesta WS en jsp: "+res.get("descripcion"));
			}else{
				String wordSearch="<title>";
				int inicioTitle=respuesta.indexOf(wordSearch);
				int finTitle=respuesta.indexOf("</title>");
				respuesta=respuesta.substring(inicioTitle + wordSearch.length(), finTitle );
				System.out.println(respuesta);
			}
			
			
		} catch (Exception e) {
			System.out.println("Error en el main: "+e.getMessage());
		}
	}*/
	
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		logger.debug("===== doView =====");
		String dispatch=(String) (request.getPortletSession().getAttribute("dispatch")==null || request.getPortletSession().getAttribute("dispatch").equals("")?"inicio":request.getPortletSession().getAttribute("dispatch"));
		String respuestaWSRequest=(String) (request.getPortletSession().getAttribute("respuestaWSRequest")==null || request.getPortletSession().getAttribute("respuestaWSRequest").equals("")?"vacio":request.getPortletSession().getAttribute("respuestaWSRequest"));
		try {
			logger.debug("dispatch: "+dispatch);
			logger.debug("respuestaWSRequest: "+respuestaWSRequest);
			
			if(dispatch.equalsIgnoreCase("inicio")){
				dispatch="/resources/jsp/inicio.jsp";
			}
			if(!respuestaWSRequest.equals("vacio")){
				if(respuestaWSRequest.startsWith("{")){
					JSONObject res=new JSONObject(respuestaWSRequest);
					logger.debug("Respuesta WS en jsp: "+res.get("descripcion"));
					request.setAttribute("respuestaWSRequest", res.get("descripcion"));
				}else{
					String wordSearch="<title>";
					int inicioTitle=respuestaWSRequest.indexOf(wordSearch);
					int finTitle=respuestaWSRequest.indexOf("</title>");
					respuestaWSRequest=respuestaWSRequest.substring(inicioTitle + wordSearch.length(), finTitle );
					logger.debug("Respuesta WS en jsp: "+respuestaWSRequest);
					request.setAttribute("respuestaWSRequest", respuestaWSRequest);
				}
			}
			response.setContentType(request.getResponseContentType());
			PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(dispatch);
			rd.include(request,response);
		} catch (Exception e) {
			logger.error("Error DoView: ",e);
		}
	}
	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, java.io.IOException {
		try {
			logger.debug("===== processAction =====");
			
			String idMagazine=(String) (request.getParameter("idMagazineHidden")==null ?"magazine-newsletter":request.getParameter("idMagazineHidden"));
			String audiencia=(String) (request.getParameter("valorChecadoHidden")==null?"":request.getParameter("valorChecadoHidden"));
			String dispatch=(String) (request.getParameter("dispatchHidden")==null?"":request.getParameter("dispatchHidden"));
			
			logger.debug("dispatch: "+dispatch);
			logger.debug("audiencia: "+audiencia);
			logger.debug("idMagazine: "+idMagazine);
			
			if(dispatch.equalsIgnoreCase("END_NEWSLETTER")){
				request.getPortletSession().removeAttribute("respuestaWSRequest");
			}else if(dispatch.equalsIgnoreCase("SEND_NEWSLETTER")){
				
				CargaProperties utilPlem=new CargaProperties();
				ParametrosDTO param=utilPlem.obtenerPropiedades();
				LlamadasWS llamadasWS=new LlamadasWS(param.getURL_WS_BASE());
				try{
					RespuestaWSPlem respuestaWSPlem=llamadasWS.callPlem(idMagazine, audiencia);
					logger.info("Respuesta del WS: "+respuestaWSPlem.getRespuesta());
					request.getPortletSession().setAttribute("respuestaWSRequest", respuestaWSPlem.getRespuesta());
				}catch(Exception e){
					logger.error("Error al hacer lo del RestTemplate "+e.getMessage());
				}
			}			
		} catch (Exception e) {
			logger.error("Error processAction: ",e);
		}
	}

}
