/**
 * 
 */
//$(function(){
function getData() {
	$.ajax({
	    url: "https://data.sfgov.org/resource/6a9r-agq8.json",
	    type: "GET",
	    data: {
	      "$limit" : 5000,
	      "$$app_token" : "GgWNAcFmnDbmOH66vLqsmycxg"
	    }
	}).done(function(data) {
	  alert("Retrieved " + data.length + " records from the dataset!!!");
	  console.log(data);
	  
	  /*$.getJSON("url_with_json_here", function(data){
		    for (var i = 0, len = data.length; i < len; i++) {
		        console.log(data[i]);
		    }
		});*/
	  
	  $.getJSON(function(data){
		  alert("hi");
		  $.each(data, function(key,value){
				 //if(value.Latitude <= value.Latitude + 5000 && value.Longitude <=value.Longitude) 
					 //window.print(value.Applicant + value.Address);
			  window.print(key);
			  });
	  });
	});
}