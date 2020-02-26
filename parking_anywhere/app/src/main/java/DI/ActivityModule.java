package DI;

import com.example.parking_anywhere.MainActivity;
import com.example.parking_anywhere.ProfileActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract ProfileActivity contributeProfileActivity();

}
