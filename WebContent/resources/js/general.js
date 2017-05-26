var urlBDContent ='/MX_UNO_WSD_BackOffice/rest/backOfficeController/saveNotes';

function validarLinks(){
	var flag=false;
	for ( var i = 0; i < arrayMagazine.length; i++) {
		if(arrayMagazine[i].fcNombre == ""){
			flag =true;
			break;
		}
	}
	return flag;
}
	function guardarLink(index){
		var indexArray=index;
		var i=index+1;
		var nuevoLink=$("#linkNoticia"+i).val();
		if(nuevoLink == ""){
			confirm("Favor de validar link");
		}else{
			arrayMagazine [ indexArray ].fcNombre=nuevoLink;
			$("#linkNoticia"+i).prop("disabled", true);
			$("#tdLinkNoticia"+index).empty();
			$("#tdLinkNoticia"+index).html('<a href="javascript:editarLink('+index+')">Editar Link</a>');
		}
	}
	function cancelarLink(index){
		var i=index+1;
		$("#linkNoticia"+i).val(arrayMagazine [ index ].fcNombre);
		$("#linkNoticia"+i).prop("disabled", true);
		$("#tdLinkNoticia"+index).empty();
		$("#tdLinkNoticia"+index).html('<a href="javascript:editarLink('+index+')">Editar Link</a>');
	}
	function editarLink(index){
		var i=index+1;
		 $("#linkNoticia"+i).prop("disabled", false); 
		 $("#tdLinkNoticia"+index).empty();
		 $("#tdLinkNoticia"+index).html('<a href="javascript:guardarLink('+index+')">Guardar</a> <a href="javascript:cancelarLink('+index+')">Cancelar</a>');	
	}

	function sendNewsLetter(formNewsLetter){
		var checado=$('input[name=audiencia]:checked').val();
		if(checado != undefined){
			$("#valorChecadoHidden").val(checado);
			$("#dispatchHidden").val("SEND_NEWSLETTER");
			$("#errorSubbmit").html("");
			formNewsLetter.submit();
		}else{
			$("#errorSubbmit").html("Favor de seleccionar una audiencia");
		}
		
	}
	
	$(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });
    
    
	function guardaNotas(){
		arrayMagazine [ 0 ].fcNombreMagazine="magazine-newsletter";
		var jsonMagazineTXT = '{"listNotas":'+JSON.stringify(arrayMagazine)+'}';
		$.ajax({
			type: "post",
			dataType : "text",
			contentType: "application/json",
			data:jsonMagazineTXT,							
			url: urlBDContent,
			async: false,
			cache:false,
			success: function(data) {
				if(data == "success"){
					console.log("guardaNotas !=failure");
					$("#loading").hide();
					$("#div_guarda_notas").show();
					$("#sendNewsLetter").show();
					$( "#guarda_notas" ).prop( "disabled", true );
					$('#noticias').html ( '' );
					$('#titulo_seccion').html ( '' );
					$('#page-selection').html ( '' );
					
					vistaPrevia(arrayMagazine);
				}else{
					console.log("guardaNotas == failure");
					$("#loading").hide();
					$("#div_guarda_notas").show();
					$( "#guarda_notas" ).prop( "disabled", false );
				}
			}
		});
	}
    
	/*function guardaNotas(){
		var id_magazine = "magazine-newsletter";
		var flag=false;
		$.ajax({
			type: "post",
			dataType: "text",			
			data:{ 
				id_magazine : id_magazine,
				listNotas : JSON.stringify(arrayMagazine)
			},							
			url: urlBDContent,
			//async: false,
			cache:false,
			success: function(data) {
				if(data != "failure"){
					console.log("guardaNotas !=failure");
					$("#loading").hide();
					$("#div_guarda_notas").show();
					$("#sendNewsLetter").show();
					$( "#guarda_notas" ).prop( "disabled", true );
					$('#noticias').html ( '' );
					$('#titulo_seccion').html ( '' );
					$('#page-selection').html ( '' );
					
					vistaPrevia(arrayMagazine);
				}else{
					console.log("guardaNotas == failure");
					$("#loading").hide();
					$("#div_guarda_notas").show();
					$( "#guarda_notas" ).prop( "disabled", false );
				}
			}
		});
	}*/
	
	function vistaPrevia(arrayMagazine) {
		var item="<table>"+
		   	    "<tr>"+
				"<td colspan='2'  style='padding:0 15px 15px 15px;'> <table width='570' border='0' cellspacing='0' cellpadding='0' style='background:white;'><tr>"+
				"<td valign='top' style='padding:12px; width:196px;'><img style='display:block; line-height:0px' border='0' src='http://www.unotv.com$IMG_PRINCIPAL$' width='196' height='118' alt='placeholder' /></td>"+
				"<td valign='top'>"+
				"<h2 style='font-family:Arial, Helvetica, sans-serif; font-size:14px; color:black; font-weight:bold; margin:12px 0 0 0'>$TITULO_NOTA$</h2>"+
				"<span style='font-family:Arial, Helvetica, sans-serif; font-size:10px; color:#666;'>$FECHA$</span>"+
				"<span style='font-family:Arial, Helvetica, sans-serif; font-size:10px; color:white; background:#F60; text-transform:uppercase; padding:3px;'>$SECCION$</span>"+
				"<p style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#333; margin:5px 0'>$DESCRIPCION_CORTA$</p>"+
				"<p style='margin:0 0 12px 0;'><a href='$URL_NOTA$' style='font-family:Arial, Helvetica, sans-serif; font-size:10px; color:#F60; text-decoration:underline; text-transform:uppercase;'>Leer noticia</a></p></td>"+
				"</tr>"+
				"</table></td>"+
				"</tr>"+
				"</table>";
		var tmp="";		
		for ( var i = 0; i < arrayMagazine.length ; i++ ){
				tmp = item;
				$.each( arrayMagazine [ i ], function( key, val ) {
					
					if ( key == 'fcIdCategoria' )
						tmp = tmp.replace("$SECCION$", val );
					if ( key == 'fcLinkDetalle' ){
						var link="";
						if(arrayMagazine [ i ].fcNombre.indexOf("http")> -1 || arrayMagazine [ i ].fcNombre.indexOf("https") > -1)
							link=arrayMagazine [ i ].fcNombre;
						else
							//link=arrayMagazine [ i ].fcLinkDetalle;
							link=arrayMagazine [ i ].fcLinkDetalle.replace("especialess","especiales");
						
						tmp = tmp.replace("$URL_NOTA$", link );
					}
					if ( key == 'fcImgPrincipal' )
						tmp = tmp.replace("$IMG_PRINCIPAL$", val );
					if ( key == 'fcTitulo' )
						tmp = tmp.replace("$TITULO_NOTA$", val );
					if ( key == 'fcFechaPublicacion' )
						tmp = tmp.replace("$FECHA$", val );
					if ( key == 'fcDescripcion' )
						tmp = tmp.replace("$DESCRIPCION_CORTA$", val );
				});
				$("#htmlNotas").append(tmp);
		}

		var _window = window.open("", "");
		_window.document.write($("#previa").html());
		}


	function regresarHome(formResultadoWS){
		$("#dispatchHidden").val("END_NEWSLETTER");
		formResultadoWS.submit();
	}
	
	
	
	