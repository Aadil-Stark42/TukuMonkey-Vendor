package in.tukumonkeyvendor.getoptforemail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.tukumonkeyvendor.R;
import in.tukumonkeyvendor.getoptforemail.mvp.GetOtpForEmailVerIntract;
import in.tukumonkeyvendor.getoptforemail.mvp.GetOtpForEmailVerPresenter;
import in.tukumonkeyvendor.otp_Verification.OTPEmailVerificationActivity;
import in.tukumonkeyvendor.signup.model.SignupResponse;
import in.tukumonkeyvendor.utils.BaseActivity;
import in.tukumonkeyvendor.utils.MnxConstant;
import in.tukumonkeyvendor.utils.MnxPreferenceManager;
import in.tukumonkeyvendor.getoptforemail.mvp.GetOtpForEmailVerContract;


public class GetOtpforEmailActivity extends BaseActivity implements GetOtpForEmailVerContract {

    TextView tv_verify,tv_email;
    ImageView iv_back;

    String TAG=GetOtpforEmailActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_add);

        try{
        MnxPreferenceManager.setString(MnxConstant.USERSTATE,"GetEmail");
        iv_back=findViewById(R.id.iv_back);
        tv_email=findViewById(R.id.tv_email);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (MnxPreferenceManager.getString(MnxConstant.REG_EMAIL,null)!=null)
            tv_email.setText(MnxPreferenceManager.getString(MnxConstant.REG_EMAIL,null));

        tv_verify=findViewById(R.id.tv_verify);
        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, " dvg d df dfh fdf : errorr rr + "+e.getMessage());
        }
    }


    private  void doapicall(){
        try {
            String strEmail= MnxPreferenceManager.getString(MnxConstant.REG_EMAIL,null);
            if (strEmail!=null){
                showLoading();
                GetOtpForEmailVerPresenter getOtpForEmailVerPresenter=new GetOtpForEmailVerPresenter(this,new GetOtpForEmailVerIntract());
                getOtpForEmailVerPresenter.validateDetails(strEmail);
            }
            else
                Toast.makeText(this,"Request Data Issue",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "do api call: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void getemailotp_success(SignupResponse signupResponse) {
        hideLoading();
        if (signupResponse!=null){
            if (signupResponse.getStatus()){
                Toast.makeText(this,signupResponse.getOtp(),Toast.LENGTH_LONG).show();
                Intent myintent=new Intent(GetOtpforEmailActivity.this, OTPEmailVerificationActivity.class);
                startActivity(myintent);
            }
        }
    }

    @Override
    public void getemailotp_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
