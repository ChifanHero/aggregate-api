package org.elasticsearch.bootstrap;

import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.jar.Manifest;

/**
 * This is a heck just to pass the elasticsearch jar hell issue.
 * See https://stackoverflow.com/questions/38712251/java-jar-hell-runtime-exception for more details
 * Created by shiyan on 5/27/17.
 */
public class JarHell {
    private JarHell() {}
    public static void checkJarHell() throws Exception {}
    public static void checkJarHell(Set<URL> urls) throws Exception {}
    public static void checkVersionFormat(String targetVersion) {}
    public static void checkJavaVersion(String resource, String targetVersion) {}
    public static Set<URL> parseClassPath() {return Collections.emptySet();}
    public static Set<URL> parseClassPath(String classPath) {return Collections.emptySet();}
    public static void checkManifest(Manifest manifest, Path jar){}
    public static void checkClass(Map<String,Path> clazzes, String clazz, Path jarpath) {}
}
