package com.example.ortalamahesapla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.yeni_ders_layout.view.*

class MainActivity : AppCompatActivity() {

    private val DERSLER = arrayOf(
        "Matematik",
        "Türkçe",
        "Fizik",
        "Algoritma",
        "Tarih"
    ) //adapter icin kaynak oluşturduk

    private var tumDerslerinBilgileri: ArrayList<Dersler> = ArrayList(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            DERSLER
        ) //adapter tanımlamasını yaptık
        etDersAd.setAdapter(adapter)//adapter'i etdersAd kısmına atadık ve çalışır hale getirdik

        btnDersEkle.setOnClickListener {
            if (!etDersAd.text.isNullOrEmpty()) { //ders adı boş değilse aşşağıdaki kod satırı çalışır

                var inflater = LayoutInflater.from(this)
                /*var inflater2=layoutInflater
                var inflater3=getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                inflater3.inflate()*/

                var yeniDersView = inflater.inflate(R.layout.yeni_ders_layout, null)

                yeniDersView.etYeniDersAd.setAdapter(adapter)

                //statik alandan kullanıcının girdiği değerleri alalım
                var dersAdi = etDersAd.text.toString()
                var dersKredi = spnDersKredi.selectedItem.toString()
                var dersHarf = spnDersNot.selectedItem.toString()

                yeniDersView.etYeniDersAd.setText(dersAdi)
                yeniDersView.spnYeniDersKredi.setSelection(
                    spinnerDegeriIndexiniBul(
                        spnDersKredi,
                        dersKredi
                    )
                )
                yeniDersView.spnYeniDersNot.setSelection(
                    spinnerDegeriIndexiniBul(
                        spnDersNot,
                        dersHarf
                    )
                )


                yeniDersView.btnDersSil.setOnClickListener {
                    rootLayout.removeView(yeniDersView)

                    if (rootLayout.childCount == 0) {                      //rootlayout icinde bulunan viewlar yani cocuklar 0 ise görünmesin(INVISIBLE)
                        // içinde view varsa görünsün(VISIBLE)
                        btnOrtalamaHesapla.visibility = View.INVISIBLE
                    } else btnOrtalamaHesapla.visibility = View.VISIBLE

                }


                rootLayout.addView(yeniDersView)

                btnOrtalamaHesapla.visibility = View.VISIBLE

                sifirla()

            } else {
                Toast.makeText(this, "Ders Adını Giriniz !", Toast.LENGTH_LONG)
                    .show() //ders adı girmediği taktirde mesaj verecek
            }

        }

    }

    fun sifirla() {
        etDersAd.setText("")
        spnDersKredi.setSelection(0)
        spnDersNot.setSelection(0)
    }


    fun spinnerDegeriIndexiniBul(spinner: Spinner, aranacakDeger: String): Int {

        var index = 0
        for (i in 0..spinner.count)//spinner uzunluğunu aldık count ile
        {
            if (spinner.getItemAtPosition(i).toString().equals(aranacakDeger)) {//qeuals ile aranacakDeger değeri girdiğimiz değer mi diye öğreniyoruz.
                index = i
                break
            }
        }

        return index
    }

    fun ortalamaHesapla(view: View) {

        var toplamNot = 0.0
        var toplamKredi = 0.0

        for (i in 0..rootLayout.childCount - 1) {
            var tekSatir = rootLayout.getChildAt(i)
            var geciciDers = Dersler(
                tekSatir.etYeniDersAd.text.toString(),
                ((tekSatir.spnYeniDersKredi.selectedItemPosition) + 1).toString(),
                tekSatir.spnYeniDersNot.selectedItem.toString()
            )

            tumDerslerinBilgileri.add(geciciDers)
        }
        for (oankiDers in tumDerslerinBilgileri) {
            toplamNot+=harfiNotaCevir(oankiDers.dersHarfNot)*(oankiDers.dersKredi.toDouble())
            toplamKredi+=oankiDers.dersKredi.toDouble()

        }
        Toast.makeText(this,"ORTALAMA: "+(toplamNot/toplamKredi),Toast.LENGTH_LONG).show()
        tumDerslerinBilgileri.clear()
    }

    fun harfiNotaCevir(gelenNotHarfDegeri: String): Double {

        var deger = 0.0
        when (gelenNotHarfDegeri) {

            "AA" -> deger = 4.0
            "BA" -> deger = 3.5
            "BB" -> deger = 3.0
            "BC" -> deger = 2.5
            "CC" -> deger = 2.0
            "DC" -> deger = 1.5
            "DD" -> deger = 1.0
            "FF" -> deger = 0.0

        }
        return deger

    }
}

