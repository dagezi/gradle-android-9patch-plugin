package io.github.dagezi.ninepatch.plugin

import com.android.build.gradle.api.ApplicationVariant
import com.android.builder.model.SourceProvider
import io.github.dagezi.ninepatch.NinePatch
import io.github.dagezi.ninepatch.NinePatchCreator
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
        def errors = false
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
                        include: (ninePatch.getSrcs().collect {"drawable*/${it}.png"}),
                ).forEach { File inputFile ->
                    NinePatchCreator creator = new NinePatchCreator(
                            ninePatch, inputFile, outputDir)
                    try {
                        creator.create()
                    } catch (IllegalArgumentException ex) {
                        logger.error(ex.getMessage() + ':' + inputFile)
                        errors = true
                    }
                }
            }
        }
        if (errors) {
            throw new RuntimeException("Some errors in ninepatch")
        }
    }
}
