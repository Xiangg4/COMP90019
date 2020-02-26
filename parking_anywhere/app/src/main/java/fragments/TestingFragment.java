package fragments;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import sqlite.model.ParkingSensor;
import view_models.ParkingBayViewModel;

public class TestingFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ParkingBayViewModel viewModel;



    public TestingFragment() { }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_blank, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }


    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ParkingBayViewModel.class);
        viewModel.init(567);
        //viewModel.getParkingSensor().observe(this, user -> updateUI(user));//If the data in database changed, automatically update UI
        //viewModel.getQuarterHour().observe(this, user -> updateUI(user));
    }

    private void updateUI(@Nullable List<ParkingSensor> user){
        //Getting the necessary data for updating UI here
        for(int i = 0; i< user.size() ; i++){
            ParkingSensor data = user.get(i);
            int bay_id = data.getBay_id();
            Log.d("fragment",bay_id+"");

        }



        }


}
