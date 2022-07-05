package khusmanda.example.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import khusmanda.example.minesweeper.MineCells.FLAG
import khusmanda.example.minesweeper.MineCells.TRY

class MainActivity : AppCompatActivity() {
    private lateinit var reset_button: Button
    private lateinit var number_flag_marked: TextView
    private lateinit var number_of_mines: TextView
    var count: Int = 0
    var mcount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MineCells.resetModel()

        reset_button = findViewById<Button>(R.id.reset_button)
        reset_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                MineCells.resetModel()
            } })

        // uncover and flag button
        val toggle: ToggleButton = findViewById(R.id.flagtoggle)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if(isChecked) "Flag Mode ON" else "Uncover Mode ON", Toast.LENGTH_SHORT).show()
            if(isChecked){
                MineCells.mode = FLAG

            }
            else{
                MineCells.mode = TRY
            }
        }


        number_flag_marked = findViewById<TextView>(R.id.number_flag_marked)
        count = 20 - MineCells.numFlags
        number_flag_marked.setText("Marked Mines: " + count.toString())

        number_of_mines = findViewById<TextView>(R.id.number_of_mines)
        mcount = MineCells.numMines
        number_of_mines.setText("Mines: " + mcount.toString())



    }
}