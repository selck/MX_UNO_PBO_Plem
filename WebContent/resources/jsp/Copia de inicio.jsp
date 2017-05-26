<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
	<portlet:defineObjects/>
	<portlet-client-model:init>
    <portlet-client-model:require module="ibm.portal.xml.*"/>
    <portlet-client-model:require module="ibm.portal.portlet.*"/>   
	</portlet-client-model:init>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/jquery-latest.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/bootpag.min.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/llamadasNoticias.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/llamadasProgramas.js'></script>
<link href="<%= request.getContextPath() %>/resources/css/paginacion.css" rel="stylesheet" type="text/css" />

<style>
	.content { 
			padding: 10px 10px 10px 10px;
			border : 1px solid #ccc;
	}
</style>

<c:if test="${empty requestScope.respuestaWSRequest}" >
<div id="allContent">
<div id="wait" style="display: none; width: 69px; height: 89px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
	<img src="<%= request.getContextPath() %>/resources/img/demo_wait.gif" width="64" height="64">
	<br>Loading..
</div>
<div class="content">	
	<p><h3>Elementos del Componente</h3></p>
	<div id="elementos_magazine">			

	</div>
</div>

<!-- Secciones -->	
<div class="content">
	<strong>Seleccione Notas de Noticias </strong>
	<br>
	Secci&oacute;n:
	<select name="secciones-noticias" id="secciones-noticias">
		<option value="-1">Seleccione</option>
	</select>
</div>
<div class="content">
	<strong>Seleccione Notas de Programas</strong>
	<br>
	Secci&oacute;n:
	<select name="secciones-programas" id="secciones-programas">
		<option value="-1">Seleccione</option>
	</select>
</div>
<div class="content">
	Programa:
	<select name="programas" id="programas">
		<option value="-1">Seleccione</option>
	</select>
</div>

</br>
<div class="content">
	<input type="button" value="Agregar seleccion a magazine" id="agregar_noticias"/>
	<span id="error_seleccion">&nbsp;</span>
</div>

<div id="div_guarda_notas" class="content" style="padding-top: 30px; padding-bottom: 30px;">
	<!--<input type="button" value="Generar HTML" id="generar_html" onclick="createHTML();" disabled="disabled">-->
	<input type="button" value="Vista Previa" id="guarda_notas" onclick="guardaNotas();" disabled="disabled">
	<span id="error_generar_html">&nbsp;</span>
</div>  
<div id="loading" style="height:50px;display:none;">
Procesando ...
<img src="<%=request.getContextPath() %>/resources/img/demo_wait.gif" style="vertical-align:middle; width:96px; height:25px"/>
</div>
<div id="sendNewsLetter" style="display: none;" class="content">
<form name="formNewsLetter" method="post" action="<portlet:actionURL/>">

<div style="width: 400px; height:20px ">
	<div id="errorSubbmit" style="color: #D8000C; background-color: #FFBABA; text-align: center;"></div>
</div>

	<input type="hidden" id="idMagazineHidden" name="idMagazineHidden" value="magazine-newsletter"/>
	<input type="hidden" id="valorChecadoHidden" name="valorChecadoHidden" value=""/>
	<input type="hidden" id="dispatchHidden" name="dispatchHidden" value=""/>
	<input type="radio" name="audiencia" value="T">Audiencia Prueba<br>
	<input type="radio" name="audiencia" value="P">Audiencia Productiva<br><br>
	<input type="button" value="Enviar NewsLetter" id="newsletterButton" onclick="sendNewsLetter(document.formNewsLetter);">
</form>	
</div> 
<div class="content">
	<p><h2><span id="titulo_seccion"></span></h2></p>
	<div id="noticias" >
		<!-- Noticias -->
	</div>
	<div id="page-selection" class="pagination bootpag" style="text-align:right;" ></div>
</div>
</div>
<!-- end Noticias -->
</c:if>


<c:if test="${not empty requestScope.respuestaWSRequest}" >
<!-- ini Result WS -->
<form name="formResultadoWS" method="post" action="<portlet:actionURL/>">
<input type="hidden" id="dispatchHidden" name="dispatchHidden" value=""/>
<div id="resultadoWS" class="content">
    <h2>El estado de la solictud es: <b><span style="background-color: #FFBABA; color: #D8000C" id="resultadoWSpan">${requestScope.respuestaWSRequest}</span></b></h2>
	<input type="button" value="Regresar" id="regresar" onclick="regresarHome(document.formResultadoWS);">
</div>
</form>
<!-- end Result WS -->
</c:if>

<!-- ini Previa -->
<div id="previa" style="display:none">
		<div id="contentDinamico">
		  <div style="margin: 0 auto; width: 600px;">
			<table width="600" border="0" cellspacing="0" cellpadding="0" style="background:#dbdbdb; margin:0 auto">
	        <!--INICIO DE HEADER-->		        
		       <tr style="background:#1e242c">
				<td  style="text-align:left"><a target="" title="" href="http://www.unotv.com" ><img style="display:block; line-height:0px" border="0" src="http://www.unotv.com/portal/unotv/utils/imgNewsletter/uno-tv-logo.jpg" width="153" height="53" alt="Uno TV" /></a></td>
				<td  style="text-align:right; padding-right:30px"><a target="" title="" href="https://www.facebook.com/UnoTVNoticias" ><img style="line-height:0px" border="0" src="http://www.unotv.com/portal/unotv/utils/imgNewsletter/icon-fb.jpg" width="22" height="22" alt="facebook" /></a><a target="" title="" href="https://twitter.com/unonoticias" ><img style="line-height:0px" border="0" src="http://www.unotv.com/portal/unotv/utils/imgNewsletter/icon-tw.jpg" width="22" height="22" alt="twitter" /></a><a target="" title="" href="https://plus.google.com/111994956321244108047" ><img style="line-height:0px" border="0" src="http://www.unotv.com/portal/unotv/utils/imgNewsletter/icon-plus.jpg" width="22" height="22" alt="plus" /></a><a target="" title="" href="http://www.mobli.com/unotv" ><img style="line-height:0px" border="0" src="http://www.unotv.com/portal/unotv/utils/imgNewsletter/icon-mobli.jpg" width="22" height="22" alt="mobli" /></a></td>
			  </tr>
			  
			  <tr>
				<td colspan="2" height="71" style="text-align:center; padding:15px">
			             <div id="BannerSuperior" style="width:519px; height:71px; margin: auto"></div>
				</td>
			  </tr>
       		 <!--FIN DE HEADER-->

    		<!--INICIO DE CONTENIDO DINÁMICO-->
			<tr>
				<td colspan="2"  style="padding:0 15px 15px 15px;">
    		    <div  id="htmlNews" >        
					<table id="htmlNotas" style="display: inline-table;" border="0" cellpadding="0" cellspacing="0" width="600">
						
					</table>
                </div>
				</td>
			</tr>
 	        <!--FIN DE CONTENIDO DINÁMICO-->
			<tr>
				<td colspan="2" height="71" style="text-align:center; padding:15px">
			             <div id="BannerInferior" style="width:519px; height:71px; margin: auto"></div>					
				</td>
			  </tr>
			<!--INICIO DE FOOTER-->
			 <tr>
				<td colspan="2">
				<p style="color:#333; font-family:Arial, Helvetica, sans-serif; margin:0 0 10px 0; font-size:11px; text-align:center">Si ud. ya no desea recibir este boletín informativo, <a target="" title="" href="mailto:unsuscribe@telmex.mitmx.net"  style="color:#333; font-weight:bold;">desuscríbase aquí.</a></p>
				<p style="color:#333; font-family:Arial, Helvetica, sans-serif; margin:0 0 10px 0; font-size:11px; text-align:center">Powered by Web comunicaciones Copyright © 2009 Todos los derechos reservados para UNO TV</p>
				</td>
			 </tr>
			<!--FIN DE FOOTER-->
			</table>
 	        </div>
 	      </div>
		
			
</div><!--Previa-->
<%
request.removeAttribute("respuestaWSRequest");
%>

