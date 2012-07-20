package gagazhn.console.actions;

public class DefaultGCAction extends Action{

	@Override
	public void doAct(String[] args) {
		System.gc();
		System.out.println("[System.gc()]");
	}

}
