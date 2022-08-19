package in.tukumonkeyvendor.slot.mvp_getdetail;


import in.tukumonkeyvendor.slot.model_getdetail.SlotDetailResponse;

public interface SlotDeatilContract {

    void slotdetail_success(SlotDetailResponse slotDetailResponse);

    void slotdetail_failure(String msg);

    void dashboard_logout();

    interface GetslotdetailIntractor {

        interface OnFinishedListener {
            void onFinished(SlotDetailResponse slotDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void slotdetailAPICall(GetslotdetailIntractor.OnFinishedListener onFinishedListener);
    }
}
