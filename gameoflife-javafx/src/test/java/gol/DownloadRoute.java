package gol;

import spark.Request;
import spark.Response;
import spark.Route;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Needed, because a Filter can not be applied to Spark.staticFiles.
 */
public class DownloadRoute implements Route {

    private final Path fileRoot;

    public DownloadRoute(final Path fileRoot) {
        this.fileRoot = fileRoot;
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {

        final URI uri = URI.create(request.uri());
        String path = uri.getPath();
        path = path.replace("/files/", "");

        final Path fileToDownload = fileRoot.resolve(path);

        return Files.readAllBytes(fileToDownload);
    }
}
