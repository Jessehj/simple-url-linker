package jessehj.urllinker.scene.main;

import jessehj.urllinker.scene.base.BasePresentLogic
import java.lang.ref.WeakReference


interface MainPresentationLogic : BasePresentLogic {
    fun presentMainData(response: MainData.Response)
    fun presentDetailData()
    fun routeToModifyItem()
}

class MainPresenter : MainPresentationLogic {

    lateinit var activity: WeakReference<MainDisplayLogic>

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

    override fun presentMainData(response: MainData.Response) {
        MainData.ViewModel().apply {
            this.links = response.links
            activity.get()!!.displayMainData(this)
        }
    }

    override fun presentDetailData() {
        activity.get()!!.displayDetailData()
    }

    override fun routeToModifyItem() {
        activity.get()!!.routeToModifyItem()
    }
}