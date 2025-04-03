package com.shubhamtripz.iplbubble.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shubhamtripz.iplbubble.R
import com.shubhamtripz.iplbubble.presentation.main.MainActivity

@Composable
fun AuthScreen() {
    val context = LocalContext.current
    val auth = Firebase.auth
    val sharedPreferences = context.getSharedPreferences("IPL_Bubble_Prefs", Context.MODE_PRIVATE)

    LaunchedEffect(Unit) {
        val skippedLogin = sharedPreferences.getBoolean("skipped_login", false)
        if (auth.currentUser != null || skippedLogin) {
            navigateToMain(context)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleGoogleSignInResult(task, auth, context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ipl_logo),
                contentDescription = "IPL Bubble Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "Welcome to IPL Bubble",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            GoogleSignInButton {
                val signInIntent = provideGoogleSignInClient(context).signInIntent
                launcher.launch(signInIntent)
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = {
                sharedPreferences.edit().putBoolean("skipped_login", true).apply()
                navigateToMain(context)
            }) {
                Text(text = "Skip", color = Color.LightGray, fontSize = 16.sp)
            }
        }
    }
}


@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Logo",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Sign in with Google", color = Color.Black, fontSize = 16.sp)
    }
}

fun provideGoogleSignInClient(context: Context): com.google.android.gms.auth.api.signin.GoogleSignInClient {
    val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
        com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
    )
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

fun handleGoogleSignInResult(task: Task<com.google.android.gms.auth.api.signin.GoogleSignInAccount>, auth: FirebaseAuth, context: Context) {
    try {
        val account = task.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                navigateToMain(context)
            } else {
                Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    } catch (e: ApiException) {
        Log.e("AuthScreen", "Google Sign-In failed", e)
    }
}

fun navigateToMain(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
}
