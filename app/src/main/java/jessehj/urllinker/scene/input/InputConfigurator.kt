package jessehj.urllinker.scene.input;

import java.lang.ref.WeakReference;


enum class InputConfigurator {
    INSTANCE;

    fun configure(activity: InputActivity) {

        val presenter = InputPresenter()
        presenter.activity = WeakReference(activity)

        val interactor = InputInteractor()
        interactor.presenter = presenter

        val router = InputRouter()
        router.activity = WeakReference(activity)

        activity.interactor = interactor
        activity.router = router
        activity.router.dataStore = interactor

    }
}