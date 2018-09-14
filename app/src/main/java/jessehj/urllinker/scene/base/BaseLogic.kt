package jessehj.urllinker.scene.base

interface BaseDisplayLogic {
    fun displaySnackbar(msg: String)
    fun displayToast(msg: String)
    fun displayProgress()
    fun dismissProgress()
}

interface BasePresentLogic {
    fun presentSnackbar(msg: String)
    fun presentToast(msg: String)
    fun presentProgress()
    fun dismissProgress()
}

