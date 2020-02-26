package fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.parking_anywhere.DescriptionFragment;
import com.example.parking_anywhere.PreferenceManager;
import com.example.parking_anywhere.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import sqlite.dao.ParkingSensorWithRestrictionDAO;
import sqlite.model.ParkingSensor;
import view_models.ParkingBayViewModel;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class mapFragement extends Fragment implements OnMapReadyCallback {

    private View mapView;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private PlacesClient placesClient;
    private LocationCallback locationCallback;
    private Location mLastKnownLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final float DEFAULT_ZOOM = 18;
    private final float SMALLER_ZOOM = 19;
    private int DURATION_ALL = 0;//for testing
    private int DURATION_15_MIN = 15;//for testing
    private int DURATION_30_MIN = 30;//for testing
    private int DURATION_60_MIN = 60;//for testing
    private int DURATION_120_MIN = 120;//for testing
    private int DURATION_240_MIN = 240;//for testing
    private DescriptionFragment description;
    private CardView cardView;
    private List<ClusterMarkerItem> mClusterMarkerItem = new ArrayList<ClusterMarkerItem>();
    private ClusterManager<ClusterMarkerItem> mClusterManager;
    private ParkingSensorWithRestrictionDAO.filteredBays recommandBay;

    //liuyi's test
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ParkingBayViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureDagger();
        try {
            this.configureViewModel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Nullable
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_map, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        placesClient = Places.createClient(getContext());
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        return view;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        initClusterManager();

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
        }

        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });
        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(getActivity(), 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null)
                            {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(getActivity(), "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @SuppressLint("MissingPermission")
    private void getDeviceLocation2() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null)
                            {
                                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(getActivity(), "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public ParkingSensorWithRestrictionDAO.filteredBays getRecommendation(){
        double[] latlon = getCurrentLocation();
        double currlat = latlon[0];
        double currlon = latlon[1];
        return viewModel.getRecommendation(0, currlat, currlon);
    }


    public void highlight(DescriptionFragment inputdescription)
    {
        ParkingSensorWithRestrictionDAO.filteredBays data = getRecommendation();

        List<ClusterMarkerItem> clusterMarkerItems = this.mClusterMarkerItem;
        Double lat = data.lat;
        Double lon = data.lon;
        String id = data.iD + "";
        LatLng parking = new LatLng(lat, lon);
        cardView.setVisibility(View.VISIBLE);
        moveMapCamera(parking);
        for(int i = 0; i < clusterMarkerItems.size(); i++)
        {

            ClusterMarkerItem clusterItem = clusterMarkerItems.get(i);

            if(clusterItem.getTitle().equals(id))
            {
                String desc = clusterItem.getSnippet();
                String bayId = clusterItem.getTitle();

                LatLng location = clusterItem.getPosition();
                String price = "$7";

                String occupation = desc.split(";")[0];



                String info = desc.split(";")[1];
                String[] descList = info.split(",");
                inputdescription.showDesc(occupation, price, descList);
                inputdescription.setBayInfo(bayId,location);
            }
        }
    }

    public double[] getCurrentLocation()
    {
        double[] location = new double[2];
        getDeviceLocation2();
        location[0] = mLastKnownLocation.getLatitude();
        location[1] = mLastKnownLocation.getLongitude();
        return location;
    }

    public void moveMapCamera(double lat, double lon)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), SMALLER_ZOOM));
    }

    public void moveMapCamera(LatLng position)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, SMALLER_ZOOM));
    }

    public void parkingSensorMarker(List<ParkingSensor> pos) {
        if (mMap != null) {
            //display on map
            if (!pos.isEmpty()) {
                for (int i = 0; i < pos.size(); i++) {
                    double lat = Double.parseDouble(pos.get(i).getLat());
                    double lon = Double.parseDouble(pos.get(i).getLon());
                    LatLng parking = new LatLng(lat, lon);
                    mMap.addMarker(new MarkerOptions().position(parking).title(pos.get(i).getBay_id() + " "));
                }
            }
        }
    }

    public void clearMarkers() {
        mMap.clear();
    }

    public void setDescription(DescriptionFragment descriptionfragement)
    {
        this.description = descriptionfragement;
    }

    public void setCardView(CardView cardview)
    {
        this.cardView = cardview;
    }

    public void initClusterManager() {
        mClusterManager = new ClusterManager<>(getActivity(), mMap);

        CustomClusterRenderer renderer = new CustomClusterRenderer(getActivity(), mMap, mClusterManager);
        mClusterManager.setRenderer(renderer);


        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterMarkerItem>() {
            @Override
            public boolean onClusterItemClick(ClusterMarkerItem clusterItem) {

                cardView.setVisibility(View.VISIBLE);

                String desc = clusterItem.getSnippet();

                String id = clusterItem.getTitle();

                String bayId = clusterItem.getTitle();

                Log.d("myCoord",bayId+"");
                LatLng location = clusterItem.getPosition();
                Log.d("clusterItem", String.valueOf(clusterItem));
                Log.d("onMarkerClick", clusterItem.getTitle());

                String price = "$7";


                String occupation = desc.split(";")[0];
                Log.d("descSplit", occupation);
                Log.d("descSplit", desc);
                String info = desc.split(";")[1];
                String[] descList = info.split(",");

                Log.d("clusterDesc", desc);
                Log.d("clusterDesc", descList.toString());
                description.showDesc(occupation, price, descList);

                description.setBayInfo(bayId,location);
                return false;
            }
        });
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
    }

    //liuyi's test
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }
    private void configureViewModel() throws InterruptedException {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ParkingBayViewModel.class);
        viewModel.init(567);

        viewModel.filterBaysByDuration(DURATION_ALL).observe(this, user -> updateInitialBays(user));

        viewModel.filterBaysByDuration(DURATION_15_MIN).observe(this, user -> updateUI_15(user));
        viewModel.filterBaysByDuration(DURATION_30_MIN).observe(this, user -> updateUI_30(user));
        viewModel.filterBaysByDuration(DURATION_60_MIN).observe(this, user -> updateUI_60(user));
        viewModel.filterBaysByDuration(DURATION_120_MIN).observe(this, user -> updateUI_120(user));
        viewModel.filterBaysByDuration(DURATION_240_MIN).observe(this, user -> updateUI_240(user));

    }


    private void updateRecommendation(@Nullable ParkingSensorWithRestrictionDAO.filteredBays bay){
        this.recommandBay = bay;
    }

    private void updateUI_15(@Nullable List<ParkingSensorWithRestrictionDAO.filteredBays> user) {
        //Getting the necessary data for updating UI here
        for (int i = 0; i < user.size(); i++) {

            ParkingSensorWithRestrictionDAO.filteredBays data = user.get(i);
            Double lat = data.lat;
            Double lon = data.lon;
            if(data.lat != 0.0){
                LatLng parking = new LatLng(lat, lon);
                if (data.isDisabled == true) {
                    if (data.occupation.equals("Available")) { //disabled with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#00FF7F", DURATION_15_MIN)); //green
                    } else { //disabled without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FFFF33", DURATION_15_MIN)); //yellow
                    }
                } else {
                    if (data.occupation.equals("Available")) { //normal with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FF0033", DURATION_15_MIN)); //red
                    } else { //normal without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#3399FF", DURATION_15_MIN)); //blue

                    }
                }
            }
        }
    }
    private void updateUI_30(@Nullable List<ParkingSensorWithRestrictionDAO.filteredBays> user) {
        //Getting the necessary data for updating UI here
        for (int i = 0; i < user.size(); i++) {
            ParkingSensorWithRestrictionDAO.filteredBays data = user.get(i);
            Double lat = data.lat;
            Double lon = data.lon;
            LatLng parking = new LatLng(lat, lon);
            if(data.lat != 0.0){

                if (data.isDisabled == true){
                    if(data.occupation.equals("Available")){ //disabled with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "",data.occupation+";"+data.combinedDescriptions+"",
                                parking, "#00FF7F", DURATION_30_MIN)); //green
                    }
                    else { //disabled without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "",data.occupation+";"+data.combinedDescriptions+"",
                                parking, "#FFFF33", DURATION_30_MIN)); //yellow
                    }
                }
                else {
                    if (data.occupation.equals("Available")) { //normal with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FF0033", DURATION_30_MIN)); //red
                    } else { //normal without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#3399FF", DURATION_30_MIN)); //blue

                    }
                }
            }
        }
    }
    private void updateUI_60(@Nullable List<ParkingSensorWithRestrictionDAO.filteredBays> user) {
        //Getting the necessary data for updating UI here
        for (int i = 0; i < user.size(); i++) {
            ParkingSensorWithRestrictionDAO.filteredBays data = user.get(i);
            Double lat = data.lat;
            Double lon = data.lon;
            if(data.lat != 0.0){

                LatLng parking = new LatLng(lat, lon);
                if (data.isDisabled == true) {
                    if (data.occupation.equals("Available")) { //disabled with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#00FF7F", DURATION_60_MIN)); //green
                    } else { //disabled without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FFFF33", DURATION_60_MIN)); //yellow
                    }
                } else {
                    if (data.occupation.equals("Available")) { //normal with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FF0033", DURATION_60_MIN)); //red
                    } else { //normal without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#3399FF", DURATION_60_MIN)); //blue

                    }
                }
            }
        }
    }
    private void updateUI_120(@Nullable List<ParkingSensorWithRestrictionDAO.filteredBays> user) {
        //Getting the necessary data for updating UI here
        for (int i = 0; i < user.size(); i++) {
            ParkingSensorWithRestrictionDAO.filteredBays data = user.get(i);
            Double lat = data.lat;
            Double lon = data.lon;
            if(data.lat != 0.0){

                LatLng parking = new LatLng(lat, lon);
                if (data.isDisabled == true) {
                    if (data.occupation.equals("Available")) { //disabled with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#00FF7F", DURATION_120_MIN)); //green
                    } else { //disabled without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FFFF33", DURATION_120_MIN)); //yellow
                    }
                } else {
                    if (data.occupation.equals("Available")) { //normal with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FF0033", DURATION_120_MIN)); //red
                    } else { //normal without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                //mClusterManager.addItem(new ClusterMarkerItem(data.iD + "",data.occupation+"/"+data.combinedDescriptions+"",
                                parking, "#3399FF", DURATION_120_MIN)); //blue

                    }
                }
            }
        }
    }
    private void updateUI_240(@Nullable List<ParkingSensorWithRestrictionDAO.filteredBays> user) {
        //Getting the necessary data for updating UI here
        for (int i = 0; i < user.size(); i++) {
            ParkingSensorWithRestrictionDAO.filteredBays data = user.get(i);
            Double lat = data.lat;
            Double lon = data.lon;
            if(data.lat != 0.0){

                LatLng parking = new LatLng(lat, lon);
                if (data.isDisabled == true) {
                    if (data.occupation.equals("Available")) { //disabled with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#00FF7F", DURATION_240_MIN)); //green
                    } else { //disabled without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FFFF33", DURATION_240_MIN)); //yellow
                    }
                } else {
                    if (data.occupation.equals("Available")) { //normal with sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                parking, "#FF0033", DURATION_240_MIN)); //red
                    } else { //normal without sensor
                        mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                //mClusterManager.addItem(new ClusterMarkerItem(data.iD + "",data.occupation+"/"+data.combinedDescriptions+"",
                                parking, "#3399FF", DURATION_240_MIN)); //blue

                    }
                }
            }
        }

    }
    /**Display All Bays Available Without Filtering the Time**/
    private void updateInitialBays(@Nullable List<ParkingSensorWithRestrictionDAO.filteredBays> bay) {
        if(bay != null){
            //get the valid descriptions in list
            for (int i = 0; i < bay.size(); i++) {
                ParkingSensorWithRestrictionDAO.filteredBays data = bay.get(i);
                Double lat = data.lat;
                Double lon = data.lon;
                if(data.lat != 0.0){
                    Log.d("initbaystest", String.valueOf(data.iD));
                    LatLng parking = new LatLng(lat, lon);
                    if (data.isDisabled == true) {
                        if (data.occupation.equals("Available")) { //disabled with sensor
                            mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                    parking, "#00FF7F", DURATION_ALL)); //green
                        } else { //disabled without sensor
                            mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                    parking, "#FFFF33", DURATION_ALL)); //yellow
                        }
                    } else {
                        if (data.occupation.equals("Available")) { //normal with sensor
                            mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                    parking, "#FF0033", DURATION_ALL)); //red
                        } else { //normal without sensor
                            mClusterMarkerItem.add(new ClusterMarkerItem(data.iD + "", data.occupation + ";" + data.combinedDescriptions + "",
                                    //mClusterManager.addItem(new ClusterMarkerItem(data.iD + "",data.occupation+"/"+data.combinedDescriptions+"",
                                    parking, "#3399FF", DURATION_ALL)); //blue

                        }
                    }
                }
            }
        }
        addClusterMarkerItem(DURATION_ALL);
    }

    public void addClusterMarkerItem(int duration)
    {
        List<ClusterMarkerItem> clusterMarkerItems = this.mClusterMarkerItem;
        mClusterManager.clearItems();
        if (mMap != null) {
            if(!clusterMarkerItems.isEmpty()){
                for(int i = 0; i < clusterMarkerItems.size(); i++)
                {
                    ClusterMarkerItem clusterMarkerItem = clusterMarkerItems.get(i);
                    if(clusterMarkerItem.getType() == duration){
                        mClusterManager.addItem(clusterMarkerItem);
                    }
                }
            }
        }
        mClusterManager.cluster();
    }

    /**update combined descriptions to UI here**/
    private void updateCombinedDescription(@Nullable List<String> descriptions) {
        if(descriptions != null){
            //get the valid descriptions in list
            for(String desc:descriptions){
                Log.d("combinedDescription", String.valueOf(desc));
            }
        }
    }

    /**update separate descriptions to UI here
     * the fields can be retrieved separately using this method,
     * so that UI can use different color or font on different fields**/
    private void updateSeparateDescription(@Nullable Map<String,List<String>> descriptions) {
        if(descriptions != null){
            //get the valid descriptions in list
            List<String> days = descriptions.get("days");
            List<String> times = descriptions.get("times");
            List<String> typeDescriptions = descriptions.get("typeDescriptions");
            // holidays represents whether this parking time is applicable on public holidays,
            // we use the "*" mark to indicate it is not applicable on public holidays.
            // extra information like "Note: * represents excluded public holidays"
            // needs to be added to inform users what it means
            List<String> holidays = descriptions.get("holidays");
            for(int i=0; i<days.size(); i++){
                String msg = days.get(i) + "\n" + times.get(i) + "\n"
                        + typeDescriptions.get(i) +holidays.get(i);
            }
        }
    }


}
