package com.github.guylabs.intellijideaindexmetricsplugin.services

import com.intellij.openapi.project.Project
import com.github.guylabs.intellijideaindexmetricsplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
