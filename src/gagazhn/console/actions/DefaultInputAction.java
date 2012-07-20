package gagazhn.console.actions;

import gagazhn.console.Console;

public class DefaultInputAction extends Action {
	private Console console;
	
	public DefaultInputAction(Console console) {
		this.console = console;
	}
	
	// 机制有同步的问题,暂时没有解决,不影响简单应用
	@Override
	public void doAct(String[] args) {
		console.setUserInput(args);
		console.go();
	}
}
