
function load(){
	var url_string = window.location.href
        var url = new URL(url_string);
        var gzId = url.searchParams.get("gzId");
        var dietId = url.searchParams.get("dietId");
        var forDay = url.searchParams.get("forDay");
        console.log(gzId + " " + dietId  + " " + forDay);
}
