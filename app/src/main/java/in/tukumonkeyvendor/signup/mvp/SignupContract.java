package in.tukumonkeyvendor.signup.mvp;


import in.tukumonkeyvendor.signup.model.SignupResponse;

public interface SignupContract {

    void signup_success(SignupResponse signupResponse);

    void signup_failure(String msg);

    void dashboard_logout();

    interface GetSignupIntractor {

        interface OnFinishedListener {
            void onFinished(SignupResponse signupResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void signupAPICall(OnFinishedListener onFinishedListener);
    }

}
