package eu.wisebed.wiseui.client.testbedselection.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapUIOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.wisebed.wiseui.shared.wiseml.Coordinate;

public class MapViewImpl extends Composite implements MapView {

    private static MapViewImplUiBinder uiBinder = GWT.create(MapViewImplUiBinder.class);

<<<<<<< HEAD
    interface MapViewImplUiBinder extends UiBinder<Widget, MapViewImpl> {
    }
    private static final int DEFAULT_ZOOM_LEVEL = 2;
    private static final int ZOOM_LEVEL = 9;
    @UiField
    SimplePanel mapContainer;
    private MapWidget mapWidget;
=======
    private static final int ZOOM_LEVEL = 12;
	
	@UiField
	SimplePanel mapContainer;
	
	private MapWidget mapWidget;
	
>>>>>>> c1e550e1c2d9d78cf7e0f0e785c229680f9ffe85
    private Marker testbedMarker;
    private InfoWindow testbedInfoWindow;
    private Coordinate coordinate;
    private String title;
    private String description;

    public MapViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        Maps.loadMapsApi("", "2", false, new Runnable() {

            @Override
            public void run() {
                initMap();
            }
        });
    }

    @Override
    public void setPresenter(final Presenter presenter) {
    }

    public void initMap() {
        final Size size = Size.newInstance(100, 100);
        final MapUIOptions options = MapUIOptions.newInstance(size);
        options.setScrollwheel(true);
        options.setMapTypeControl(true);
        options.setHybridMapType(true);
        options.setLargeMapControl3d(true);
        options.setPhysicalMapType(true);
        options.setSatelliteMapType(true);
        options.setScaleControl(true);
        options.setDoubleClick(true);
        options.setNormalMapType(true);
        options.setKeyboard(true);

        mapWidget = new MapWidget();
        mapWidget.setUI(options);
        mapWidget.setSize("100%", "100%");
        mapWidget.setContinuousZoom(true);
        mapContainer.add(mapWidget);

        updateMap();
    }

    private void updateMap() {
        if (testbedMarker != null && testbedInfoWindow != null) {
            mapWidget.removeOverlay(testbedMarker);
            testbedInfoWindow.close();
        }

        LatLng center = LatLng.newInstance(0.0, 0.0);
        if (coordinate != null) {
            center = convert(coordinate);
            testbedMarker = new Marker(center);

            mapWidget.addOverlay(testbedMarker);
            mapWidget.setZoomLevel(ZOOM_LEVEL);

            final HTML htmlWidget = new HTML("<b>" + title + "</b><p>" + description + "</p>");
            testbedInfoWindow = mapWidget.getInfoWindow();
            testbedInfoWindow.open(testbedMarker, new InfoWindowContent(htmlWidget));
        } else {
            mapWidget.setZoomLevel(DEFAULT_ZOOM_LEVEL);
        }
        mapWidget.setCenter(center);
        mapWidget.checkResizeAndCenter();
    }

    @Override
    public void setTestbedCoordinate(final Coordinate coordinate, final String title, final String description) {
        this.coordinate = coordinate;
        this.title = title;
        this.description = description;

        if (mapWidget != null) {
            updateMap();
        }
    }

    public static LatLng convert(final Coordinate coordinate) {
        final double x = coordinate.getX();
        final double y = coordinate.getY();
        return LatLng.newInstance(x, y);
    }
}
