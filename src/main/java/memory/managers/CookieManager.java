package memory.managers;

import memory.ListType;
import memory.types.Cookie;

public class CookieManager extends Manager<Cookie> {
    public CookieManager() {
        super(ListType.COOKIE_LIST.type());
    }

    public String getCookie() {
        StringBuilder builder = new StringBuilder();
        for (Cookie cookie : getElements()) {
            builder.append(cookie.getKey());
            builder.append("=");
            builder.append(cookie.getValue());
            builder.append("; ");
        }
        return builder.toString();
    }
}
