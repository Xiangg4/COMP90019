package fragments;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class CustomClusterRenderer extends DefaultClusterRenderer<ClusterMarkerItem> {
    final private int MIN_SIZE = 30;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<ClusterMarkerItem> clusterManager) {
        super(context, map, clusterManager);
        setMinClusterSize(MIN_SIZE);

    }
    @Override
    protected void onBeforeClusterItemRendered(ClusterMarkerItem item, MarkerOptions markerOptions) {
        markerOptions.icon(item.getIcon());
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return cluster.getSize() > MIN_SIZE; // if markers <=5 then not clustering
    }


}
