package com.groovycalamari.iawriterblog.gradle

import groovy.transform.Internal
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.CopySpec
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.provider.Provider

class CopyTemplateFonts extends DefaultTask {
    @Input
    final Property<File> iatemplate = project.objects.property(File)

    @Internal
    @Input
    final Provider<File> sourceDir = iatemplate.map { new File(it.absolutePath + '/Contents/Resources') }

    File outputDir

    @OutputDirectory
    File getOutputDir() {
        return this.outputDir
    }

    CopyTemplateFonts() {
        outputDir = project.buildDir
    }

    @TaskAction
    void copyCss() {
        project.copy(new Action<CopySpec>() {
            @Override
            void execute(CopySpec copySpec) {
                copySpec.from(sourceDir)
                copySpec.into(outputDir)
                copySpec.include("*.eot", "*.woff", "*.woff2")
            }
        })
    }
}
