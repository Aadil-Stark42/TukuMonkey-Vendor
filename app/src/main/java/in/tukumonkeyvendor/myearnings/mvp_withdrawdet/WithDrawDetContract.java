package in.tukumonkeyvendor.myearnings.mvp_withdrawdet;


import in.tukumonkeyvendor.myearnings.model_withdrawdet.WithDrawDetailResponse;

public interface WithDrawDetContract {

    void withdrawdet_success(WithDrawDetailResponse withDrawDetailResponse);

    void withdrawdet_failure(String msg);

    void dashboard_logout();

    interface GetwithdrawdetIntractor {

        interface OnFinishedListener {
            void onFinished(WithDrawDetailResponse withDrawDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void withdrawdetAPICall(GetwithdrawdetIntractor.OnFinishedListener onFinishedListener);
    }
}
