package gol.jnlpserver;

import com.github.tinosteinort.beanrepository.BeanRepository;
import spark.Spark;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StartTestServer {

    private final BeanRepository repo;

    /**
     * Files are available on 'http://localhost:8123/files'
     *
     * JNLP File: http://localhost:8123/files/GameOfLife.jnlp
     *
     * fileRoot has to point to compiled Code (with dependencies)
     */
    public StartTestServer() {

        final Path fileRoot = Paths.get("gameoflife-javafx/target/jnlp/");

        repo = new BeanRepository.BeanRepositoryBuilder()
                .singleton(ContentTypeFilter.class, ContentTypeFilter::new)
                .singleton(DownloadRoute.class, () -> new DownloadRoute(fileRoot))
                .build();
    }

    public void start() {

        Spark.port(8123);
        Spark.get("/files/*", repo.getBean(DownloadRoute.class));

        Spark.after(repo.getBean(ContentTypeFilter.class));
    }

    public static void main(String[] args) {
        new StartTestServer().start();
    }
}

