package io.github.sykoram.sis;

import android.net.Uri;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainActivityUtils {

    public static boolean isUrlAllowed(Uri url) {
        List<String> allowedSchemes = Arrays.asList("http", "https");
        if (!allowedSchemes.contains(url.getScheme())) {
            return false;
        }

        List<String> allowedBeginnings = Arrays.asList(
                "is.cuni.cz/studium", "idp.cuni.cz", "ldapuser.cuni.cz");
        List<String> disallowedBeginnings = Arrays.asList(
                "is.cuni.cz/studium/v4");

        String hostAndPath = url.getHost().concat(url.getPath());
        boolean allowed = false;
        for (String bgn : allowedBeginnings) {
            if (hostAndPath.startsWith(bgn)) {
                allowed = true;
                break;
            }
        }
        if (allowed) {
            for (String bgn : disallowedBeginnings) {
                if (hostAndPath.startsWith(bgn)) {
                    allowed = false;
                    break;
                }
            }
        }

        return allowed;
    }

    public static String readFileToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String getPageIdentifier(String url) {
        String identifier = "";

        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        String path = uri.getPath();
        if (host.equals("is.cuni.cz") && path.startsWith("/studium")) {
            path = path.replaceFirst("^/studium(/eng)?/?", "")
                    .replaceFirst("(/index)?\\.php$", "");
            identifier = path.split("/")[0];
        }

        return identifier;
    }

}
