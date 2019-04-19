package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidator {
    private Pattern pattern;
    private Matcher matcher;

    /**
     * private final static -> create this variable only once. private final -> create this variable for every object. First one saves memory
     */
    // TODO change [1,15] to [3,15] when app will be finished
    private static final String USERNAME_PATTERN ="^[a-z0-9]{1,15}$";

    public RegistrationValidator() {
        this.pattern = pattern.compile(USERNAME_PATTERN);
    }

    /**
     * Validate username with regular expression
     * @param username username for validation
     * @return true valid username, false invalid username
     */
    public boolean validate(final String username){
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
