package jessehj.urllinker.manager

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import jessehj.urllinker.AppDB
import jessehj.urllinker.RealmConfig
import jessehj.urllinker.model.Link


object RealmManager {

    lateinit var realmConfig: RealmConfiguration

    fun initializeRealm(context: Context) {
        Realm.init(context)
        realmConfig = RealmConfiguration.Builder()
                .name(RealmConfig.NAME)
                .schemaVersion(RealmConfig.SCHEMA_VER)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfig)
    }

    fun checkRealmState(completion: InitializeCompletion) {
        Realm.getInstanceAsync(realmConfig, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                completion.isReady()
            }
        })
    }

    object LinkDB {

        fun upsert(links: MutableList<Link>, completion: DBCompletion) {
            Realm.getDefaultInstance().apply {

                executeTransactionAsync({ realm ->
                    for (link in links) {
                        realm.copyToRealmOrUpdate(link)
                    }
                }, {
                    completion.onSuccess()
                }, { error -> completion.onError(error.localizedMessage) })

                close()
            }
        }

        fun upsertOne(link: Link, completion: DBCompletion) {
            Realm.getDefaultInstance().apply {
                executeTransactionAsync({
                    it.copyToRealmOrUpdate(link)
                }, {
                    completion.onSuccess()
                }, { error ->
                    completion.onError(error.localizedMessage)
                })
                close()
            }
        }

        fun findOne(id: Long): Link? {
            return Realm.getDefaultInstance().let {
                val link = it.where(Link::class.java).equalTo(AppDB.KEY, id).findFirst()
                it.close()

                link
            }
        }

        fun findAll(): MutableList<Link>? {
            return Realm.getDefaultInstance().let {

                var result = it.where(Link::class.java).findAll()
                result = result.sort(AppDB.KEY, Sort.DESCENDING)
                val links = it.copyFromRealm(result)
                it.close()

                links
            }
        }

        fun removeAll(completion: DBCompletion) {
            Realm.getDefaultInstance().apply {
                executeTransactionAsync({ realm ->
                    realm.deleteAll()
                }, {
                    completion.onSuccess()
                }, { error -> completion.onError(error.localizedMessage) })

                close()
            }
        }

        fun removeOne(id: Long, completion: DBCompletion) {
            Realm.getDefaultInstance().apply {
                executeTransactionAsync({
                    val target = it.where(Link::class.java).equalTo(AppDB.KEY, id).findFirst()
                    target?.deleteFromRealm()
                }, {
                    completion.onSuccess()
                }, { error -> completion.onError(error.localizedMessage) })

                close()
            }
        }
    }

    interface InitializeCompletion {
        fun isReady()
    }

    interface DBCompletion {
        fun onSuccess()
        fun onError(errMsg: String)
    }
}