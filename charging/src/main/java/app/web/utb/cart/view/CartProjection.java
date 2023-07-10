package app.web.utb.cart.view;

interface CartProjection {
    long getId();

    String getUmupNumber();

    String getDescription();

    long getCorrectChargings();

    long getFailedChargings();
}
