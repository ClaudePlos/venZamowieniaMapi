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
         
         //00 lp
	 var tdHead01 = document.createElement('TD');
			 tdHead01.width = '175';
			 tdHead01.setAttribute("style", "font-weight:bold");
			 tdHead01.appendChild(document.createTextNode("LP"));
			 trHead.appendChild(tdHead01);
         
	 //01 cell
	 var tdHead01 = document.createElement('TD');
			 tdHead01.width = '175';
			 tdHead01.setAttribute("style", "font-weight:bold");
			 tdHead01.appendChild(document.createTextNode("Nazwa Skladnik"));
			 trHead.appendChild(tdHead01);
	 //02 cell
	 var tdHead02 = document.createElement('TD');
			 tdHead02.width = '175';
			 tdHead02.setAttribute("style", "font-weight:bold");
			 tdHead02.appendChild(document.createTextNode("Posilek Kod"));
			 trHead.appendChild(tdHead02);
	 //03 cell
	 var tdHead = document.createElement('TD');
			 tdHead.width = '175';
			 tdHead.setAttribute("style", "font-weight:bold");
			 tdHead.appendChild(document.createTextNode("Rodzaj"));
			 trHead.appendChild(tdHead);

	 //04 cell
	 var tdHead = document.createElement('TD');
			 tdHead.width = '175';
			 tdHead.setAttribute("style", "font-weight:bold");
			 tdHead.appendChild(document.createTextNode("Ilość"));
			 trHead.appendChild(tdHead);

	 var tdHead = document.createElement('TD');
			 tdHead.width = '175';
			 tdHead.setAttribute("style", "font-weight:bold");
			 tdHead.appendChild(document.createTextNode("Gramatura"));
			 trHead.appendChild(tdHead);

	 var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("jm Gramatura Dania"));
			 trHead.appendChild(tdHead);
			 
	var tdHead = document.createElement('TD');
 			tdHead.width = '175';
 			tdHead.setAttribute("style", "font-weight:bold");
 			tdHead.appendChild(document.createTextNode("jm Kod"));
			 trHead.appendChild(tdHead);
			 
	
        for (var i in listJadlospis) {
            
            listJadlospis.sort(function(a, b){
		    if(a.lp < b.lp) { return -1; }
		    if(a.lp > b.lp) { return 1; }
		    return 0;
		})
            
            var tableBody = document.createElement('TBODY');
            table.appendChild(tableBody);
            var tr = document.createElement('TR');
 			 
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].lp));
            tr.appendChild(td);

            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].nazwaSkladnik));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].posilekKod));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].rodzaj));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].ilosc));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].gramatura));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].jmGramaturaDania));
            tr.appendChild(td);
            
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(listJadlospis[i].jmKod));
            tr.appendChild(td);

            tableBody.appendChild(tr);
            
        }  
        
        myTableDiv.appendChild(table);


        });
}
