package com.app.id.pemmob

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.app.id.pemmob.model.biodata
import kotlinx.android.synthetic.main.activity_crud.*
import org.json.JSONObject

class crud : AppCompatActivity() {
    var arrayList = ArrayList<biodata>()
    lateinit var txtNim: EditText
    lateinit var txtNama: EditText
    lateinit var tvId: TextView
    lateinit var imgRemove: ImageView
    lateinit var imgEdit: ImageView

    lateinit var idBio: String
    lateinit var nim: String
    lateinit var nama: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        fetchData()

        btnTambah.setOnClickListener {
            popup()
        }

        lvBiodata.setOnItemClickListener { parent, view, position, id ->
            imgEdit = view.findViewById(R.id.imgEdit)
            imgRemove = view.findViewById(R.id.imgRemove)
            idBio = view.findViewById<TextView>(R.id.tvId).text.toString()
            nim = view.findViewById<TextView>(R.id.tvNim).text.toString()
            nama = view.findViewById<TextView>(R.id.tvNama).text.toString()

            imgEdit.setOnClickListener {
                popupUpdate()
            }

            imgRemove.setOnClickListener {
                deleteBiodata()
            }
        }
    }

    fun fetchData(){
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get("https://trepiscora.site/mobile/fetchBiodata.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()

                    val jsonArray = response?.optJSONArray("data")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Belum ada Biodata", Toast.LENGTH_SHORT).show()
                        lvBiodata.requestLayout()
                    }

                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray?.optJSONObject(i)

                        arrayList.add(
                            biodata(jsonObject.getInt("id"),
                                    jsonObject.getString("nim"),
                                    jsonObject.getString("nama"))
                        )

                        if(jsonArray?.length() - 1 == i){

                            loading.dismiss()
                            val adapter = adater(applicationContext,arrayList)
                            adapter.notifyDataSetChanged()
                            lvBiodata.adapter = adapter

                        }

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Gagal Fecth Biodata", Toast.LENGTH_SHORT).show()
                }
            })
    }

    @SuppressLint("InflateParams")
    fun popup(){
        val builder = AlertDialog.Builder(this@crud)
        val inflater = this@crud.layoutInflater
        val view = inflater.inflate(R.layout.addbiodata, null)
        builder.setTitle("Tambah biodata")

        txtNim = view.findViewById(R.id.txtNim)
        txtNama = view.findViewById(R.id.txtNama)

        builder.setPositiveButton("Tambah") { dialog, which ->
            addBiodata()
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        builder.setView(view)
        val al = builder.create()
        al.show()
    }

    fun addBiodata(){
        val loading = ProgressDialog(this)
        loading.setMessage("Tambah data...")
        loading.show()

        AndroidNetworking.post("https://trepiscora.site/mobile/addBiodata.php")
            .addBodyParameter("nim",txtNim.text.toString())
            .addBodyParameter("nama",txtNama.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    fetchData()
                    loading.dismiss()
                    Toast.makeText(this@crud, "Sukses tambah biodata", Toast.LENGTH_SHORT).show()
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Gagal Tambah Biodata", Toast.LENGTH_SHORT).show()
                }
            })
    }

    @SuppressLint("InflateParams")
    fun popupUpdate(){
        val builder = AlertDialog.Builder(this@crud)
        val inflater = this@crud.layoutInflater
        val view = inflater.inflate(R.layout.addbiodata, null)
        builder.setTitle("Ubah biodata")

        val txtNim = view.findViewById<EditText>(R.id.txtNim)
        val txtNama = view.findViewById<EditText>(R.id.txtNama)

        txtNim.setText(nim)
        txtNama.setText(nama)

        builder.setPositiveButton("Ubah"){dialog, which ->
            val loading = ProgressDialog(this)
            loading.setMessage("Mengubah data...")
            loading.show()

            AndroidNetworking.post("https://trepiscora.site/mobile/updateBiodata.php")
                .addBodyParameter("nim",txtNim.text.toString())
                .addBodyParameter("nama",txtNama.text.toString())
                .addBodyParameter("id",idBio)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {
                        fetchData()
                        loading.dismiss()
                        Toast.makeText(this@crud, "Sukses ubah biodata", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR",anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext,"Gagal ubah Biodata", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        builder.setView(view)
        val al = builder.create()
        al.show()
    }

    @SuppressLint("InflateParams")
    fun deleteBiodata(){
        val builder = AlertDialog.Builder(this@crud)
        builder.setTitle("Apakah anda yakin hapus biodata " + nim + " ?")

        builder.setPositiveButton("Ya"){dialog, which ->
            val loading = ProgressDialog(this)
            loading.setMessage("Menghapus data...")
            loading.show()

            AndroidNetworking.post("https://trepiscora.site/mobile/deleteBiodata.php")
                .addBodyParameter("id",idBio)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {
                        fetchData()
                        loading.dismiss()
                        Toast.makeText(this@crud, "Sukses hapus biodata", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR",anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext,"Gagal hapus Biodata", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        val al = builder.create()
        al.show()
    }


}