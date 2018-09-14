package jessehj.urllinker.scene.main;

import jessehj.urllinker.LinkDefault
import jessehj.urllinker.manager.RealmManager
import jessehj.urllinker.model.Link


interface MainBusinessLogic : MainDataPassing, MainDataStore {
    fun fetchMainData(request: MainData.Request)
    fun fetchInitData(request: InitData.Request)
    fun fetchDetailData(request: DetailData.Request)
    fun requestRemoveItem(request: RemoveData.Request)
    fun requestModifyItem(request: ModifyData.Request)
}

interface MainDataPassing {
    fun setPassedData(Obj: Any)
}

interface MainDataStore {
    fun selectedLink(): Link?
}

class MainInteractor : MainBusinessLogic {

    lateinit var presenter: MainPresentationLogic
    private var worker = MainWorker()

    lateinit var linkList: MutableList<Link>
    lateinit var link: Link

    override fun setPassedData(Obj: Any) {

    }

    override fun selectedLink(): Link? {
        return if (::link.isInitialized) {
            link
        } else {
            null
        }
    }

    override fun fetchMainData(request: MainData.Request) {
        presenter.presentProgress()

        RealmManager.checkRealmState(object : RealmManager.InitializeCompletion {
            override fun isReady() {

                RealmManager.LinkDB.findAll()?.apply {
                    if (isEmpty()) {

                        InitData.Request().apply {
                            context = request.context
                            fetchInitData(this)
                        }

                    } else {
                        presenter.dismissProgress()
                        linkList = this
                        MainData.Response().apply {
                            context = request.context
                            this.links = linkList
                            presenter.presentMainData(this)
                        }
                    }
                }
            }
        })
    }

    override fun fetchInitData(request: InitData.Request) {

        RealmManager.LinkDB.removeAll(object : RealmManager.DBCompletion {
            override fun onSuccess() {

                createDefaultLinks(object : RealmManager.DBCompletion {
                    override fun onSuccess() {

                        MainData.Request().apply {
                            context = request.context
                            fetchMainData(this)
                        }
                    }

                    override fun onError(errMsg: String) {
                        presenter.dismissProgress()
                        presenter.presentSnackbar(errMsg)
                    }
                })
            }

            override fun onError(errMsg: String) {
                presenter.dismissProgress()
                presenter.presentSnackbar(errMsg)
            }
        })
    }

    override fun fetchDetailData(request: DetailData.Request) {
        link = linkList[request.position]
        presenter.presentDetailData()
    }

    override fun requestRemoveItem(request: RemoveData.Request) {
        presenter.presentProgress()

        RealmManager.LinkDB.removeOne(linkList[request.position].id, object : RealmManager.DBCompletion {
            override fun onSuccess() {
                MainData.Request().apply {
                    this.context = request.context
                    fetchMainData(this)
                }
            }

            override fun onError(errMsg: String) {
                presenter.dismissProgress()
                presenter.presentSnackbar(errMsg)
            }
        })
    }

    override fun requestModifyItem(request: ModifyData.Request) {
        link = linkList[request.position]
        presenter.routeToModifyItem()
    }

    private fun createDefaultLinks(completion: RealmManager.DBCompletion) {
        val defaults = mutableListOf(
                Link(0, LinkDefault.TITLE_SIGN_UP, LinkDefault.URL_SIGN_UP),
                Link(1, LinkDefault.TITLE_ROUTE_MAP, LinkDefault.URL_ROUTE_MAP),
                Link(2, LinkDefault.TITLE_TUNE_MAIN, LinkDefault.URL_TUNE_MAIN),
                Link(3, LinkDefault.TITLE_TUNE_EVENT, LinkDefault.URL_TUNE_EVENT),
                Link(4, LinkDefault.TITLE_TUNE_EVENT_DETAIL, LinkDefault.URL_TUNE_EVENT_DETAIL),
                Link(5, "MOST", "https://bit.ly/2lJgoX8")
        )

        RealmManager.LinkDB.upsert(defaults, completion)
    }
}