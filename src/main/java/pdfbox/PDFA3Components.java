package pdfbox;

public class PDFA3Components {
	private String inputFilePath;
	private String embedFilePath;
	private String colorProfilePath;
	private String outputFilePath;
	private String documentType;
	private String documentFileName;
	private String documentVersion;
	/**
	 * 	 * Use for set file path (input and output)
	 * 
	 * @param inputFilePath : path of input PDF file, e.g. Test.pdf, invoice.pdf
	 * @param embedFilePath : path of attached file, e.g. example.xml
	 * @param colorProfilePath : path of color profile file, e.g. sRGB Color Space Profile.icm
	 * @param outputFilePath : name of output PDF/A-3 file, e.g. invoiceA3.pdf
	 * @param documentType : document type of PDF ,e.g. "Tax Invoice", "Credit Note", "Debit Note"
	 * @param documentFileName : name of embed file name
	 * @param documentVersion : document version
	 */
	public PDFA3Components(String inputFilePath, String embedFilePath, String colorProfilePath, String outputFilePath, String documentType, String documentFileName, String documentVersion) {
		super();
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.embedFilePath = embedFilePath;
		this.colorProfilePath = colorProfilePath;
		this.documentType = documentType;
		this.documentFileName = documentFileName;
		this.documentVersion = documentVersion;
	}
	public String getInputFilePath() {
		return inputFilePath;
	}
	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	public String getEmbedFilePath() {
		return embedFilePath;
	}
	public void setEmbedFilePath(String embedFilePath) {
		this.embedFilePath = embedFilePath;
	}
	public String getColorProfilePath() {
		return colorProfilePath;
	}
	public void setColorProfilePath(String colorProfilePath) {
		this.colorProfilePath = colorProfilePath;
	} 
	public String getOutputFilePath() {
		return outputFilePath;
	}
	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentFileName() {
		return documentFileName;
	}
	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}
	public String getDocumentVersion() {
		return documentVersion;
	}
	public void setDocumentVersion(String documentVersion) {
		this.documentVersion = documentVersion;
	}
	

}
