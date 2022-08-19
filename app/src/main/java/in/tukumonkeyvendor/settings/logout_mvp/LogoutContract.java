package in.tukumonkeyvendor.settings.logout_mvp;


import in.tukumonkeyvendor.utils.GeneralResponse;

public interface LogoutContract {
    void logout_success(GeneralResponse generalResponse);

    void logout_failure(String msg);

    void dashboard_logout();


    interface GetlogoutIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void logoutAPICall(GetlogoutIntractor.OnFinishedListener onFinishedListener);
    }
}
