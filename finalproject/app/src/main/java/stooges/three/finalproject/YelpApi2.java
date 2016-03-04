package stooges.three.finalproject;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * Created by Thomas on 3/4/2016.
 */
public class YelpApi2  extends DefaultApi10a {

    @Override
    public String getRequestTokenEndpoint() {
        return null;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return null;
    }
}
