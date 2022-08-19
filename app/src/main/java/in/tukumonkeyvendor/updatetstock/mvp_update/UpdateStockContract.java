package in.tukumonkeyvendor.updatetstock.mvp_update;


import in.tukumonkeyvendor.utils.GeneralResponse;

public interface UpdateStockContract {

    void updatestock_success(GeneralResponse loginResponse);

    void updatestock_failure(String msg);

    void dashboard_logout();

    interface updatestockProductIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void updatestockAPICall(updatestockProductIntractor.OnFinishedListener onFinishedListener);
    }
}
