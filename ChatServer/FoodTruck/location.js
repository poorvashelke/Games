
var x = document.getElementById("demo");
var a = document.getElementById("file");

var map;
//  SF values delete afterwords(for testing purpose)
var latitude = 37.7678524427122;
var longitude = -122.466104892532;

function getLocation() {
    if (navigator.geolocation) {
    	navigator.geolocation.getCurrentPosition(showTrucksOnMap, showError);
    } else {
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
}

/*function showPosition1(position) {
	document.write("Latitude: " + position.coords.latitude + 
    "<br>Longitude: " + position.coords.longitude+"<br>");
	getData();
}*/

function getData() {
	$.ajax({
	    url: "https://data.sfgov.org/resource/6a9r-agq8.json",
	    type: "GET",
	    data: {
	      "$limit" : 5000,
	      "$$app_token" : "GgWNAcFmnDbmOH66vLqsmycxg"
	    }
	}).done(function(data) {
	  
	  $.each(data, function(key,value){				 
		  if((latitude + 0.3) < value.latitude
				  || (latitude - 0.3) > value.latitude					 
				  || (longitude + 0.3) < value.longitude
				  || (longitude - 0.3) > value.longitude) {
			  return;}
		  var truckCenter = new google.maps.LatLng(value.latitude, value.longitude);
		  var marker = new google.maps.Marker({position:truckCenter});
		 /* var marker = new google.maps.Marker({
			  position: truckCenter,
			  icon: 1
			  //icon: "pinkball.png"
				  });*/
		  marker.setMap(map);
		  });	  
	  });
}

function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            x.innerHTML = "User denied the request for Geolocation."
            break;
        case error.POSITION_UNAVAILABLE:
            x.innerHTML = "Location information is unavailable."
            break;
        case error.TIMEOUT:
            x.innerHTML = "The request to get user location timed out."
            break;
        case error.UNKNOWN_ERROR:
            x.innerHTML = "An unknown error occurred."
            break;
    }
}

 function showTrucksOnMap(position) {
	 //latitude = position.coords.latitude;
	 //longitude = position.coords.longitude;
	 
	 var mapOptions = {
	    center: new google.maps.LatLng(latitude, longitude),
	    zoom:14,
	    type: 'Food truck',
	    mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	  
	  var userCenter = new google.maps.LatLng(latitude, longitude);
	  var marker = new google.maps.Marker({position:userCenter, animation: google.maps.Animation.BOUNCE});	  
	  map = new google.maps.Map(document.getElementById("googleMap"),mapOptions);
	  marker.setMap(map);
	  
	  getData();
}
 

