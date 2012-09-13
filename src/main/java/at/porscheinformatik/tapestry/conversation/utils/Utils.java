package at.porscheinformatik.tapestry.conversation.utils;

/**
 * Utility Classes
 * 
 * @author Gerold Glaser
 */
public class Utils
{
    /**
     * Represents a failed index search.
     * 
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    private Utils()
    {
        // no construction possible
    }

    // Taken from apache commons lang

    /**
     * <p>
     * Replaces a String with another String inside a larger String, for the first <code>max</code> values of the search
     * String.
     * </p>
     * <p>
     * A <code>null</code> reference passed to this method is a no-op.
     * </p>
     * 
     * <pre>
     * StringUtils.replace(null, *, *, *)         = null
     * StringUtils.replace("", *, *, *)           = ""
     * StringUtils.replace("any", null, *, *)     = "any"
     * StringUtils.replace("any", *, null, *)     = "any"
     * StringUtils.replace("any", "", *, *)       = "any"
     * StringUtils.replace("any", *, *, 0)        = "any"
     * StringUtils.replace("abaa", "a", null, -1) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
     * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
     * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
     * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     * 
     * @param text text to search and replace in, may be null
     * @param searchString the String to search for, may be null
     * @param replacement the String to replace it with, may be null
     * @param max maximum number of values to replace, or <code>-1</code> if no maximum
     * @return the text with any replacements processed, <code>null</code> if null String input
     */
    public static String replace(String text, String searchString, String replacement, int max)
    {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0)
        {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == INDEX_NOT_FOUND)
        {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND)
        {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0)
            {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * <p>
     * Checks if a String is empty ("") or null.
     * </p>
     * 
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the String. That functionality is available in
     * isBlank().
     * </p>
     * 
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

}
