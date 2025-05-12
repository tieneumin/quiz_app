package com.example.quizapp.core.service

import android.content.Context
import android.net.Uri
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.quizapp.data.repo.UserRepo
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthServiceImpl(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val userRepo: UserRepo,
) : AuthService {
    override suspend fun register(email: String, password: String): Boolean {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        authResult.user?.let {
            userRepo.addUser(it.uid, email)
            return true
        }
        return false
    }

    override suspend fun login(email: String, password: String): Boolean {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return authResult.user != null
    }

    override suspend fun loginWithGoogle(context: Context): Boolean {
        val idToken = getGoogleCredential(context)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
        authResult.user?.let { user ->
            val existingUser = userRepo.getUserById(user.uid)
            if (existingUser == null) {
                userRepo.addUser(user.uid, user.email!!)
            }
            return true
        }
        return false
    }

    override fun getUid(): String? = firebaseAuth.uid
    override suspend fun getUserRole(): String? = getUid()?.let {
        userRepo.getUserById(it)?.role
    }

    override fun getUserPhoto(): Uri? = firebaseAuth.currentUser?.photoUrl
    override fun logout() = firebaseAuth.signOut()

    private suspend fun getGoogleCredential(context: Context): String? {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("530421237489-5f2hb6npou51r2e627qvvq2cuur5vk64.apps.googleusercontent.com")
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = CredentialManager.create(context)
            .getCredential(context, request)
        return result.credential.data.getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN")
    }
}