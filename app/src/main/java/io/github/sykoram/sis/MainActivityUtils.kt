package io.github.sykoram.sis

import android.content.res.AssetManager
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import java.io.InputStream
import java.lang.Exception
import java.net.URLConnection
import java.util.*

fun isUrlAllowed(url: Uri): Boolean {
    val allowedSchemes = listOf("http", "https")
    if (!allowedSchemes.contains(url.scheme)) {
        return false
    }

    val allowedBeginnings = listOf("is.cuni.cz/studium", "idp.cuni.cz", "ldapuser.cuni.cz")
    val disallowedBeginnings = listOf("is.cuni.cz/studium/v4")
    val hostAndPath = url.host + url.path
    var allowed = false
    for (bgn in allowedBeginnings) {
        if (hostAndPath.startsWith(bgn)) {
            allowed = true
            break
        }
    }
    if (allowed) {
        for (bgn in disallowedBeginnings) {
            if (hostAndPath.startsWith(bgn)) {
                allowed = false
                break
            }
        }
    }
    return allowed
}

fun readFileToString(ins: InputStream?): String {
    val s = Scanner(ins).useDelimiter("\\A")
    return if (s.hasNext()) s.next() else ""
}

/*
    Adds page identifiers to body as classes.
 */
fun addPageIdentifiersToBody(view: WebView, url: String) {
    val classes = getPageIdentifiers(url).joinToString(" ", " ")
    view.evaluateJavascript("document.body.className += '$classes';", null)
}

/*
    Returns List of page identifiers for SIS pages (starting with "is.cuni.cz/studium").
    Path sections and "do" and "doe" parameters are considered.
    Examples:
        https://is.cuni.cz/studium/index.php?id=...&tid=& -> [index]
        https://is.cuni.cz/studium/predm_st2/index.php?id=...&tid=&KEY=... -> [predm_st2]
        https://is.cuni.cz/studium/predmety/index.php?id=...&tid=&do=prohl -> [predmety prohl]
        https://is.cuni.cz/studium/omne/index.php?id=...&tid=&do=email&doe=email_detail&mail_id=...&arch=0 -> [omne email email_detail]
 */
fun getPageIdentifiers(url: String): List<String> {
    val uri = Uri.parse(url)
    val host = uri.host
    val path = uri.path
    val doParam = uri.getQueryParameter("do")
    val doeParam = uri.getQueryParameter("doe")

    if (host == "is.cuni.cz" && path!!.startsWith("/studium")) {
        val ids = path.replaceFirst("^/studium(/eng)?/?".toRegex(), "")
                .replaceFirst("(/index)?\\.php$".toRegex(), "")
                .split("/")
                .toList()
        return listOf(ids, listOfNotNull(doParam, doeParam)).flatten() // concat lists
    }

    return emptyList()
}

val fromAssetsReplacements = mapOf(
    // header icons
    "stev_home.gif" to "openmoji-black/E25E.png",
    "stev_settings.gif" to "openmoji-black/2699.png",
    "stev_login.gif" to "openmoji-black/1F513.png",
    "stev_logoff.gif" to "openmoji-black/1F512.png",
    "stev_en.gif" to "openmoji-color/1F1EC-1F1E7.png",
    "stev_cz.gif" to "openmoji-color/1F1E8-1F1FF.png",
    "stev_help.gif" to "openmoji-black/2754.png",
    "stev_menu_home.gif" to "openmoji-black/E25E.png",
    "stev_menu_bookmarks.gif" to "openmoji-black/1F516.png",
    // homepage icons
    "v4.gif" to "openmoji-color/0034-FE0F-20E3.png",
    "term_st2.gif" to "openmoji-color/1F4C5.png",
    "szz_st.gif" to "openmoji-color/1F393.png",
    "predm_st2.gif" to "openmoji-color/1F4DD.png",
    "predmety.gif" to "openmoji-color/269B.png",
    "grupicek.gif" to "openmoji-color/1F4C8.png",
    "dipl_st.gif" to "openmoji-color/1F4DC.png",
    "anketa.gif" to "openmoji-color/2705.png",
    "zkous_st.gif" to "openmoji-color/1F4D2.png",
    "rozvrhng.gif" to "openmoji-color/E0AB.png",
    "prijimacky.gif" to "openmoji-color/E266.png",
    "prihlastaz.gif" to "openmoji-color/1F4C3.png",
    "publikace.gif" to "openmoji-color/1F4F0.png",
    "rozcestnik.gif" to "openmoji-color/E094.png",
    "komise.gif" to "openmoji-color/1F465.png",
    "szz.gif" to "openmoji-color/1F393.png",
    "deda_amu.gif" to "openmoji-color/1F30D.png",
    "ckis.gif" to "openmoji-color/1F4DA.png",
    "pez.gif" to "openmoji-color/E1CC.png",
    "ukaz.gif" to "icons/ukaz.png",
    "moodle.gif" to "icons/moodle.png",
    "iforum.gif" to "icons/iforum.png",
    "sis_uk_point.gif" to "icons/uk_point.png",
    "sis_centrum_carolina.gif" to "icons/centrum_carolina.png",
    "sis_phd_platform.gif" to "icons/phd_platform.png",
    "sis_klub_alumni.gif" to "icons/klub_alumni.png",
    "bookmarks.gif" to "openmoji-color/1F516.png",
    "ciselniky.gif" to "openmoji-color/E1C1.png",
    "ekczv.gif" to "openmoji-color/1F4D8.png",
    "harmonogram.gif" to "openmoji-color/1F5D3.png",
    "kdojekdo.gif" to "openmoji-color/1F50E.png",
    "loginy.gif" to "openmoji-color/002A-FE0F-20E3.png",
    "nastenka.gif" to "openmoji-color/1F4CC.png",
    "omne.gif" to "openmoji-color/1F464.png",
    "promoce.gif" to "openmoji-color/1F9D1-200D-1F393.png",
    "skolitel.gif" to "openmoji-color/1F9D1-200D-1F3EB.png",
    "soub_mana.gif" to "openmoji-color/1F4C2.png",
    "wstip_st.gif" to "openmoji-color/1F4B6.png",
    "prezkumy_st.gif" to "openmoji-color/1F9FE.png",
    "dotaznik.gif" to "openmoji-black/2754.png",
    "cml.gif" to "openmoji-color/1F4DA.png",
    "podprij.gif" to "openmoji-color/1F4C3.png",
    "staze_uc.gif" to "openmoji-color/1F30D.png",
    "sestavy.gif" to "openmoji-color/1F5D2.png",
    "deda_strav.gif" to "openmoji-color/1F37D.png",
    "deda_zahost.gif" to "openmoji-color/1F30D.png",
    "esc.gif" to "openmoji-color/1F697.png",
    "wstip_uc.gif" to "openmoji-color/1F4B6.png",
    // other - in filters, tables - don't forget to add css rule to smaller it!
    "div_tip.gif" to "openmoji-color/2139.png",
    "ico_invert.gif" to "openmoji-color/E25B.png",
    "ico_n_style.png" to "openmoji-color/E265.png",
    "ico_predmety.png" to "openmoji-color/269B.png",
    "ico_rozvrhng.png" to "openmoji-color/E0AB.png",
    "ico_ucitel_small.png" to "openmoji-color/1F9D1-200D-1F3EB.png",
    "ico_detail.png" to "openmoji-color/E259.png",
    "term_st_false.gif" to "openmoji-color/274C.png",
    "ico_delete.png" to "openmoji-color/E262.png",
    "ico_date.png" to "openmoji-color/1F5D3.png",
    "chk_true.gif" to "openmoji-color/2714.png",
    "chk_true.png" to "openmoji-color/2714.png",
    "chk_false.gif" to "openmoji-color/274C.png",
    "chk_false.png" to "openmoji-color/274C.png",
    "ico_list.png" to "openmoji-color/1F4D1.png",
    "div_legend.gif" to "openmoji-color/2139.png",
    "ico_select_all.gif" to "openmoji-color/2714.png",
    "ico_unselect_all.gif" to "openmoji-color/274C.png",
    "filtr_minus.gif" to "openmoji-color/2796.png",
    "ico_n_bin.png" to "openmoji-color/E262.png",
    "ico_dialog_find.png" to "openmoji-color/1F50E.png",
    "ico_dialog_null.png" to "openmoji-color/274C.png",
    "ico_dialog_plus.png" to "openmoji-color/2795.png",
    "ico_n_link_go.png" to "openmoji-color/E269.png",
    "ico_n_help.png" to "openmoji-black/2754.png",
    "ico_n_page_attach.png" to "openmoji-color/1F4CE.png",
    "ico_file.png" to "openmoji-color/1F4BE.png",
    "ico_n_add.png" to "openmoji-color/E25F.png",
    "term_st_zapsan.gif" to "openmoji-color/2714.png",
    "ico_n_arrow_right.png" to "openmoji-color/27A1.png",
    "ico_download.png" to "openmoji-color/E25C.png",
    "ico_n_cross.png" to "openmoji-color/274C.png",
    "ico_n_cancel.png" to "openmoji-color/274C.png",
    "ico_detail_history.png" to "openmoji-color/1F5D3.png",
    "ajax-loader_big.gif" to "icons/loading-circle.png", // loader
    "ajax-loader.gif" to "icons/loading-circle.png", // loader
    "ico_n_eye.png" to "openmoji-color/1F441.png",
    "term_st_splnen.gif" to "openmoji-color/2611.png",
    // don't forget to add css rule to smaller it!
)

fun replaceRequestWithAssets(request: WebResourceRequest, assets: AssetManager): WebResourceResponse? {
    val file = request.url.lastPathSegment

    if (fromAssetsReplacements.containsKey(file)) {
        val newFile = fromAssetsReplacements[file]!!
        try {
            return WebResourceResponse(URLConnection.guessContentTypeFromName(newFile), "", assets.open(newFile))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return null
}