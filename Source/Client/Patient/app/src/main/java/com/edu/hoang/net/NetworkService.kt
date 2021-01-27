package com.edu.hoang.net

import android.content.Context
import com.edu.hoang.R
import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.security.KeyStore
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.*

object NetworkService {
    private lateinit var hostname: String
    private lateinit var siteUrl: String
    const val NETWORK_TIMEOUT = 1000L
    private lateinit var retrofit: Retrofit

    private val timeDeserializer = object : JsonDeserializer<Time> {
        private val formatter = SimpleDateFormat("HH:mm:ss", Locale.US)
        override fun deserialize(
            json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?
        ): Time {
            val raw = json?.asString ?: "00:00:00"
            val timeInMillis = formatter.parse(raw)?.time ?: 0
            return Time(timeInMillis)
        }
    }

    private val dateSerializer = object : JsonSerializer<Date> {
        private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        override fun serialize(
            src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?
        ): JsonElement {
            val date = formatter.format(src ?: Date())
            return JsonPrimitive(date)
        }

    }

    fun initialize(context: Context) {
        hostname = context.resources.getString(R.string.host_server)
        siteUrl = "https://$hostname:8888"

        val gson = GsonBuilder().registerTypeAdapter(Time::class.java, timeDeserializer)
            .registerTypeAdapter(Date::class.java, dateSerializer)
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(siteUrl)
            .client(unsafeHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    private fun unsafeHttpClient(context: Context): OkHttpClient {
        val trustManager = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        ).run {
            init(readKeystore(context))
            trustManagers
        }[0] as X509TrustManager

        val sslSocketFactory = SSLContext.getInstance("TLS").run {
            init(null, arrayOf(trustManager), null)
            socketFactory
        }

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier { hostname, _ ->
                hostname == this.hostname
            }
            .build()
    }

    private fun readKeystore(context: Context): KeyStore {
        val password = "hoanghiep".toCharArray()
        val keyFileInput = context.resources.openRawResource(R.raw.hoanghiep);
        return KeyStore.getInstance("pkcs12").apply {
            load(keyFileInput, password)
            keyFileInput.close()
        }
    }

    val personalDetailsApi = lazy { retrofit.create(PersonalDetailsApi::class.java) }
    val prescriptionDetailsApi = lazy { retrofit.create(PrescriptionDetailsApi::class.java) }
    val testDetailsApi = lazy { retrofit.create(TestDetailsApi::class.java) }
    val clinicDetailsApi = lazy { retrofit.create(ClinicDetailsApi::class.java) }

}