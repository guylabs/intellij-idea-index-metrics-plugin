@file:Suppress("UnstableApiUsage")

package com.github.guylabs.intellijideaindexmetricsplugin.listeners

import com.github.guylabs.intellijideaindexmetricsplugin.services.IndexDataStorage
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.util.indexing.diagnostic.IndexDiagnosticDumper.ProjectIndexingHistoryListener
import com.intellij.util.indexing.diagnostic.ProjectIndexingHistory

class CaptureIndexDataListener() : ProjectIndexingHistoryListener, ProjectManagerListener {

    override fun onFinishedIndexing(projectIndexingHistory: ProjectIndexingHistory) {
        val indexDataStorage: IndexDataStorage = IndexDataStorage.instance
        indexDataStorage.state.totalIndexTime.plus(projectIndexingHistory.times.indexingDuration.seconds)
    }

}
