package gagazhn.console.actions;

public class DefaultExitAction extends Action {
	public DefaultExitAction() {
		super();
	}

	@Override
	public void doAct(String[] args) {
		System.exit(0);
	}

}
