package core;

import org.openntf.domino.xots.Tasklet;

import com.ibm.commons.util.io.json.JsonJavaObject;

@Tasklet(session = Tasklet.Session.CLONE)
public class BackgroundProcesses implements Runnable {
	private JsonJavaObject jsonData = null;
	private int processType = 0;

	public BackgroundProcesses(int tempProcessType, JsonJavaObject tempJsonData) {
		jsonData = tempJsonData;
		processType = tempProcessType;
	}

	public void run() {
		switch (processType) {
		case 1:// Function 1
			break;
		}
	}
}
