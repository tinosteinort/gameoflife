package gol.persistence;

import gol.Cell;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tino on 06.03.2016.
 */
@Component
public class ResourceLoaderService {

    private final static String RESOURCEFOLDER = "contextmenu/";
    private final List<String> resources = Arrays.asList(
            "Glider.lif",
            "Lightweight Spaceship.lif",
            "Middleweight Spaceship.lif",
            "Heavyweight Spaceship.lif");

    public List<ResourceFigure> loadBuildInFigures() {
        final List<ResourceFigure> figures = new ArrayList<>(resources.size());
        try {
            for (String resource : resources) {
                figures.add(loadResourceFigure(resource));
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error while loading Resource", ex);
        }
        return figures;
    }

    private ResourceFigure loadResourceFigure(final String resourceName) throws IOException {
        final ResourceFigure figure = new ResourceFigure(resourceNameAsName(resourceName));

        final String resourcePath = RESOURCEFOLDER + resourceName;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                figure.getCells().add(asCell(line));
            }
        }
        return figure;
    }

    private String resourceNameAsName(final String resourceName) {
        return resourceName.substring(0, resourceName.indexOf("."));
    }

    private Cell asCell(final String line) {
        final String[] coordinates = line.split("\\s");
        final int x = Integer.valueOf(coordinates[0]);
        final int y = Integer.valueOf(coordinates[1]);
        return new Cell(x, y);
    }
}
