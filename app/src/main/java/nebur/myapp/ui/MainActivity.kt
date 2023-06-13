package nebur.myapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nebur.myapp.R
import nebur.myapp.databinding.ActivityMainBinding
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var n: NavController
    private val vm: MainActivityVM by viewModels()

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                Log.e("locality", "isLocationEnabled")
                mFusedLocationClient.lastLocation.addOnSuccessListener { location->

                    if (location != null) {
                        try{
                            val geocoder = Geocoder(this, Locale.getDefault())
                            val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            val locality = list?.get(0)?.locality
                            locality?.let { vm.saveLocality(it) }
                        }
                        catch (_: Throwable) { }
                    }
                    else {
                        Log.e("locality", "location == null")
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        n = navHostFragment.navController

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()

        n.addOnDestinationChangedListener(vm.navListener)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.s.collect { s ->
                    updateUi(s)
                }
            }
        }

        b.btnMain.setOnClickListener {
            navigateTo(nebur.main.R.id.main_graph, vm.s.value.currentGraphId)
        }

        b.btnBag.setOnClickListener {
            navigateTo(nebur.bag.R.id.bag_graph, vm.s.value.currentGraphId)
        }
    }

    private fun updateUi(s: MainActivityVM.State) {
        updateBottomNav(s.currentGraphId)
    }

    private fun updateBottomNav(graph: Int) {
        val color1 = ContextCompat.getColor(this, nebur.common.R.color._3364E0)
        val color2 = ContextCompat.getColor(this, nebur.common.R.color._A5A9B2)

        b.icnMain.setColorFilter(color2)
        b.txtMain.setTextColor(color2)
        b.icnSearch.setColorFilter(color2)
        b.txtSearch.setTextColor(color2)
        b.icnBag.setColorFilter(color2)
        b.txtBag.setTextColor(color2)
        b.icnProfile.setColorFilter(color2)
        b.txtProfile.setTextColor(color2)

        when(graph) {
            nebur.main.R.id.main_graph -> {
                b.icnMain.setColorFilter(color1)
                b.txtMain.setTextColor(color1)
            }
            nebur.bag.R.id.bag_graph -> {
                b.icnBag.setColorFilter(color1)
                b.txtBag.setTextColor(color1)
            }
        }
    }

    private fun navigateTo(destinationId: Int, currentGraphId: Int) {
        if (destinationId == currentGraphId) {
            if(destinationId == nebur.main.R.id.main_graph) {
                n.popBackStack(n.graph.findStartDestination().id, false)
            }
            else {
                n.navigate(destinationId, null, navOptions {
                    launchSingleTop = true
                    popUpTo(n.graph.findStartDestination().id)
                })
            }
        }
        else {
            n.navigate(destinationId, null, navOptions {
                launchSingleTop = true
                restoreState = true
                popUpTo(n.graph.findStartDestination().id) {
                    saveState = true
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        n.removeOnDestinationChangedListener(vm.navListener)
    }
}