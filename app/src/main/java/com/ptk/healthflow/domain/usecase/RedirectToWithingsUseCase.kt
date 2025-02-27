package com.ptk.healthflow.domain.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.ptk.healthflow.util.Constants
import javax.inject.Inject

class RedirectToWithingsUseCase @Inject constructor() {
    suspend operator fun invoke(context: Context) {
        Log.e("testASDF", "HEllo REDIRECTUSECASE")

        val oauthUrl = Constants.AUTHORIZATION_URL +
                "authorize2?response_type=code" +
                "&client_id=${Constants.CLIENT_ID}" +
                "&state=Active" +
                "&redirect_uri=myapp://callback" +
                "&scope=user.info,user.metrics"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(oauthUrl))
        context.startActivity(intent) // Opens browser for login
        Log.e("testASDF", "StartActivity")

    }
}
