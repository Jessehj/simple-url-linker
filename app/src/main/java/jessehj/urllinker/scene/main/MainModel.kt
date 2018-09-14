package jessehj.urllinker.scene.main;

import android.content.Context
import jessehj.urllinker.model.Link


object MainData {

    class Request {
        lateinit var context: Context
    }

    class Response {
        lateinit var context: Context
        lateinit var links: MutableList<Link>
    }

    class ViewModel {
        lateinit var links: MutableList<Link>
    }
}

object InitData {
    class Request {
        lateinit var context: Context
    }
}

object DetailData {
    class Request {
        lateinit var context: Context
        var position: Int = -1
    }
}

object RemoveData {
    class Request {
        lateinit var context: Context
        var position: Int = -1
    }
}

object ModifyData {
    class Request {
        lateinit var context: Context
        var position: Int = -1
    }
}