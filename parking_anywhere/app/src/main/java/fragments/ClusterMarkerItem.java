package fragments;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarkerItem implements ClusterItem {
    private String title;
    private String snippet;
    private LatLng latLng;
    private String color;
    private BitmapDescriptor icon;
    private int type;
    private int DURATION_ALL = 0;//for testing
    private int DURATION_15_MIN = 15;//for testing
    private int DURATION_30_MIN = 30;//for testing
    private int DURATION_60_MIN = 60;//for testing
    private int DURATION_120_MIN = 120;//for testing
    private int DURATION_240_MIN = 240;//for testing

    public ClusterMarkerItem(String title, String snippet, LatLng latLng, String color, int type) {
        this.title = title;
        this.snippet = snippet;
        this.latLng = latLng;
        this.color = color;
        this.type = type;

        this.icon = getMarkerIconByColor(color);
    }


    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public String getColor()
    {
        return color;
    }

    public BitmapDescriptor getIcon()
    {
        return icon;
    }

    public BitmapDescriptor getMarkerIconByColor(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    public int getType()
    {
        return type;
    }

    public void setSnippet(String inputsnippet){
        this.snippet = inputsnippet;
    }

    public void setTitle(String inputtitle){
        this.title = inputtitle;
    }

    public void setLatLng(LatLng inputlatlng){
        this.latLng = inputlatlng;
    }

    public void setColor(String inputcolor){
        this.color = inputcolor;
    }

    public void setType(int inputtype)
    {
        this.type = inputtype;
    }

    public void setIcon(BitmapDescriptor bitmapDescriptor)
    {
        this.icon = bitmapDescriptor;
    }
}
