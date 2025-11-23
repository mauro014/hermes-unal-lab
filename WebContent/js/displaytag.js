/**
*
* Funciones necesarias para complementar la barra de navegacion generada por la
* libreria de presentacion displaytag usada par ala generacion de las tabals de
* resultados de la aplicacion SAWW.
*/

function write_page_selected ( param0 ){

    var totalResultsS = new String();
    totalResultsS = document.getElementById("totalRes").value ;

    if (totalResultsS.length > 3 ) totalResultsS = totalResultsS.replace(",","");
    var totalResults = new Number( totalResultsS );

    var fin = new Number( eval ( ( param0 -1 ) * 20 + 20 ) );
    var init = new Number ( eval ( ( param0 -1 ) * 20 + 1 ) );

    if ( eval ( totalResults > 1 ) ){
        if ( eval( fin > totalResults && eval( totalResults != 1 ) ) ) {
         document.write ( init + " - " + totalResults +"  de  " + totalResults );
        } else if (eval( totalResults <= 20) && eval( totalResults != 1 ) ){
            document.write ( totalResults + "  de  " + totalResults );
        } else if (eval( totalResults > 20 ) && eval( fin <= totalResults ) ) {
            document.write ( init + " - " + fin +"  de  " + totalResults );
        } else {
         document.write ( init + " - " + fin +"  de  " + totalResults );
        }
    }
}

function write_page_link ( param0 ){

    var totalResultsS = new String();
    totalResultsS = document.getElementById("totalRes").value ;

    if (totalResultsS.length > 3 ) totalResultsS = totalResultsS.replace(",","");
    var totalResults = new Number( totalResultsS );

    var fin = new Number( eval ( ( param0 -1 ) * 20 + 20 ) );
    var init = new Number ( eval ( ( param0 -1 ) * 20 + 1 ) );

    if ( eval ( totalResults > 1 ) ){
        if ( eval( fin > totalResults && eval( totalResults != 1 ) ) ) {
         document.write ( init + " - " + totalResults +"  de  " + totalResults );
        } else if (eval( totalResults <= 20) && eval( totalResults != 1 ) ){
            document.write ( totalResults + "  de  " + totalResults );
        } else if (eval( totalResults > 20 ) && eval( fin <= totalResults ) ) {
            document.write ( init + " - " + fin +"  de  " + totalResults );
        } else {
        document.write ( init + " - " + fin +"  de  " + totalResults );
        }
    }
}


/** Redirecciona la pagina segun el rango de paginas seleccionado en el select de paginación */
function goLink(select){
	window.location = select.value;
}

/** esta funcion resetea los valores de los campos del formualrio de bsuqueda */
function resetForm(formName) {
	var form = document.forms[formName];

	for (x= 0; x < document.forms[formName].elements.length ; x++) {
		if (form.elements[x].type == 'text' ||
			form.elements[x].type == 'textarea') {
			form.elements[x].value="";
		}
		if (form.elements[x].type == 'select-one' ) {
			form.elements[x].options[0].selected=true;
		}
	}
	return false;
}