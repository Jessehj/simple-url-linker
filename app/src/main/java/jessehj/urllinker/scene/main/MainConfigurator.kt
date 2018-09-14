package jessehj.urllinker.scene.main;

import java.lang.ref.WeakReference;


enum class MainConfigurator {
    INSTANCE;

    fun configure(activity: MainActivity) {

        val presenter = MainPresenter()
        presenter.activity = WeakReference(activity)

        val interactor = MainInteractor()
        interactor.presenter = presenter

        val router = MainRouter()
        router.activity = WeakReference(activity)

        activity.interactor = interactor
        activity.router = router
        activity.router.dataStore = interactor

    }
}