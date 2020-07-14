import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import java.util.*

class MainMapsFragment : SupportMapFragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationChangeListener {
    private lateinit var map: GoogleMap
    private var mk: Marker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load map len giao dien
        getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        //bat la ban len
        this.map = map
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.uiSettings.isZoomGesturesEnabled= true
        map.uiSettings.isZoomControlsEnabled = true
        map.isMyLocationEnabled = true
        
        //lang nghe su kien thay doi vi tri
        map.setOnMyLocationChangeListener(this)
    }

    override fun onMyLocationChange(location: Location) {
        val latLong = LatLng(location.latitude,location.longitude)
        
        //them marker vao
        if (mk == null){
            val marker = MarkerOptions()
            marker.position(latLong)
            marker.title("My Custom Location")
            marker.snippet(getAdress(latLong))
            marker.icon(BitmapDescriptorFactory.defaultMarker())
            mk = map.addMarker(marker)
            
            // Phong to Google Maps
            zoomCamera(latLong)
        }
        else{
            mk!!.position = latLong
            mk!!.snippet = getAdress(latLong)
        }
    }

    private fun zoomCamera(latLong: LatLng) {
        val camera = CameraUpdateFactory.newCameraPosition(
            CameraPosition(latLong,13f,1f,0f)
        )
        map.moveCamera(camera)

    }

    private fun getAdress(latLong: LatLng): String? {
        val geo = Geocoder(context, Locale.getDefault())
        val address = geo.getFromLocation(
            latLong.latitude,latLong.longitude,1
        )
        if (address.size == 0 ){
            return "Unknow Address"
        }
        val maxLine = address.get(0).maxAddressLineIndex
        var line =  address.get(0).getAddressLine(0)
        for (i in 1..maxLine-1){
            line+=", "+ address.get(0)
                .getAddressLine(i)
        }


        val city = address.get(0).getLocality()
        val state = address.get(0).getAdminArea()
        val country = address.get(0).getCountryName()
        val postalCode = address.get(0).getPostalCode()
        val knownName = address.get(0).getFeatureName()

        return line+", "+ city+", "+country
    }

}
