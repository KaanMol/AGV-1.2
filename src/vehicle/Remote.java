package vehicle;

import hardware.Infrared;
import interfaces.InfraredUpdater;
import interfaces.Updatable;

public class Remote implements Updatable {
    private InfraredUpdater callback;
    private Infrared infrared;

    public Remote(InfraredUpdater callback) {
        this.callback = callback;
        this.initialize();
    }

    public void initialize() {
        infrared = new Infrared(configuration.Remote.INFRA_RED_PIN);
    }

    public void update() {
        int remoteCode = infrared.getRemoteCode();

        if (remoteCode != -1) {
            this.callback.onInfraredCommandUpdate(remoteCode);
        }
    }
}
