package DI;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import fragments.TestingFragment;
import fragments.mapFragement;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract TestingFragment contributeTestingFragment();
    @ContributesAndroidInjector
    abstract mapFragement contributemapFragment();
}
