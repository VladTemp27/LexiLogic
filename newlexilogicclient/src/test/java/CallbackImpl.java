import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallbackPOA;

public class CallbackImpl extends PlayerCallbackPOA {
    private ControllerInterface controller;
    private String username;
    @Override
    public String username() {
        return this.username;
    }

    @Override
    public void username(String newUsername) {
        this.username = newUsername;
    }

    private void setController(ControllerInterface controller){
        this.controller = controller;
    }

    @Override
    public void uiCall(String jsonString) throws InvalidRequestException {
        controller.testUICall(jsonString);
    }
}
