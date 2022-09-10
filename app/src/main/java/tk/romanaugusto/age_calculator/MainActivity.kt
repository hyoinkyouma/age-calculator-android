package tk.romanaugusto.age_calculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        val btnDate: Button = findViewById(R.id.dateBtn)
        val textDate: TextView = findViewById(R.id.dateText)
        val textMins: TextView = findViewById(R.id.minutesText)
        launch(textDate, textMins)
        btnDate.setOnClickListener {
            setDate(textDate, textMins)

        }
    }
    private data class DateData (val current:Map<Char, Short>, val selected:Map<Char, Short>)
    private fun setDate (date:TextView, mins:TextView){
        val calendar = Calendar.getInstance()
        val currentDate = mapOf('y' to calendar.get(Calendar.YEAR).toShort(),
            'm' to calendar.get(Calendar.MONTH).toShort(),
            'd' to calendar.get(Calendar.DAY_OF_MONTH).toShort())

        DatePickerDialog(this,
            {v, y, m, d ->
                date.text = "$d/$m/$y"
                val dateDate = DateData(currentDate, mapOf( 'y' to y.toShort(), 'm' to m.toShort(), 'd' to d.toShort() ))
                mins.text = dateToMinutes(dateDate).toString()

            },
            currentDate['y']!!.toInt(),
            currentDate['m']!!.toInt(),
            currentDate['d']!!.toInt()
            ).show()

    }

    private fun launch (textDate: TextView, mins:TextView) {
        val calendar = Calendar.getInstance()
        val currentDate = mapOf('y' to calendar.get(Calendar.YEAR).toShort(),
            'm' to calendar.get(Calendar.MONTH).toShort(),
            'd' to calendar.get(Calendar.DAY_OF_MONTH).toShort())
        textDate.text = "${currentDate['d']}/${currentDate['m']}/${currentDate['y']}"

        val startData = DateData(currentDate, currentDate)
        mins.text = dateToMinutes(startData).toString()
    }

    private fun dateToMinutes (dateData: DateData):Long {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        val current = sdf.parse("${dateData.current['d']}/${dateData.current['m']}/${dateData.current['y']}")
        val selected = sdf.parse("${dateData.selected['d']}/${dateData.selected['m']}/${dateData.selected['y']}")

        return (current.time - selected.time) / 60_000
    }



}