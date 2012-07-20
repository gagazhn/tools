package gagazhn.console.actions;

public abstract class Action{
	// Action可能用到了一些对象实例，在命令被接收后，Action可以
	//  对这些实例进行对应操作
	protected Object[] objects;
	
	public Action() {
		
	}
	
	public Action(Object[] objects) {
		this.objects = objects;
	}
	
	
	public abstract void doAct(String[] args);
}
