package com.project.myapplication.googlemap

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.project.myapplication.model.ClusterMarkerData

class MarkerClusterItem(
    latLng : LatLng,
    titleID : String?,
    pSnippet : String?,
    pData : ClusterMarkerData
): ClusterItem {
    private val position: LatLng = latLng
    private val title: String? = titleID
    private val snippet: String? = pSnippet
    private val data : ClusterMarkerData = pData

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    fun latLng():LatLng{
        return position
    }
}