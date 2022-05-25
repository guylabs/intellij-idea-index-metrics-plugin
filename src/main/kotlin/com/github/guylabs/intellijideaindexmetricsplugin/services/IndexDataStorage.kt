package com.github.guylabs.intellijideaindexmetricsplugin.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "index-data", storages = [Storage("indexdata.xml")])
class IndexDataStorage : PersistentStateComponent<IndexDataStorage?> {

    var totalIndexTime: Double = 0.0
    var indexExecutions: MutableMap<String, Map<String, String>> = mutableMapOf()

    override fun getState(): IndexDataStorage {
        return this
    }

    override fun loadState(state: IndexDataStorage) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: IndexDataStorage
            get() = ApplicationManager.getApplication().getService(IndexDataStorage::class.java)
    }

}
