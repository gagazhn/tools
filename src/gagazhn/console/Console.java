package gagazhn.console;

import gagazhn.console.actions.Action;
import gagazhn.console.actions.DefaultExitAction;
import gagazhn.console.actions.DefaultGCAction;
import gagazhn.console.actions.DefaultHelpAction;
import gagazhn.console.actions.DefaultIgnoreAction;
import gagazhn.console.actions.DefaultGoAction;
import gagazhn.console.actions.DefaultInputAction;

import java.util.HashMap;
import java.util.Scanner;

/*
 * 控制台工具,程序运行听到,从shell中读入用户输入的命令,发出相应的动作
 * 
 * 控制台独立维护一个线程监听用户输入信息,映射到相应命令
 * 	其中,控制台的动作分为两种形式:立即式,和标记式
 * 	1.立即式:控制台获得命令后立即产生动作
 * 	2.标注式:控制台获得命令后,将某个全局标记赋值,这个标记可以被控制台的使用者用以判定
 * 
 * 作者，gaga.zhn@gmail.com
 */

public class Console {
	private static Console me;
	
	private Object lock;
	
	private Scanner scanner;
	
	private Action defHeAction;
	private Action defPaAction;
	private Action defExAction;
	private Action defIgAction;
	private Action defInAction;
	private Action defGCAction;
	
	private Action defAction;
	
	// 是否需要把所有输入传入DefaultInputAction
	private boolean isInput;
	private String[] userInput;
	
	// 控制台是否已经运行
	// !!! 目前自己使用,没用加上同步处理
	private boolean hasRun;
	
	// 命令与动作的映射
	private HashMap<String, Action> map;
	
	private Console() {
		// init
		map = new HashMap<String, Action>();
		hasRun = false;
		isInput = false;
		userInput = null;
		
		// lock
		lock = new Object();
		
		// 获得输入流
		this.scanner = new Scanner(System.in);
		
		// 载入一些默认的命令
		defHeAction = new DefaultHelpAction();
		defPaAction = new DefaultGoAction(this);
		defExAction = new DefaultExitAction();
		defIgAction = new DefaultIgnoreAction();
		defInAction = new DefaultInputAction(this);
		defGCAction = new DefaultGCAction();
		
		defAction = defHeAction;
		
		map.put("go", defPaAction);
		map.put("help", defHeAction);
		map.put("?", defHeAction);
		map.put("exit", defExAction);
		map.put("quit", defExAction);
		map.put("", defIgAction);
		map.put("gc", defGCAction);
		
	}
	
	public static Console instance() {
		if (me == null) {
			me = new Console();
		}
		
		return me;
	}
	
	public void run() {
		if (hasRun) {
			// ignore
		} else {
			new MainThread().start();
		}
	}
	
	public void setHelpInfo(String helpInfo) {
		((DefaultHelpAction)defHeAction).setHelpInfo(helpInfo);
	}
	
	// 自定义命令
	public Action setAction(String command, Action action) {
		return this.map.put(command.toLowerCase(), action);
	}
	
	// 输入为'空'时,默认动作
	public Action setDefaultAction(Action action) {
		return defAction = action;
	}
	
	// 暂停当前线程,提示信息
	public void pause(String info) {
		System.out.println(info);
		System.out.println("Type 'go' to continue.");
		synchronized (lock) {
			try {
				lock.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 暂停当前线程
	public void pause() {
		synchronized (lock) {
			try {
				lock.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void go() {
		synchronized (lock) {
			try {
				lock.notifyAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String[] getInput() {
		isInput = true;
		pause();
		
		return userInput;
	}
	
	public void setUserInput(String[] input) {
		this.userInput = input;
		this.isInput = false;
	}
	
	private class MainThread extends Thread {
		public MainThread() {
			super();
		}
		
		@Override
		public void run() {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] commands = line.split(" ");
				
				if (isInput) {
					// 调用inputAction时不需要使用新线程
					defInAction.doAct(commands);
				} else {
					Action action = map.get(commands[0].toLowerCase());
					
					// 命令为空表明命令输入错误.
					if (action == null) {
						System.out.println("Invalid command for: " + line);
						
						new ActionWrapper(commands, defAction).start();
					} else {
						new ActionWrapper(commands, action).start();
					}
				}
			}
		}
	}
	
	private class ActionWrapper extends Thread {
		private Action action;
		private String[] commands;
		
		public ActionWrapper(String[] commands, Action action) {
			super();
			
			this.commands = commands;
			this.action = action;
		}
		
		@Override
		public void run() {
			action.doAct(commands);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Console:");
		
		Console console = Console.instance();
		console.run();
		System.out.println("SSS:　" + console.getInput()[0]);
		

	}
}
