package jessehj.urllinker.scene.input;

import android.content.Context
import android.webkit.URLUtil
import jessehj.urllinker.R
import jessehj.urllinker.manager.RealmManager
import jessehj.urllinker.model.Link
import jessehj.urllinker.model.Query
import jessehj.urllinker.util.ModelUtils


interface InputBusinessLogic : InputDataPassing, InputDataStore {
    fun fetchInputData(request: InputData.Request)
    fun addQueryField(context: Context)
    fun removeQueryField(context: Context, position: Int)
    fun saveData(request: SaveData.Request)
    fun refreshData(context: Context)
}

interface InputDataPassing {
    fun setPassedData(linkId: Long)
}

interface InputDataStore {
    // fun getData(): Any
}


class InputInteractor : InputBusinessLogic {

    lateinit var presenter: InputPresentationLogic
    private var worker = InputWorker()

    var originId: Long = -1
    var linkName: String = ""
    var linkDomain: String = ""
    var queryList = mutableListOf(Query("", ""))

    override fun setPassedData(linkId: Long) {
        if (linkId > 0) {
            RealmManager.LinkDB.findOne(linkId)?.let { link ->
                val url = link.url
                originId = link.id
                linkName = link.title
                linkDomain = ModelUtils.parseDomain(url)

                val queryMap = ModelUtils.parseQueryString(url)
                queryList = mutableListOf()

                queryMap.forEach {
                    queryList.add(Query(it.key, it.value))
                }
                queryList.add(Query("", ""))
            }
        }
    }

    override fun refreshData(context: Context) {
        RefreshData.Response().apply {
            this.context = context
            this.name = linkName
            this.url = linkDomain
            this.list = queryList
            presenter.refreshData(this)
        }
    }

    override fun fetchInputData(request: InputData.Request) {

        request.url?.let { linkDomain = it }
        request.query?.let {
            if (request.position != null && request.position!! < queryList.size) {
                queryList[request.position!!] = it
            }
        }

        InputData.Response().apply {
            context = request.context
            this.url = linkDomain
            this.list = queryList
            presenter.presentInputData(this)
        }
    }

    override fun addQueryField(context: Context) {
        queryList.add(Query("", ""))
        refreshData(context)
    }

    override fun removeQueryField(context: Context, position: Int) {
        queryList.removeAt(position)
        refreshData(context)
    }

    override fun saveData(request: SaveData.Request) {
        presenter.presentProgress()

        if (URLUtil.isValidUrl(request.url)) {

            val id = if (originId != -1L) {
                originId
            } else {
                System.currentTimeMillis()
            }

            val link = Link(
                    id,
                    request.name,
                    request.url)

            RealmManager.LinkDB.upsertOne(link, object : RealmManager.DBCompletion {
                override fun onSuccess() {
                    presenter.dismissProgress()
                    presenter.routeToMain()
                }

                override fun onError(errMsg: String) {
                    presenter.dismissProgress()
                    presenter.presentSnackbar(errMsg)
                }
            })

        } else {
            presenter.dismissProgress()
            presenter.presentSnackbar(request.context.getString(R.string.error_invalid_url))
        }
    }
}