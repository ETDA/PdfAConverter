package pdfbox;

/**
 * Convert file input from PDF to PDF/A-3
 * 
 * @author ETDA
 *
 */

public class Convert {

	/**
	 * Input argument with full path of file.
	 * 
	 * 
	 * @param args[input
	 *            file name, embed file name, color profile name, output file
	 *            name, document type, document file name, document version]
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String inputFilePath = "src/main/resources/sample.pdf";
		String embbedFilePath = "src/main/resources/ETDA-invoice.xml";
		String colorProfilePath = "src/main/resources/sRGB Color Space Profile.icm";
		String outputFilePath = "target/success.pdf";
		String documentType = "Tax Invoice";
		String docFileName = "ETDA-invoice.xml";
		String docVersion = "2.0";
		PDFA3Components pdfa3Components = new PDFA3Components(inputFilePath, embbedFilePath, colorProfilePath,
				outputFilePath, documentType, docFileName, docVersion);

		ConvertPDFtoA3.Convert(pdfa3Components);
	}

}
