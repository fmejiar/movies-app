package com.fmejiar.moviesapp.core

import com.fmejiar.moviesapp.application.AppConstants.SOCKET_HOSTNAME
import com.fmejiar.moviesapp.application.AppConstants.SOCKET_PORT
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object InternetCheck {

    suspend fun isNetworkAvailable() = coroutineScope {
        return@coroutineScope try {
            val sock = Socket()
            val socketAddress = InetSocketAddress(SOCKET_HOSTNAME, SOCKET_PORT)
            sock.connect(socketAddress, 2000)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }

}