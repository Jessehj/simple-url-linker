package jessehj.urllinker.scene.main;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import jessehj.urllinker.R
import jessehj.urllinker.scene.adapter.LinkAdapter
import jessehj.urllinker.scene.base.BaseActivity
import jessehj.urllinker.scene.base.BaseDisplayLogic
import jessehj.urllinker.view.RecyclerDecoration
import kotlinx.android.synthetic.main.activity_main.*


interface MainDisplayLogic : BaseDisplayLogic {
    fun displayMainData(viewModel: MainData.ViewModel)
    fun displayDetailData()
    fun routeToModifyItem()
}

class MainActivity : BaseActivity(), MainDisplayLogic {

    lateinit var interactor: MainBusinessLogic
    lateinit var router: MainRouter

    private lateinit var linkAdapter: LinkAdapter

    companion object {
        const val REQ_CODE_INPUT = 0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainConfigurator.INSTANCE.configure(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        configActions()
        configList()

        fetchMainData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_reset -> {
                InitData.Request().apply {
                    context = this@MainActivity
                    interactor.fetchInitData(this)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_CODE_INPUT -> fetchMainData()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun configActions() {
        main_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && main_fab.isShown) {
                    main_fab.hide()
                } else if (dy <= 0 && !main_fab.isShown) {
                    main_fab.show()
                }
            }
        })

        main_fab.setOnClickListener {
            router.navigateToInput()
        }
    }

    private fun configList() {
        linkAdapter = LinkAdapter()
        main_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = linkAdapter
            addItemDecoration(RecyclerDecoration(0, 4, 8, 8))
        }

        linkAdapter.itemClickListener = object : LinkAdapter.ItemClickListener {
            override fun onLinkItemClick(position: Int) {
                fetchDetailData(position)
            }

            override fun onLinkItemLongClick(position: Int) {
                showLinkOptionsDialog(position)
            }
        }
    }

    private fun fetchMainData() {
        MainData.Request().apply {
            context = this@MainActivity
            interactor.fetchMainData(this)
        }
    }

    private fun fetchDetailData(position: Int) {
        DetailData.Request().apply {
            context = this@MainActivity
            this.position = position
            interactor.fetchDetailData(this)
        }
    }

    private fun requestRemoveItem(position: Int) {
        RemoveData.Request().apply {
            context = this@MainActivity
            this.position = position
            interactor.requestRemoveItem(this)
        }
    }

    private fun requestModifyItem(position: Int) {
        ModifyData.Request().apply {
            context = this@MainActivity
            this.position = position
            interactor.requestModifyItem(this)
        }
    }

    private fun showLinkOptionsDialog(position: Int) {
        MaterialDialog(this@MainActivity).show {
            listItems(R.array.arrays_link_options) { dialog, index, text ->
                when (index) {
                    0 -> requestModifyItem(position)
                    1 -> requestRemoveItem(position)
                }
            }
        }
    }

    override fun displaySnackbar(msg: String) {
        showSnackbar(main_layout, msg)
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

    override fun displayMainData(viewModel: MainData.ViewModel) {
        linkAdapter.viewModels = viewModel.links
        linkAdapter.notifyDataSetChanged()
    }

    override fun displayDetailData() {
        router.navigateWithUrl()
    }

    override fun routeToModifyItem() {
        router.navigateToModify()
    }


}