package in.tukumonkeyvendor.addnewproduct.mvp_get;


import in.tukumonkeyvendor.addnewproduct.model_get.GetProductResponse;

public interface GetProductContract {

    void getproduct_success(GetProductResponse getProductResponse);

    void getproduct_failure(String msg);
    void dashboard_logout();

    interface GetproductIntractor {

        interface OnFinishedListener {
            void onFinished(GetProductResponse getProductResponse);
            void onFailure(String error_msg);
            void do_logout();

        }
        void getproductAPICall(GetproductIntractor.OnFinishedListener onFinishedListener);
    }
}
