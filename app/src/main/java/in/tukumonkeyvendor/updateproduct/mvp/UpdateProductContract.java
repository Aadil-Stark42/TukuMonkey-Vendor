package in.tukumonkeyvendor.updateproduct.mvp;


import in.tukumonkeyvendor.utils.GeneralResponse;

public interface UpdateProductContract {

    void updateproduct_success(GeneralResponse loginResponse);

    void updateproduct_failure(String msg);

    void dashboard_logout();

    interface GetUpdateProductIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void updateproductAPICall(GetUpdateProductIntractor.OnFinishedListener onFinishedListener);
    }
}
