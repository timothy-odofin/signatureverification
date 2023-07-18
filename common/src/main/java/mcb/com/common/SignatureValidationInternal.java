package mcb.com.common;

public class SignatureValidationInternal {

    private String signImage;
    private boolean isValid;

    public SignatureValidationInternal(String signImage, boolean isValid) {

        this.signImage = signImage;
        this.isValid = isValid;
    }

    public String getSignImage() {
        return signImage;
    }

    public boolean isValid() {
        return isValid;
    }
}
