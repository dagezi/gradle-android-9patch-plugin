package io.github.dagezi.ninepatch.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import io.github.dagezi.ninepatch.NinePatch

import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class NinePatchPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.add('ninepatch', project.container(NinePatch) {String name ->
          return new NinePatch(name)
        })

        project.afterEvaluate {
            def android = project.extensions.findByType(AppExtension)
            if (!android) {
                throw new Exception(
                        "Not an Android application; you forget `apply plugin: 'com.android.application`?")
            }
            Set<NinePatch> config = project.extensions.findByName('ninepatch')
            config.each { ninePatch ->
               println "name: ${ninePatch.name}, stretchables: ${ninePatch.stretchables}"
            }

            def tasks = new ArrayList<Task>();

            android.applicationVariants.all { ApplicationVariant variant ->
                def generatedResDir = getGeneratedResDir(project, variant)
                android.sourceSets.findByName(variant.name).res.srcDir(generatedResDir)

                def name = "${NinePatchTask.NAME}${capitalize(variant.name)}"
                def task = project.task(name, type: NinePatchTask) as NinePatchTask
                task.variant = variant
                task.outputDir = generatedResDir
                task.ninePatches = config
                tasks.add(task)
            }

            project.task(NinePatchTask.NAME, dependsOn: tasks);
        }
    }

    static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    static File getGeneratedResDir(Project project, ApplicationVariant variant) {
        return new File(project.buildDir,
                "generated/ninePatch/res/${variant.name}")
    }
}
