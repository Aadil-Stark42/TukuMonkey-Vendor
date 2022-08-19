package in.tukumonkeyvendor.addnewproduct.mvp_add;


import in.tukumonkeyvendor.addnewproduct.model_add.AddProductResponse;

public interface AddProductContract {
    void addproduct_success(AddProductResponse generalResponse);

    void addproduct_failure(String msg);

    void dashboard_logout();

    interface GetAddproductIntractor {

        interface OnFinishedListener {
            void onFinished(AddProductResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void getAddproductAPICall(GetAddproductIntractor.OnFinishedListener onFinishedListener);
    }
}
