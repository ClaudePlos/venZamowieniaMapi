var parameters = {};

function load(){
	var url_string = window.location.href
        var url = new URL(url_string);
        var gzId = url.searchParams.get("gzId");
        var dietId = url.searchParams.get("dietId");
        var forDay = url.searchParams.get("forDay");
        console.log(gzId + " " + dietId  + " " + forDay);
        
	parameters.dietId = dietId;
	parameters.forDay = forDay;
        
        generateDisabilityForWorkersOnSk(parameters);
}

// Create the XHR object.
function createCORSRequest(method, url) {
  var xhr = new XMLHttpRequest();
  //xhr.withCredentials = true;
  if ("withCredentials" in xhr) {
    // XHR for Chrome/Firefox/Opera/Safari.
    xhr.open(method, url, true);
  } else if (typeof XDomainRequest != "undefined") {
    // XDomainRequest for IE.
    xhr = new XDomainRequest();
    xhr.open(method, url);
  } else {
    // CORS not supported.
    xhr = null;
  }
  return xhr;
}

function getInfAboutJadlospisForDiet(parameters, callback){
	var url = 'http://localhost:8080/venZamowieniaMapi/webresources/mapiServiceRest/getInfAboutWartoscOdzywczaForDiet'; //TODO

        var xhr = createCORSRequest('POST', url);
              xhr.setRequestHeader("Content-Type", "application/json");
              //xhr.setRequestHeader("Authorization", "Basic a2xhdWRpdXN6LnNrb3dyb25za2k6Y0BtZWxocDEx");
        if (!xhr) {
          alert('CORS not supported');
          return;
        }

        // Response handlers.
        xhr.onload = function() {
                      //$("#").fadeOut();
          var text = xhr.responseText;
                      var data = JSON.parse(text);
          callback(null, data);
              }

              xhr.onerror = function() {
                      //$("#").fadeOut();
          alert('Woops, cant get limit Holiday days for works.');
        };
        //console.log(parameters);
        xhr.send(JSON.stringify(parameters));
}



function generateDisabilityForWorkersOnSk(parameters){
	
	 getInfAboutJadlospisForDiet(parameters, function(error, listJadlospis){
           console.log(listJadlospis);
           
           
          var myTableDiv = document.getElementById("divJadlospisTable");
	  myTableDiv.innerHTML = ""; //clear table 
          
          
          	//table head
	  var table = document.createElement('TABLE');
	  table.border = '0';

	  var tableThead = document.createElement('thead');
	  table.appendChild(tableThead);

		//HEAD
	 var trHead = document.createElement('TR');
	 tableThead.appendChild(trHead);
	 //01 cell
	 var tdHead01 = document.createElement('TD');
			 tdHead01.width = '175';
			 tdHead01.setAttribute("style", "font-weight:bold");
			 tdHead01.appendChild(document.createTextNode("Dieta Kod"));
			 trHead.appendChild(tdHead01);
	 //02 cell
	 var tdHead02 = document.createElement('TD');
			 tdHead02.width = '175';
			 tdHead02.setAttribute("style", "font-weight:bold");
			 tdHead02.appendChild(document.createTextNode("Dieta Nazwa"));
			 trHead.appendChild(tdHead02);
	 //03 cell
	 var tdHead = document.createElement('TD');
			 tdHead.width = '175';
			 tdHead.setAttribute("style", "font-weight:bold");
			 tdHead.appendChild(document.createTextNode("Data"));
			 trHead.appendChild(tdHead);

	 //04 cell
	 var tdHead = document.createElement('TD');
			 tdHead.width = '175';
			 tdHead.setAttribute("style", "font-weight:bold");
			 tdHead.appendChild(document.createTextNode("Energia kcal"));
			 trHead.appendChild(tdHead);

	 var tdHead = document.createElement('TD');
			 tdHead.width = '175';
			 tdHead.setAttribute("style", "font-weight:bold");
			 tdHead.appendChild(document.createTextNode("Białko ogółem"));
			 trHead.appendChild(tdHead);

	 var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Tłuszcz"));
			 trHead.appendChild(tdHead);
			 
	var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Węglowodany ogółem"));
			 trHead.appendChild(tdHead);
			 
	var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Błonnik pokarmowy"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Sód"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Cholesterol"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Żelazo"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Fenyloalanina"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Witamina A (ekwiw.retinolu)"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Witamina C"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Tiamina"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Sacharoza"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Potas"));
 			trHead.appendChild(tdHead);      
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Fosfor"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("Wapń"));
 			trHead.appendChild(tdHead);                 
                        
        for (var i in listJadlospis) {
            
            var tableBody = document.createElement('TBODY');
            table.appendChild(tableBody);
            var tr = document.createElement('TR');
 			 
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].dietaKod));
            tr.appendChild(td);

            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].dietaNazwa));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].dObr));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w1 + " kcal"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w2+ " g"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w3 + " g"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w4 + " g"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w5 + " g"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w6 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w7 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w8 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w9 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w10 + " ug"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w11 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w12 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w13 + " g"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w14 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w15 + " mg"));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w16 + " mg"));
            tr.appendChild(td);

            tableBody.appendChild(tr);
            
        }  
        
        myTableDiv.appendChild(table);


        });
}
