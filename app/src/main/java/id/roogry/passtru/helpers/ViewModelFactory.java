package id.roogry.passtru.helpers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.roogry.passtru.viewmodel.DialogViewModel;
import id.roogry.passtru.viewmodel.FormManageAccountViewModel;
import id.roogry.passtru.viewmodel.HomeViewModel;
import id.roogry.passtru.viewmodel.ListAccountViewModel;
import id.roogry.passtru.viewmodel.ListSosmedViewModel;
import id.roogry.passtru.viewmodel.ResetPinViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(application);
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(FormManageAccountViewModel.class)) {
            return (T) new FormManageAccountViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(ListAccountViewModel.class)) {
            return (T) new ListAccountViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(ListSosmedViewModel.class)) {
            return (T) new ListSosmedViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(DialogViewModel.class)) {
            return (T) new DialogViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(ResetPinViewModel.class)) {
            return (T) new ResetPinViewModel(mApplication);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}