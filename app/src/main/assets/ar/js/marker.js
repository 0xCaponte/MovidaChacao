function Marker(poiData) {
    this,distanceToUser = 0;
    this.poiData = poiData;
    
    this.isSelected = false;


    var markerLocation = new AR.GeoLocation(poiData.latitude, poiData.longitude, poiData.altitude);

    this.markerDrawable_idle = new AR.ImageDrawable(World.markerDrawable_idle, 1.5, {
        zOrder: 0,
        enabled: true,
        onClick: Marker.prototype.getOnClickTrigger(this)
    });

    this.markerDrawable_selected = new AR.ImageDrawable(World.markerDrawable_selected, 1.5, {
        zOrder: 0,
        enabled: false,
        onClick: Marker.prototype.getOnClickTrigger(this)
    });


    this.titleLabel = new AR.Label(poiData.title, 0.7, {
        zOrder: 1,
        offsetY: 0.35,
        offsetX: 0.55,
        scale: 0.6,
        style: {
            textColor: '#FFFFFF',
            fontStyle: AR.CONST.FONT_STYLE.BOLD
        }
    });

	this.categoryLabel = new AR.Label(poiData.category, 0.4, {
        zOrder: 1,
        offsetY: -0.05,
        offsetX: 0.50,
        style: {
            textColor: '#FFFFFF'
        }
    });

    this.descriptionLabel = new AR.Label(poiData.description, 0.4, {
        zOrder: 1,
        offsetY: -0.35,
        offsetX: 0.50,
        style: {
            textColor: '#FFFFFF'
        }
    });

    this.radarCircle = new AR.Circle(0.03, {
                                     horizontalAnchor: AR.CONST.HORIZONTAL_ANCHOR.CENTER,
                                     opacity: 0.8,
                                     style: {
                                     fillColor: "#ffffff"
                                     }
                                     });
    this.radardrawables = [];
    this.radardrawables.push(this.radarCircle);

    this.markerObject = new AR.GeoObject(markerLocation, {
        drawables: {
            cam: [this.markerDrawable_idle, this.markerDrawable_selected, this.titleLabel, this.descriptionLabel, this.categoryLabel],
                indicator: this.directionIndicatorDrawable,
                radar: this.radardrawables
        }
    });

    return this;
}

Marker.prototype.getOnClickTrigger = function(marker) {

    return function() {

        if (marker.isSelected) {

            Marker.prototype.setDeselected(marker);

        } else {

            World.onScreenClick(marker);
            World.onMarkerSelected(marker);
            Marker.prototype.setSelected(marker);
        }

        return true;
    };
};

Marker.prototype.setSelected = function(marker) {

    marker.isSelected = true;

    marker.markerDrawable_idle.enabled = false;
    marker.markerDrawable_selected.enabled = true;
};

Marker.prototype.setDeselected = function(marker) {

    marker.isSelected = false;

    marker.markerDrawable_idle.enabled = true;
    marker.markerDrawable_selected.enabled = false;
};
