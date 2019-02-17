package com.winsonmac.core.helper

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.util.concurrent.TimeUnit


/* =========================================================================================== */
/*  Create api service instance                                                                */
/* =========================================================================================== */

class APIManager private constructor(
    private val url: String,
    private val headers: Map<String, String>?

) : Serializable {

    companion object {

        // Singleton object
        private var api: Retrofit? = null


        /**
         * Check retrofit instance, if null create new.
         * Then return an instance
         *
         * @return retrofit
         */
        fun newInstance(url: String, headers: Map<String, String>? = null): Retrofit {
            if (api == null)
                synchronized(APIManager::class.java) {
                    if (null == api)
                        api = APIManager(
                            url,
                            headers
                        ).createRetrofit()

                }
            return api!!
        }

        /**
         * Retrieve a created instance, if null rails error
         */
        fun getInstance(): Retrofit {
            if (api == null) {
                throw RuntimeException("You must call newInstance() method before using this.")
            }
            return api!!
        }
    }


    /**
     * Create a retrofit instance
     *
     * @return instance
     */
    private fun createRetrofit(): Retrofit {
        try {
            // For http logging
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            // Create request client
            val httpBuilder = OkHttpClient.Builder()
            httpBuilder.connectTimeout(60, TimeUnit.SECONDS)
            httpBuilder.readTimeout(60, TimeUnit.SECONDS)
            httpBuilder.writeTimeout(60, TimeUnit.SECONDS)
            httpBuilder.addInterceptor(interceptor)
            httpBuilder.addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .method(original.method(), original.body())

                headers?.forEach { it ->
                    requestBuilder.addHeader(it.key, it.value)
                }

                chain.proceed(requestBuilder.build())
            }

            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpBuilder.build())
                .build()

        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}