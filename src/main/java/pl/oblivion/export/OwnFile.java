package pl.oblivion.export;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OwnFile {
    private static final Logger logger = Logger.getLogger(OwnFile.class.getName());

    private static final String FILE_SEPARATOR = File.separator;

    private String path;
    private String name;

    public OwnFile(String path) {
        this.path = path.startsWith(FILE_SEPARATOR) ? path : FILE_SEPARATOR.concat(path);
        this.name = setName(path);

    }

    private String setName(String path) {
        String[] dirs = path.split(FILE_SEPARATOR);
        return dirs[dirs.length - 1];
    }

    public OwnFile(String... paths) {
        this.path = "";
        for (String part : paths) {
            this.path += (FILE_SEPARATOR.concat(part));
        }
        this.name = setName(this.path);
    }

    public OwnFile(OwnFile ownFile, String subFile) {
        this.path = ownFile.path.concat(FILE_SEPARATOR).concat(subFile);
        this.name = subFile;
    }

    public OwnFile(OwnFile ownFile, String... subFiles) {
        this.path = ownFile.path;
        for (String part : subFiles) {
            this.path += (FILE_SEPARATOR.concat(part));
        }
        this.name = setName(this.path);
    }

    public BufferedReader getReader() {
        try {
            InputStreamReader isr = new InputStreamReader(getInputStream());
            return new BufferedReader(isr);
        } catch (Exception e) {
            logger.error("Couldn't get reader for " + this.path, e);
            throw e;
        }
    }

    private InputStream getInputStream() {
        return Class.class.getResourceAsStream(this.path);
    }

    @Override
    public String toString() {
        return name;
    }
}
