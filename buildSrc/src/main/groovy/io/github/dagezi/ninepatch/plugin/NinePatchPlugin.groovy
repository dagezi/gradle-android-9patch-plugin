package io.github.dagezi.ninepatch.plugin

import io.github.dagezi.ninepatch.NinePatch

import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project

public class NinePatchPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.add('ninepatch', project.container(NinePatch) {String name ->
          return new NinePatch(name)
        })

        project.afterEvaluate {
            def config = project.extensions.findByName('ninepatch')
            config.each { ninePatch ->
               println "name: ${ninePatch.name}, stretchables: ${ninePatch.stretchables}"
            }
        }
    }
}
