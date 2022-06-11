package com.example.aplikasisqliteadamnurthoriq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adpMatkul: AdapterMataKuliah
    private lateinit var dataMatkul: ArrayList<MataKuliah>
    private lateinit var lvMatakuliah: ListView
    private lateinit var linTidakAda: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvMatakuliah = findViewById(R.id.lvMataKuliah)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataMatkul = ArrayList()
        adpMatkul = AdapterMataKuliah(this@MainActivity, dataMatkul)

        lvMatakuliah.adapter = adpMatkul

        refresh()
        btnTambah.setOnClickListener{
            val i= Intent(this@MainActivity, EntriMatkulActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) refresh()
    }

    private fun refresh() {
        val db = DbHelper(this@MainActivity)
        val data = db.tampil()
        repeat(dataMatkul.size) { dataMatkul.removeFirst()}
        if (data.count > 0){
            while (data.moveToNext()){
                val matkul = MataKuliah(
                    data.getString(0),
                    data.getString(1),
                    data.getInt(2),
                    data.getString(3)
                )
                adpMatkul.add(matkul)
                adpMatkul.notifyDataSetChanged()
            }
            lvMatakuliah.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvMatakuliah.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}