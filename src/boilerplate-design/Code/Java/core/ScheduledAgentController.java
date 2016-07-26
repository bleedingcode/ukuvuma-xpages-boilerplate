package core;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ScheduledAgentController implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTANTS
	private static final String SERVICE_PARAM_AGENT_TYPE = "agentType";

	public static void InitScheduledAgent() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		String agentType = "";

		try {
			agentType = request.getParameter(SERVICE_PARAM_AGENT_TYPE);

			switch (Integer.parseInt(agentType)) {
			case 1:// Cleanup Temp File Attachments
				FileAttachmentController.CleanupTempFileAttachments();
				break;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
