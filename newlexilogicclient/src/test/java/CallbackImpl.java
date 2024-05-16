import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallbackPOA;

public class CallbackImpl extends PlayerCallbackPOA {
    private String username;
    @Override
    public String username() {
        return this.username;
    }

    @Override
    public void username(String newUsername) {
        this.username = newUsername;
    }

    @Override
    public void uiCall(String jsonString) throws InvalidRequestException {

    }
}
