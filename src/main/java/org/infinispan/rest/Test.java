package org.infinispan.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.infinispan.rest.JDKHttpClient.*;

import static org.infinispan.rest.JDKHttpClient.*;

public class Test {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URI keyUri; Params params;

        // Store binary content
        keyUri = cacheKeyUri("key-bytes");

        params = Params.apply(Keys.BODY, Files.readAllBytes(Paths.get(Test.class.getResource("/SuperSmallFile.txt").toURI()))).add(Keys.CONTENT_TYPE, "application/octet-stream");
        System.out.println((Integer) put(keyUri, params.map()).get(Keys.STATUS_CODE));
    }

    static URI cacheKeyUri(String key) {
        return uri("localhost", 8080,
                String.format("/rest/___defaultcache/%s", key));
    }

    URI cacheUri() {
        return uri("localhost", 8080,
                String.format("/rest/___defaultcache"));
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
