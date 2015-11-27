package io.github.dagezi.ninepatch.plugin;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;

// This code is derived from https://github.com/gfx/gradle-android-ribbonizer-plugin
// Copyright (c) 2015 FUJI Goro (gfx) gfuji@cpan.org.

public class Resources {

    public static String resourceFilePattern(String name) {
        if (name.startsWith("@")) {
            String[] pair = name.substring(1).split("/", 2);
            String baseResType = pair[0];
            String fileName = pair[1];
            if (fileName == null) {
                throw new IllegalArgumentException(
                        "Icon names does include resource types (e.g. drawable/ic_launcher): "
                                + name);
            }
            return baseResType + "*/" + fileName + ".*";
        } else {
            return name;
        }
    }

    public static String getLauncherIcon(File manifestFile)
            throws SAXException, ParserConfigurationException, IOException {
        GPathResult manifestXml = new XmlSlurper().parse(manifestFile);
        GPathResult applicationNode = (GPathResult) manifestXml.getProperty("application");
        return String.valueOf(applicationNode.getProperty("@android:icon"));
    }
}
