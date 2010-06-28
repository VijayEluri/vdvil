package org.codehaus.httpcache4j.cache;

import org.apache.commons.httpclient.HttpClient;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.client.HTTPClientResponseResolver;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

public class VdvilCacheStuff {


    public final HTTPCache persistentcache;
    final File storeLocation;
    final String storeName = "vaudeville";

    /**
     *
     * @param location Where to persist the cache files
     */
    public VdvilCacheStuff(File location) {
        storeLocation = location;
        persistentcache = new HTTPCache(new PersistentCacheStorage(1000, location, storeName), new HTTPClientResponseResolver(new HttpClient()));
    }

    /**
     * @param url location of stream to download
     * @return InputStream
     */
    public InputStream fetchAsInputStream(String url) {
        HTTPRequest request = new HTTPRequest(URI.create(url));
        HTTPResponse response = persistentcache.doCachedRequest(request);
        return response.getPayload().getInputStream();
    }


    /**
     * @param url location of file to download
     * @return the file or null if file not found. Not a good thing to do!
     */
    public File fetchAsFile(String url) {
        HTTPRequest mp3Request = new HTTPRequest(URI.create(url));
        HTTPResponse mp3Response = persistentcache.doCachedRequest(mp3Request);
        if (mp3Response.getPayload() instanceof CleanableFilePayload) {
            CleanableFilePayload cleanableFilePayload = (CleanableFilePayload) mp3Response.getPayload();
            return cleanableFilePayload.getFile();
        } else
            return null;
    }
}
