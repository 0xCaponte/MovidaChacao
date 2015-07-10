var Movida = {

	/**
	 * Agrega un conjunto de puntos al universo aumentado.
	 *
	 * @param {Object} un array de puntos con el siguiente formato:
	 {
	 "latitude" : double,
	 "longitude" : double,
	 "altitude" : int,
	 "title" : string,
	 "description" : string
	 }
	 */
	newData : function(poiArray) {

		for (var i = 0; i < poiArray.length; i++) {

			var poi = poiArray[i];
			var poiData = {
				"id" : poi["id"],
				"latitude" : poi["latitude"],
				"longitude" : poi["longitude"],
				"altitude" : poi["altitude"],
				"title" : poi["title"],
				"description" : poi["description"],
				"category" : poi["category"]
			};
			World.markerList.push(new Marker(poiData));
		}
		
		for (var i = 0; i < World.markerList.length; i++) {
            World.markerList[i].distanceToUser = World.markerList[i].markerObject.locations[0].distanceToUser();
        }
        
        // Sort by Distance Ascending
        World.markerList.sort(function(a, b) {
                              return a.distanceToUser - b.distanceToUser;
                              });
                              
        PoiRadar.show();
        PoiRadar.setMaxDistance(2000);
        PoiRadar.updatePosition();
	}
};
