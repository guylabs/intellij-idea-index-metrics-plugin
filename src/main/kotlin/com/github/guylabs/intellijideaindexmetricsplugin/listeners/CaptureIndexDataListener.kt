@file:Suppress("UnstableApiUsage")

package com.github.guylabs.intellijideaindexmetricsplugin.listeners

import com.github.guylabs.intellijideaindexmetricsplugin.services.IndexDataStorage
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.util.indexing.diagnostic.ProjectIndexingHistory
import com.intellij.util.indexing.diagnostic.ProjectIndexingHistoryListener
import java.util.UUID

class CaptureIndexDataListener : ProjectIndexingHistoryListener, ProjectManagerListener {
    override fun onFinishedIndexing(projectIndexingHistory: ProjectIndexingHistory) {
        val indexDataStorage: IndexDataStorage = IndexDataStorage.instance
        indexDataStorage.state.totalIndexTime += projectIndexingHistory.times.indexingDuration.seconds
        indexDataStorage.state.indexExecutions[UUID.randomUUID().toString()] = mapOf(
            "indexingReason" to projectIndexingHistory.indexingReason.toString(),
            "totalTime" to projectIndexingHistory.times.indexingDuration.seconds.toString(),
            "wasFullIndexing" to projectIndexingHistory.times.wasFullIndexing.toString(),
            "scanFilesDuration" to projectIndexingHistory.times.scanFilesDuration.toString()
        )
    }

}
