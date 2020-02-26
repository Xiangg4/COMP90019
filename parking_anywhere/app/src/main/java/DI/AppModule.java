package DI;

import android.app.Application;

import androidx.room.Room;

import com.example.parking_anywhere.GetData;
import com.example.parking_anywhere.RetrofitClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import repositories.ParkingRepository;
import sqlite.ParkingBayDatabase;
import sqlite.dao.ParkingBayDAO;
import sqlite.dao.ParkingLocationDAO;
import sqlite.dao.ParkingLocationWithRestrictionDAO;
import sqlite.dao.ParkingSensorDAO;
import sqlite.dao.ParkingSensorWithRestrictionDAO;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    ParkingBayDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                ParkingBayDatabase.class, "parking_db")
                .allowMainThreadQueries()
                .build();
    }
    @Provides
    @Singleton
    ParkingBayDAO provideParkingBayDAO(ParkingBayDatabase database) { return database.getParkingBayDAO(); }

    @Provides
    @Singleton
    ParkingSensorDAO provideParkingSensorDAO(ParkingBayDatabase database) { return database.getParkingSensorDAO(); }

    @Provides
    @Singleton
    ParkingLocationDAO provideParkingLocationDAO(ParkingBayDatabase database) { return database.getParkingLocationDAO(); }


    @Provides
    @Singleton
    ParkingLocationWithRestrictionDAO provideParkingLocationWithRestrictionDAO(ParkingBayDatabase database) { return database.getParkingLocationWithRestrictionDAO(); }

    @Provides
    @Singleton
    ParkingSensorWithRestrictionDAO provideParkingSensorWithRestrictionDAO(ParkingBayDatabase database) { return database.getParkingSensorWithRestrictionDAO(); }


    // --- REPOSITORY INJECTION ---

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    ParkingRepository provideParkingRepository(GetData webservice, ParkingBayDAO parkingBayDAO, ParkingSensorDAO parkingSensorDAO, ParkingLocationDAO parkingLocationDAO, ParkingLocationWithRestrictionDAO parkingLocationWithRestrictionDAO, ParkingSensorWithRestrictionDAO parkingSensorWithRestrictionDAO, Executor executor) {
        return new ParkingRepository(webservice, parkingBayDAO,parkingSensorDAO, parkingLocationDAO,parkingLocationWithRestrictionDAO, parkingSensorWithRestrictionDAO, executor);
    }
    // --- NETWORK INJECTION ---



    @Provides
    @Singleton
    GetData provideApiWebservice() {
        return RetrofitClient.getRetrofitInstance().create(GetData.class);
    }




}
