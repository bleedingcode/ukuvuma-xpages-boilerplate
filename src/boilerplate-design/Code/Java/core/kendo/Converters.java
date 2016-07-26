package core.kendo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

public class Converters {

	public static void DecodeAndOutputBase64String(String FileName, String FileContentType, String StringToDecode,
			HttpServletResponse response) {

		byte[] decodedByteArray = null;
		decodedByteArray = DatatypeConverter.parseBase64Binary(StringToDecode);

		response.resetBuffer();

		response.setHeader("Content-Disposition", "attachment;filename=" + FileName);

		response.setContentType(FileContentType);
		response.setContentLength(decodedByteArray.length);
		try {
			response.getOutputStream().write(decodedByteArray);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception: " + e.getMessage());
		}
	}

	public static String DecodeBase64String(String FileName, String FileContentType, String StringToDecode,
			HttpServletResponse response) {
		byte[] decoded = DatatypeConverter.parseBase64Binary(StringToDecode);

		String returnValue = "";

		try {
			returnValue = new String(decoded, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnValue;
	}
}