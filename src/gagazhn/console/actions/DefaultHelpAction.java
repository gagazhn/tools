package gagazhn.console.actions;

public class DefaultHelpAction extends Action {
	private String helpInfo;
	
	public DefaultHelpAction() {
		super();
		
		helpInfo = "No help info avaliable.";
	}

	@Override
	public void doAct(String[] args) {
		System.out.println(helpInfo);
	}

	public void setHelpInfo(String help) {
		this.helpInfo = help;
	}
}
