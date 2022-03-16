package com.osmar.examen.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.firebase.ktx.Firebase
import com.osmar.examen.R
import com.osmar.examen.databinding.ActivityMainBinding
import com.osmar.examen.services.LocationService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var  navController: NavController
    private lateinit var  vBind: ActivityMainBinding
    private var locationService: LocationService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBind.root)
        setupNavigationComponent()
        getLocationPermission()
    }

    /**
     * Crea la opciones de la ActionBar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * Configura las opciones del actionBar
     */
    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.my_directions ->{
                navController.navigate(R.id.directions)
                true
            }
            R.id.upload_pictures->{
                navController.navigate(R.id.upload_pictures)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    /**
     * Configura el componente de navegación
     */
    private fun setupNavigationComponent(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    /**
     * Pregunta por los permisos de ubicación
     */
    private fun getLocationPermission(){
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            if(isGranted){
                startService()
            }else{
                Toast.makeText(this, getString(R.string.location_permision_denied), Toast.LENGTH_SHORT).show()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startService()
                }
                shouldShowRequestPermissionRationale(getString(R.string.location))->{
                    AlertDialog.Builder(this).setTitle(R.string.location_permission).setMessage(R.string.location_permission_details).show()
                }
                else ->{
                    permissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            }
        }else{
            startService()
        }
    }

    /**
     * Inicia el servicío de ubicación
     */
    private fun startService(){
        val intent = Intent(this, LocationService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * Recibe el Binder cuando se junta el servicio de ubicación y Activity
     */
    private val serviceConnection: ServiceConnection = object : ServiceConnection {//Inicia comunicacion con proceso de ubicación
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        locationService = (service as LocationService.LocalBinder).getService()
    }

        override fun onServiceDisconnected(name: ComponentName?) {//finaliza  comunicación con proceso de ubicación
            locationService = null
        }

    }
}