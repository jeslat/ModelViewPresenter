package com.jla.modelviewpresenter.view.filmList.presenter;

import com.jla.modelviewpresenter.domain.interactor.GetPopularFilmsInteractor;
import com.jla.modelviewpresenter.view.bus.MainThreadBus;
import com.jla.modelviewpresenter.view.filmList.view.FilmListView;
import com.jla.modelviewpresenter.view.ui.Navigator;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Subscribe;

public class FilmListPresenterImpl implements FilmListPresenter {

    private FilmListView filmListView;
    private GetPopularFilmsInteractor getPopularFilmsInteractor;
    private Navigator navigator;
    private MainThreadBus bus;
    private JobManager jobManager;

    public FilmListPresenterImpl(FilmListView filmListView, GetPopularFilmsInteractor getPopularFilmsInteractor, Navigator navigator, JobManager jobManager, MainThreadBus bus) {
        this.filmListView = filmListView;
        this.getPopularFilmsInteractor = getPopularFilmsInteractor;
        this.navigator = navigator;
        this.bus = bus;
        this.jobManager = jobManager;
        this.bus.register(this);
    }

    @Override
    public void onResume() {
        filmListView.showProgress();
        jobManager.addJob(getPopularFilmsInteractor);
    }

    @Override
    public void onPause() {
        bus.unregister(this);
    }

    @Override
    public void onItemClicked(int position) {
    }

    @Subscribe
    public void filmsAvailable(FilmsReady filmsReady){
        filmListView.setFilms(filmsReady.getFilms());
        filmListView.hideProgress();
    }
}
