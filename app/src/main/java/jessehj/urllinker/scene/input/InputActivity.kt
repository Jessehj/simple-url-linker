package jessehj.urllinker.scene.input;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import jessehj.urllinker.R
import jessehj.urllinker.model.Query
import jessehj.urllinker.scene.adapter.QueryAdapter
import jessehj.urllinker.scene.base.BaseActivity
import jessehj.urllinker.scene.base.BaseDisplayLogic
import kotlinx.android.synthetic.main.activity_input.*


interface InputDisplayLogic : BaseDisplayLogic {
    fun displayInputData(viewModel: InputData.ViewModel)
    fun routeToMain()
    fun refreshData(viewModel: RefreshData.ViewModel)
}

class InputActivity : BaseActivity(), InputDisplayLogic {

    companion object {
        const val EXTRA_SELECTED_LINK_ID = "extraSelectedLinkId"
    }

    lateinit var interactor: InputBusinessLogic
    lateinit var router: InputRouter

    private lateinit var queryAdapter: QueryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InputConfigurator.INSTANCE.configure(this)
        setContentView(R.layout.activity_input)

        passDataToInteractor()

        configToolbar(input_toolbar)
        configActions()
        configQueryList()

        interactor.refreshData(this@InputActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.input_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> {

                saveData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun passDataToInteractor() {
        if (intent.hasExtra(EXTRA_SELECTED_LINK_ID)) {
            interactor.setPassedData(intent.getLongExtra(EXTRA_SELECTED_LINK_ID, -1))
        }
    }

    private fun configActions() {
        url_text_input_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    fetchInputData(p0.toString(), null, null)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    private fun configQueryList() {
        queryAdapter = QueryAdapter()
        query_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@InputActivity)
            adapter = queryAdapter
        }

        queryAdapter.itemClickListener = object : QueryAdapter.ItemClickListener {
            override fun onQueryInputted(position: Int, query: Query) {
                fetchInputData(null, position, query)
            }

            override fun add(position: Int) {
                interactor.addQueryField(this@InputActivity)
            }

            override fun remove(position: Int) {
                interactor.removeQueryField(this@InputActivity, position)
            }
        }
    }

    private fun fetchInputData(url: String?, position: Int?, query: Query?) {
        InputData.Request().apply {
            context = this@InputActivity
            this.url = url
            this.position = position
            this.query = query
            interactor.fetchInputData(this)
        }
    }

    private fun saveData() {
        SaveData.Request().apply {
            context = this@InputActivity
            name = name_text_input_edit_text.text.toString()
            url = url_preview_text_view.text.toString()
            interactor.saveData(this)
        }
    }


    override fun displayInputData(viewModel: InputData.ViewModel) {
        url_preview_text_view.text = viewModel.fullUrl
    }

    override fun refreshData(viewModel: RefreshData.ViewModel) {
        name_text_input_edit_text.setText(viewModel.name)
        url_text_input_edit_text.setText(viewModel.url)
        queryAdapter.apply {
            queryList = viewModel.list
            notifyDataSetChanged()
        }
    }

    override fun routeToMain() {
        router.navigateToMain()
    }

    override fun displaySnackbar(msg: String) {
        showSnackbar(input_layout, msg)
    }

    override fun displayToast(msg: String) {
        showToast(msg)
    }

    override fun displayProgress() {
        showProgressDialog()
    }

    override fun dismissProgress() {
        stopProgressDialog()
    }
}