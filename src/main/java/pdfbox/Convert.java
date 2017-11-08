package pdfbox;

public class Convert {

	public static void main(String[] args) throws Exception {
		String inputFileName = args[0];
		String embedFileName = args[1];
		String colorProfileName = args[2];
		String outputFile = args[3];
		String documentType = args[4];

		ConvertPDFtoA3.Convert(inputFileName, embedFileName, outputFile, colorProfileName,documentType);
	}
}
