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
	var url = 'http://localhost:8080/venZamowieniaMapi/webresources/mapiServiceRest/getInfAboutJadlospisForDiet'; //TODO

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
			 tdHead.appendChild(document.createTextNode("w1"));
			 trHead.appendChild(tdHead);

	 var tdHead = document.createElement('TD');
			 tdHead.width = '175';
			 tdHead.setAttribute("style", "font-weight:bold");
			 tdHead.appendChild(document.createTextNode("w2"));
			 trHead.appendChild(tdHead);

	 var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w3"));
			 trHead.appendChild(tdHead);
			 
	var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w4"));
			 trHead.appendChild(tdHead);
			 
	var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w5"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w6"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w7"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w8"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w9"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w10"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w11"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w12"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w13"));
 			trHead.appendChild(tdHead); 
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w14"));
 			trHead.appendChild(tdHead);      
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w15"));
 			trHead.appendChild(tdHead);
                        
        var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("w16"));
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
            td.appendChild(document.createTextNode(listJadlospis[i].w1));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w2));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w3));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w4));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w5));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w6));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w7));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w8));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w9));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w10));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w11));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w12));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w13));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w14));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w15));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].w16));
            tr.appendChild(td);

            tableBody.appendChild(tr);
            
        }  
        
        myTableDiv.appendChild(table);


        });
}
