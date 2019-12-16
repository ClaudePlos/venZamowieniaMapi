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

        });
}
