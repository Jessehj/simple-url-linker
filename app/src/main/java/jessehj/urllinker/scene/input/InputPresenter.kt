package jessehj.urllinker.scene.input;

import jessehj.urllinker.util.ModelUtils
import jessehj.urllinker.scene.base.BasePresentLogic
import java.lang.ref.WeakReference


interface InputPresentationLogic : BasePresentLogic {
    fun presentInputData(response: InputData.Response)
    fun refreshData(response: RefreshData.Response)
    fun routeToMain()
}

class InputPresenter : InputPresentationLogic {

    lateinit var activity: WeakReference<InputDisplayLogic>

    override fun presentInputData(response: InputData.Response) {
        InputData.ViewModel().apply {
            fullUrl = ModelUtils.getQueryAppendedUrl(response.url, response.list)
            activity.get()!!.displayInputData(this)
        }
    }

    override fun refreshData(response: RefreshData.Response) {
        RefreshData.ViewModel().apply {
            name = response.name
            url = response.url
            list = response.list
            activity.get()!!.refreshData(this)
        }
    }

    override fun routeToMain() {
        activity.get()!!.routeToMain()
    }

    override fun presentSnackbar(msg: String) {
        activity.get()!!.displaySnackbar(msg)
    }

    override fun presentToast(msg: String) {
        activity.get()!!.displayToast(msg)
    }

    override fun presentProgress() {
        activity.get()!!.displayProgress()
    }

    override fun dismissProgress() {
        activity.get()!!.dismissProgress()
    }
}