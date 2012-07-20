package gagazhn.console.actions;

import gagazhn.console.Console;

public class DefaultGoAction extends Action {
	private Console console;
	
	public DefaultGoAction(Console console) {
		this.console = console;
	}

	@Override
	public void doAct(String[] args) {
		console.go();
	}
}
