package jessehj.urllinker

object RealmConfig {
    const val NAME = "deeplinker.realm"
    const val SCHEMA_VER = 1L
}

object AppDB {
    const val KEY = "id"
}

object LinkDefault {
    const val URL_MARKET = "https://play.google.com/store/apps/details?id=com.skn.zamong2"

    const val TITLE_SIGN_UP = "모스트 회원가입"
    const val URL_SIGN_UP = "most://collabopromotion?channel=kakaoMap&type=signUp"
    const val TITLE_ROUTE_MAP = "지도 연결(10010)"
    const val URL_ROUTE_MAP = "most://collabopromotion?channel=kakaoMap&type=map&sequence=10010"

    const val TITLE_TUNE_MAIN = "TUNE MAIN"
    const val URL_TUNE_MAIN = "https://kfo-k.tlnk.io/serve?action=click&campaign_id_android=440238&campaign_id_ios=440239&destination_id_android=521196&destination_id_ios=521197&invoke_id_android=295025&invoke_id_ios=295022&publisher_id=363825&site_id_android=141803&site_id_ios=141804"

    const val TITLE_TUNE_EVENT = "TUNE EVENT"
    const val URL_TUNE_EVENT = "https://kfo-k.tlnk.io/serve?action=click&campaign_id_android=440238&campaign_id_ios=440239&destination_id_android=521196&destination_id_ios=521197&invoke_id_android=295026&invoke_id_ios=295023&publisher_id=363825&site_id_android=141803&site_id_ios=141804"

    const val TITLE_TUNE_EVENT_DETAIL = "TUNE_EVENT_DETAIL"
    const val URL_TUNE_EVENT_DETAIL = "https://kfo-k.tlnk.io/serve?action=click&campaign_id_android=440238&campaign_id_ios=440239&destination_id_android=521196&destination_id_ios=521197&invoke_id_android=295027&invoke_id_ios=295024&my_placement=E0000000000000000069&publisher_id=363825&site_id_android=141803&site_id_ios=141804"

    const val TITLE_IMG_TEST = "IMG TEST"
    const val URL_IMG_TEST = "http://mobilehamdev.sknetworks.co.kr/web/images/event/asianaM.jpg"
}

enum class LinkOptions {
    Modify,
    Remove
}