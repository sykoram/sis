package io.github.sykoram.sis

import android.net.Uri
import java.io.InputStream
import java.util.*

fun isUrlAllowed(url: Uri): Boolean {
    val allowedSchemes = listOf("http", "https")
    if (!allowedSchemes.contains(url.scheme)) {
        return false
    }

    val allowedBeginnings = listOf("is.cuni.cz/studium", "idp.cuni.cz", "ldapuser.cuni.cz")
    val disallowedBeginnings = listOf("is.cuni.cz/studium/v4", "is.cuni.cz/studium/help2")
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

fun getPageIdentifier(url: String): String {
    val uri = Uri.parse(url)
    val host = uri.host
    val path = uri.path
    if (host == "is.cuni.cz" && path!!.startsWith("/studium")) {
        return path.replaceFirst("^/studium(/eng)?/?".toRegex(), "")
                .replaceFirst("(/index)?\\.php$".toRegex(), "")
                .split("/")
                .toTypedArray()[0]
    }
    return ""
}