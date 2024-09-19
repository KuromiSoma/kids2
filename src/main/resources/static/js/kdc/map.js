var canvas;
var mapObj;

google.maps.event.addDomListener(window, 'load', function() {  
	canvas = document.getElementById("map-canvas");  
    var mapOps = {
        zoom: 16,  
        center: new google.maps.LatLng(35.686773, 139.68815),  
        mapTypeId: google.maps.MapTypeId.ROADMAP  
    };  
    mapObj = new google.maps.Map(canvas, mapOps);  
}); 
