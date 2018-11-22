package com.mishanovosel.sazanwatchfish.InterfaceMap;

import com.mishanovosel.sazanwatchfish.Events.DeleteClickInDialog;
import com.mishanovosel.sazanwatchfish.Events.MyClickInDialog;
import com.mishanovosel.sazanwatchfish.Fragments.MapFragment;
import com.mishanovosel.sazanwatchfish.Helper.CompareKoordObserverHelper;
import com.mishanovosel.sazanwatchfish.Modul.AppDatabaseModule;

import dagger.Component;

@Component(modules = {AppDatabaseModule.class})
public interface MyComponent {
    void inject(MyClickInDialog myClickInDialog);
    void inject(MapFragment mapFragment);
    void inject(DeleteClickInDialog deleteClickInDialog);
    void inject(CompareKoordObserverHelper deleteClickInDialog);



}
