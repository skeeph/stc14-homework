package khabib.lec23_injections;

import khabib.lec23_injections.api.Downloader;
import khabib.lec23_injections.api.Handler;
import khabib.lec23_injections.api.Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.ServiceLoader;

@Component
public class DataHandler implements Handler {
    private Downloader downloader;
    private Uploader uploader;

    @Autowired
    public DataHandler(Downloader downloader, Uploader uploader) {
        this.downloader = downloader;
        this.uploader = uploader;
    }

    private void initSPI() {
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
