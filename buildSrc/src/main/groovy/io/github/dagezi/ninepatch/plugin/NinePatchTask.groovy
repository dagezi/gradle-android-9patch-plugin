package io.github.dagezi.ninepatch.plugin

import com.android.build.gradle.api.ApplicationVariant
import com.android.builder.model.SourceProvider
import io.github.dagezi.ninepatch.NinePatch
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.util.function.Function
import java.util.stream.Stream

public class NinePatchTask extends DefaultTask {
    static final String NAME = "ninepatch"

    ApplicationVariant variant

    File outputDir

    Set<NinePatch> ninePatches

    @TaskAction
    public void run() {
        variant.sourceSets.stream()
                .flatMap(new Function<SourceProvider, Stream>() {

            @Override
            Stream apply(SourceProvider sourceProvider) {
                return sourceProvider.resDirectories.stream()
            }
        }).forEach { File resDir ->
            if (resDir == outputDir) {
                return
            }
            ninePatches.forEach { NinePatch ninePatch ->
                project.fileTree(
                        dir: resDir,
                        include: "drawable*/${ninePatch.name}.png",
                ).forEach { File inputFile ->
                    println "process $inputFile"
                }
            }
        }
    }
}