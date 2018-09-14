package jessehj.urllinker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Link(@PrimaryKey var id: Long = 0, var title: String = "", var url: String = "") : RealmObject()