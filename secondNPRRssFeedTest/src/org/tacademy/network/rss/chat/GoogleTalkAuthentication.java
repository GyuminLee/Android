package org.tacademy.network.rss.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;


public class GoogleTalkAuthentication extends SASLMechanism {

    public GoogleTalkAuthentication( SASLAuthentication saslAuthentication )
    {
        super( saslAuthentication );
    }

    @Override
    protected String getName()
    {
        return "X-GOOGLE-TOKEN";
    }

    @Override
    public void authenticate( String username, String host, String password ) throws IOException, XMPPException
    {
        super.authenticate( username, host, password );
    }

    @Override
    protected void authenticate() throws IOException, XMPPException
    {
        String authCode = password;
        String jidAndToken = "\0" + URLEncoder.encode( authenticationId, "utf-8" ) + "\0" + authCode;

        StringBuilder stanza = new StringBuilder();
        stanza.append( "<auth mechanism=\"" ).append( getName() );
        stanza.append( "\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">" );
        stanza.append( Base64.encodeBytes( jidAndToken.getBytes( "UTF-8" ) ) );

        stanza.append( "</auth>" );

        // Send the authentication to the server
        getSASLAuthentication().send( new AuthMechanism(getName(), Base64.encodeBytes( jidAndToken.getBytes( "UTF-8" ) )) );
    }
    
    
}
