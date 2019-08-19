package pdfbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collections;
import java.util.GregorianCalendar;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;

/**
 * The ConvertPDFtoPDFA3 method is used to convert any kind of PDF(.pdf) to
 * PDF/A-3
 * 
 * @author ETDA
 * 
 */
public class ConvertPDFtoA3 {

	private static float pdfVer = 1.7f;

	/**
	 * The convertPDFtoPDFA3(FilePaths, String, String, String) method is used
	 * to convert any kind of PDF(.pdf) to PDF/A-3
	 * 
	 * @param pdfa3Components
	 * @throws Exception
	 */
	public static void Convert(PDFA3Components pdfa3Components) throws Exception {
		File inputFile = new File(pdfa3Components.getInputFilePath());
		PDDocument doc = PDDocument.load(inputFile);
		File colorPFile = new File(pdfa3Components.getColorProfilePath());
		InputStream colorProfile = new FileInputStream(colorPFile);


		PDDocumentCatalog cat = makeA3compliant(doc, pdfa3Components);
		attachFile(doc, pdfa3Components.getEmbedFilePath());
		addOutputIntent(doc, cat, colorProfile);
		doc.setVersion(pdfVer);

		doc.save(pdfa3Components.getOutputFilePath());
		doc.close();
		File outputFile = new File(pdfa3Components.getOutputFilePath());
		if (outputFile.exists()) {
			System.out.println(pdfa3Components.getOutputFilePath());
		} else {
			System.out.println("Failed to convert.");
		}
	}

	private static void addOutputIntent(PDDocument doc, PDDocumentCatalog cat, InputStream colorProfile)
			throws IOException {
		if (cat.getOutputIntents().isEmpty()) {
			PDOutputIntent oi = new PDOutputIntent(doc, colorProfile);
			oi.setInfo("sRGB IEC61966-2.1");
			oi.setOutputCondition("sRGB IEC61966-2.1");
			oi.setOutputConditionIdentifier("sRGB IEC61966-2.1");
			oi.setRegistryName("http://www.color.org");
			cat.addOutputIntent(oi);
		}

	}

	private static void attachFile(PDDocument doc, String embedFilePath) throws IOException {
		PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();

		File embedFile = new File(embedFilePath);
		
		String subType = Files.probeContentType(FileSystems.getDefault().getPath(embedFilePath));
		String embedFileName = FilenameUtils.getName(embedFilePath);
		// first create the file specification, which holds the embedded file

		PDComplexFileSpecification fs = new PDComplexFileSpecification();
		fs.setFile(embedFileName);
		COSDictionary dict = fs.getCOSObject();
		// Relation "Source" for linking with eg. catalog
		dict.setName("AFRelationship", "Source");

		dict.setString("UF", embedFileName);

		InputStream is = new FileInputStream(embedFile);

		PDEmbeddedFile ef = new PDEmbeddedFile(doc, is);

		// set some of the attributes of the embedded file
		ef.setModDate(GregorianCalendar.getInstance());

		ef.setSize((int) embedFile.length());
		ef.setCreationDate(new GregorianCalendar());
		fs.setEmbeddedFile(ef);
		ef.setSubtype(subType);

		// now add the entry to the embedded file tree and set in the document.
		efTree.setNames(Collections.singletonMap(embedFileName, fs));

		// attachments are stored as part of the "names" dictionary in the
		PDDocumentCatalog catalog = doc.getDocumentCatalog();

		PDDocumentNameDictionary names = new PDDocumentNameDictionary(doc.getDocumentCatalog());
		names.setEmbeddedFiles(efTree);
		catalog.setNames(names);

		COSDictionary dict2 = catalog.getCOSObject();
		COSArray array = new COSArray();
		array.add(fs.getCOSObject());
		dict2.setItem("AF", array);
	}

	private static PDDocumentCatalog makeA3compliant(PDDocument doc, PDFA3Components pdfa3Components) throws Exception {
		PDDocumentCatalog cat = doc.getDocumentCatalog();
		PDDocumentInformation pdd = doc.getDocumentInformation();
		PDMetadata metadata = new PDMetadata(doc);
		cat.setMetadata(metadata);

		PDDocumentInformation pdi = new PDDocumentInformation();
		pdi.setProducer(pdd.getProducer());
		pdi.setAuthor(pdd.getAuthor());
		pdi.setTitle(pdd.getTitle());
		pdi.setSubject(pdd.getSubject());
		pdi.setKeywords(pdd.getKeywords());

		// Set OID
		// pdi.setCustomMetadataValue("OID", "10.2.3.65.5");
		doc.setDocumentInformation(pdi);

		// use for eTax invoice only
		Charset charset = StandardCharsets.UTF_8;

		byte[] fileBytes = Files.readAllBytes(new File(pdfa3Components.getXmpTemplatePath()).toPath());
		String content = new String(fileBytes, charset);
		content = content.replaceAll("@DocumentFileName", pdfa3Components.getDocumentFileName());
		content = content.replaceAll("@DocumentType", pdfa3Components.getDocumentType());
		content = content.replaceAll("@DocumentVersion", pdfa3Components.getDocumentVersion());
		
		byte[] editedBytes = content.getBytes(charset);

		metadata.importXMPMetadata(editedBytes);
		return cat;
	}
}
