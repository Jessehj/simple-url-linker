package jessehj.urllinker.scene.input;

import android.content.Context
import jessehj.urllinker.model.Query


object InputData {

    class Request {
        lateinit var context: Context
        var url: String? = null
        var position: Int? = null
        var query: Query? = null
    }

    class Response {
        lateinit var context: Context
        lateinit var url: String
        lateinit var list: MutableList<Query>
    }

    class ViewModel {
        lateinit var fullUrl: String
    }
}

object SaveData {
    class Request {
        lateinit var context: Context
        lateinit var name: String
        lateinit var url: String
    }
}

object RefreshData {

    class Response {
        lateinit var context : Context
        lateinit var name: String
        lateinit var url: String
        lateinit var list: MutableList<Query>
    }
    class ViewModel {
        lateinit var name: String
        lateinit var url: String
        lateinit var list: MutableList<Query>
    }
}

