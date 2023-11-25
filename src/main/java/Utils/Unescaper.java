package Utils;

import java.io.StringWriter;
import java.util.HashMap;

@SuppressWarnings({"SpellCheckingInspection", "GrazieInspection"})
public class Unescaper {
    @SuppressWarnings("SpellCheckingInspection")
    private static final String[][] ESCAPES = {
            {"\"", "quot"}, // " - double-quote
            {"&", "amp"}, // & - ampersand
            {"<", "lt"}, // < - less-than
            {">", "gt"}, // > - greater-than

            // Mapping to escape ISO-8859-1 characters to their named HTML 3.x equivalents.
            {"\u00A0", "nbsp"}, // non-breaking space
            {"¡", "iexcl"}, // inverted exclamation mark
            {"¢", "cent"}, // cent sign
            {"£", "pound"}, // pound sign
            {"¤", "curren"}, // currency sign
            {"¥", "yen"}, // yen sign = yuan sign
            {"¦", "brvbar"}, // broken bar = broken vertical bar
            {"§", "sect"}, // section sign
            {"¨", "uml"}, // diaeresis = spacing diaeresis
            {"©", "copy"}, // © - copyright sign
            {"ª", "ordf"}, // feminine ordinal indicator
            {"«", "laquo"}, // left-pointing double angle quotation mark = left pointing guillemet
            {"¬", "not"}, // not sign
            {"\u00AD", "shy"}, // soft hyphen = discretionary hyphen
            {"®", "reg"}, // ® - registered trademark sign
            {"¯", "macr"}, // macron = spacing macron = overline = APL overbar
            {"°", "deg"}, // degree sign
            {"±", "plusmn"}, // plus-minus sign = plus-or-minus sign
            {"²", "sup2"}, // superscript two = superscript digit two = squared
            {"³", "sup3"}, // superscript three = superscript digit three = cubed
            {"´", "acute"}, // acute accent = spacing acute
            {"µ", "micro"}, // micro sign
            {"¶", "para"}, // pilcrow sign = paragraph sign
            {"·", "middot"}, // middle dot = Georgian comma = Greek middle dot
            {"¸", "cedil"}, // cedilla = spacing cedilla
            {"¹", "sup1"}, // superscript one = superscript digit one
            {"º", "ordm"}, // masculine ordinal indicator
            {"»", "raquo"}, // right-pointing double angle quotation mark = right pointing guillemet
            {"¼", "frac14"}, // vulgar fraction one quarter = fraction one quarter
            {"½", "frac12"}, // vulgar fraction one half = fraction one half
            {"¾", "frac34"}, // vulgar fraction three quarters = fraction three quarters
            {"¿", "iquest"}, // inverted question mark = turned question mark
            {"À", "Agrave"}, // А - uppercase A, grave accent
            {"Á", "Aacute"}, // Б - uppercase A, acute accent
            {"Â", "Acirc"}, // В - uppercase A, circumflex accent
            {"Ã", "Atilde"}, // Г - uppercase A, tilde
            {"Ä", "Auml"}, // Д - uppercase A, umlaut
            {"Å", "Aring"}, // Е - uppercase A, ring
            {"Æ", "AElig"}, // Ж - uppercase AE
            {"Ç", "Ccedil"}, // З - uppercase C, cedilla
            {"È", "Egrave"}, // И - uppercase E, grave accent
            {"É", "Eacute"}, // Й - uppercase E, acute accent
            {"Ê", "Ecirc"}, // К - uppercase E, circumflex accent
            {"Ë", "Euml"}, // Л - uppercase E, umlaut
            {"Ì", "Igrave"}, // М - uppercase I, grave accent
            {"Í", "Iacute"}, // Н - uppercase I, acute accent
            {"Î", "Icirc"}, // О - uppercase I, circumflex accent
            {"Ï", "Iuml"}, // П - uppercase I, umlaut
            {"Ð", "ETH"}, // Р - uppercase Eth, Icelandic
            {"Ñ", "Ntilde"}, // С - uppercase N, tilde
            {"Ò", "Ograve"}, // Т - uppercase O, grave accent
            {"Ó", "Oacute"}, // У - uppercase O, acute accent
            {"Ô", "Ocirc"}, // Ф - uppercase O, circumflex accent
            {"Õ", "Otilde"}, // Х - uppercase O, tilde
            {"Ö", "Ouml"}, // Ц - uppercase O, umlaut
            {"×", "times"}, // multiplication sign
            {"Ø", "Oslash"}, // Ш - uppercase O, slash
            {"Ù", "Ugrave"}, // Щ - uppercase U, grave accent
            {"Ú", "Uacute"}, // Ъ - uppercase U, acute accent
            {"Û", "Ucirc"}, // Ы - uppercase U, circumflex accent
            {"Ü", "Uuml"}, // Ь - uppercase U, umlaut
            {"Ý", "Yacute"}, // Э - uppercase Y, acute accent
            {"Þ", "THORN"}, // Ю - uppercase THORN, Icelandic
            {"ß", "szlig"}, // Я - lowercase sharps, German
            {"à", "agrave"}, // а - lowercase a, grave accent
            {"á", "aacute"}, // б - lowercase a, acute accent
            {"â", "acirc"}, // в - lowercase a, circumflex accent
            {"ã", "atilde"}, // г - lowercase a, tilde
            {"ä", "auml"}, // д - lowercase a, umlaut
            {"å", "aring"}, // е - lowercase a, ring
            {"æ", "aelig"}, // ж - lowercase ae
            {"ç", "ccedil"}, // з - lowercase c, cedilla
            {"è", "egrave"}, // и - lowercase e, grave accent
            {"é", "eacute"}, // й - lowercase e, acute accent
            {"ê", "ecirc"}, // к - lowercase e, circumflex accent
            {"ë", "euml"}, // л - lowercase e, umlaut
            {"ì", "igrave"}, // м - lowercase i, grave accent
            {"í", "iacute"}, // н - lowercase i, acute accent
            {"î", "icirc"}, // о - lowercase i, circumflex accent
            {"ï", "iuml"}, // п - lowercase i, umlaut
            {"ð", "eth"}, // р - lowercase eth, Icelandic
            {"ñ", "ntilde"}, // с - lowercase n, tilde
            {"ò", "ograve"}, // т - lowercase o, grave accent
            {"ó", "oacute"}, // у - lowercase o, acute accent
            {"ô", "ocirc"}, // ф - lowercase o, circumflex accent
            {"õ", "otilde"}, // х - lowercase o, tilde
            {"ö", "ouml"}, // ц - lowercase o, umlaut
            {"÷", "divide"}, // division sign
            {"ø", "oslash"}, // ш - lowercase o, slash
            {"ù", "ugrave"}, // щ - lowercase u, grave accent
            {"ú", "uacute"}, // ъ - lowercase u, acute accent
            {"û", "ucirc"}, // ы - lowercase u, circumflex accent
            {"ü", "uuml"}, // ь - lowercase u, umlaut
            {"ý", "yacute"}, // э - lowercase y, acute accent
            {"þ", "thorn"}, // ю - lowercase thorn, Icelandic
            {"ÿ", "yuml"}, // я - lowercase y, umlaut
    };
    private static final int MIN_ESCAPE = 2;
    private static final int MAX_ESCAPE = 6;
    private static final HashMap<String, CharSequence> lookupMap;

    static {
        lookupMap = new HashMap<>();
        for (final CharSequence[] seq : ESCAPES)
            lookupMap.put(seq[1].toString(), seq[0]);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static String unescape(final String input) {
        StringWriter writer = null;
        int len = input.length();
        int i = 1;
        int st = 0;
        while (true) {
            // look for '&'
            while (i < len && input.charAt(i - 1) != '&')
                i++;
            if (i >= len)
                break;

            // found '&', look for ';'
            int j = i;
            while (j < len && j < i + MAX_ESCAPE + 1 && input.charAt(j) != ';')
                j++;
            if (j == len || j < i + MIN_ESCAPE || j == i + MAX_ESCAPE + 1) {
                i++;
                continue;
            }

            // found escape
            if (input.charAt(i) == '#') {
                // numeric escape
                int k = i + 1;
                int radix = 10;

                final char firstChar = input.charAt(k);
                if (firstChar == 'x' || firstChar == 'X') {
                    k++;
                    radix = 16;
                }

                try {
                    int entityValue = Integer.parseInt(input.substring(k, j), radix);

                    if (writer == null)
                        writer = new StringWriter(input.length());
                    writer.append(input.substring(st, i - 1));

                    if (entityValue > 0xFFFF) {
                        final char[] chrs = Character.toChars(entityValue);
                        writer.write(chrs[0]);
                        writer.write(chrs[1]);
                    } else {
                        writer.write(entityValue);
                    }

                } catch (NumberFormatException ex) {
                    i++;
                    continue;
                }
            } else {
                // named escape
                CharSequence value = lookupMap.get(input.substring(i, j));
                if (value == null) {
                    i++;
                    continue;
                }

                if (writer == null)
                    writer = new StringWriter(input.length());
                writer.append(input.substring(st, i - 1));

                writer.append(value);
            }

            // skip escape
            st = j + 1;
            i = st;
        }

        if (writer != null) {
            writer.append(input.substring(st, len));
            return writer.toString();
        }
        return input;
    }

}