package edu.uniandes.moni.view

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import edu.uniandes.moni.R
import edu.uniandes.moni.navigation.AppNavigation
import edu.uniandes.moni.view.theme.MoniTheme
import edu.uniandes.moni.viewmodel.NetworkStatusChecker
import edu.uniandes.moni.viewmodel.TutoringViewModel
import java.lang.Math.sqrt
import java.util.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f
    private var tutoringViewModel = TutoringViewModel()

    private lateinit var networkStatusChecker: NetworkStatusChecker
    private lateinit var connectivityObserver: ConnectivityObserver
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        tutoringViewModel.getAllTutorings()
        connectivityObserver=NetworkConnectivityObserver(applicationContext)
        super.onCreate(savedInstanceState)
        networkStatusChecker = NetworkStatusChecker(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)
            ?.registerListener(
                sensorListener, sensorManager!!
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
            )

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH
        setContent {
            MoniTheme() {
                // A surface container using the 'background' color from the theme
                val status by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Available )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    if (status==ConnectivityObserver.Status.Available){
                        AppNavigation()
                    }
                    else{

                        noInternet(status.toString())
                    }
                }
            }
        }
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            // Display a Toast message if
            // acceleration value is over 12
//            println(acceleration)
            if (acceleration > 13) {
//                Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show()
                setContent {
                    MoniTheme {
                        RenderHelpBottomSheetDialog()
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        sensorManager?.registerListener(
            sensorListener, sensorManager!!.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            ), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        networkStatusChecker.unregisterCallback()
        super.onPause()
    }
    @Composable
    fun noInternet(status: String){
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(15.dp),
            contentAlignment = Alignment.Center,


        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nosignal),
                    contentDescription = "no internet"
                )

                Text(text = "The Network status is: $status")
            }

        }
    }

}
