package desktop.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import desktop.todo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            // 로그인 실패
        } else if (token != null) {
            // 로그인 성공
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
        // naver SDK 초기화
        NaverIdLoginSDK.initialize(this, getString(R.string.naver_client_id), getString(R.string.naver_client_secret), "두잇두잇")

        binding.kakaoLoginButton.setOnClickListener {
            kakaoLogin()
        }

        binding.naverLoginButton.setOnClickListener {
            naverLogin()
        }

    }

    private fun kakaoLogin() {
        // 카카오톡 로그인 가능하면
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->

                if (error != null) {
                    // 카카오톡 로그인 실패
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    // 로그인 성공
                    // Log.e("login", "token == $token")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        } else {
            // 카카오계정 로그인
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun naverLogin() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.e("login", "네이버 로그인 실패. $message")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
                Log.e("login", "네이버 로그인 에러. $message")
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

}