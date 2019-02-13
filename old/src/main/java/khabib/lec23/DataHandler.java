package khabib.lec23;

import khabib.lec23.api.Downloader;
import khabib.lec23.api.Handler;
import khabib.lec23.api.Uploader;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DataHandler implements Handler {
    public Downloader downloader;
    public Uploader uploader;

    public DataHandler() {
        init();
    }

    private void init() {
        Iterator<Downloader> downloaderProviders = ServiceLoader.load(Downloader.class).iterator();
        if (downloaderProviders.hasNext()) downloader = downloaderProviders.next();

        Iterator<Uploader> upProviders = ServiceLoader.load(Uploader.class).iterator();
        if (upProviders.hasNext()) uploader = upProviders.next();

        System.out.println(uploader);
    }

    @Override
    public boolean process(String src, String dest) {
        Object obj = downloader.download(src);
        doSomeUsefulThings(obj);
        return uploader.upload(dest, obj);
    }

    private void doSomeUsefulThings(Object obj) {
        System.out.println("This is very useful stuff: " + obj);
    }
}
