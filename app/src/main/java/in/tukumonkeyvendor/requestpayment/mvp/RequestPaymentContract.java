package in.tukumonkeyvendor.requestpayment.mvp;


import in.tukumonkeyvendor.requestpayment.model.PaymentRequestResponse;

public interface RequestPaymentContract {

    void requestpayment_success(PaymentRequestResponse paymentRequestResponse);

    void requestpayment_failure(String msg);

    void dashboard_logout();

    interface GetrequestpaymentIntractor {

        interface OnFinishedListener {
            void onFinished(PaymentRequestResponse paymentRequestResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void requestpaymentAPICall(GetrequestpaymentIntractor.OnFinishedListener onFinishedListener);
    }
}
