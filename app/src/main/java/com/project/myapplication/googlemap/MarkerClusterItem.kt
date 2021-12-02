package com.project.myapplication.googlemap

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.project.myapplication.model.ClusterMarkerModel

class MarkerClusterItem(
    latLng : LatLng,
    titleID : String?,
    pSnippet : String?,
    pModel : ClusterMarkerModel
): ClusterItem {
    private val position: LatLng = latLng
    private val title: String? = titleID
    private val snippet: String? = pSnippet
    private val model : ClusterMarkerModel = pModel

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? { // Diary의 ID 임
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    fun latLng():LatLng{
        return position
    }

    fun diaryData(): ClusterMarkerModel {
        return model
    }
}