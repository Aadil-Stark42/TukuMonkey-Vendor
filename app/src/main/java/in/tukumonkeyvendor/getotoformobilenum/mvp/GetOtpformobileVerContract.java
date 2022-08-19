package in.tukumonkeyvendor.getotoformobilenum.mvp;

import in.tukumonkeyvendor.signup.model.SignupResponse;

public interface GetOtpformobileVerContract {

    void sendotp_success(SignupResponse signupResponse);

    void sendotp_failure(String msg);

    void dashboard_logout();

    interface GetSendOtpIntractor {

        interface OnFinishedListener {
            void onFinished(SignupResponse signupResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void sendotpAPICall(OnFinishedListener onFinishedListener);
    }

}
