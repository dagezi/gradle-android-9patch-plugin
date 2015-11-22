package io.github.dagezi.ninepatch.plugin

import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
public class NinePatchPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.add(NinePatchExtension.NAME, NinePatchExtension)
    }
}