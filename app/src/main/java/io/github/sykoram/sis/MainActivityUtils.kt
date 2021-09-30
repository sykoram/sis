package io.github.sykoram.sis

import android.net.Uri
import android.webkit.WebView
import java.io.InputStream
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