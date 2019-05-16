package it.smartcommunitylab.bridge.personaldata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;

/**
 * Hello world!
 *
 */
public class PersonalDataApp {
	
	public void extraxtCasCVTemplate(String inputFile, String outputFile, 
			boolean skipPersonalData) throws Exception {
		TextDocument document = TextDocument.loadDocument(inputFile);
		Paragraph initParagraph = document.getParagraphByIndex(0, true);
		if(!initParagraph.getTextContent().strip().startsWith("CURRICULUM VITAE")) {
			throw new RuntimeException("formato non corretto");
		}
		Iterator<Section> sectionIterator = document.getSectionIterator();
		while(sectionIterator.hasNext()) {
			Section section = sectionIterator.next();
			Iterator<Paragraph> paragraphIterator = section.getParagraphIterator();
			int count = 0;
			boolean skipSection = false;
			while(paragraphIterator.hasNext() && !skipSection) {
				Paragraph paragraph = paragraphIterator.next();
				if(count == 0) {
					if(!paragraph.getTextContent().strip().startsWith("Nome e Cognome")) {
						skipSection = true;
						continue;
					}
				}
				if((count == 1) && !paragraph.getTextContent().strip().startsWith("Residenza")) {
					throw new RuntimeException("formato non corretto");
				}
				if((count == 2) && !paragraph.getTextContent().strip().startsWith("Nato a")) {
					throw new RuntimeException("formato non corretto");
				}
				if((count == 4) && !paragraph.getTextContent().strip().startsWith("Cittadinanza")) {
					throw new RuntimeException("formato non corretto");
				}				
				if((count == 5) && !paragraph.getTextContent().strip().startsWith("Codice Fiscale")) {
					throw new RuntimeException("formato non corretto");
				}				
				if((count >= 8) && (count <= 11)) {
					paragraph.setTextContent(null);
				}
				if(count >= 13) {
					paragraph.setTextContent(null);
				}
				count++;
			}
		}
		document.save(outputFile);
	}
	
	public void extraxtCasModelloSchedaTemplate(String inputFile, String outputFile, 
			boolean skipPersonalData) throws Exception {
		TextDocument document = TextDocument.loadDocument(inputFile);
		List<Table> tableList = document.getTableList();
		Table table = tableList.get(0);
		if(!table.getCellByPosition(0, 0).getStringValue().strip().startsWith("Nome") || 
				!table.getCellByPosition(0, 1).getStringValue().strip().startsWith("Cognome") ||
				!table.getCellByPosition(0, 2).getStringValue().strip().startsWith("Data di Nascita")) {
			throw new RuntimeException("formato non corretto");
		}
		if(skipPersonalData) {
			table.removeRowsByIndex(0, table.getRowCount());
		} else {
			for(Row row : table.getRowList()) {
				String field = row.getCellByIndex(0).getStringValue();
				if(field.isBlank()) {
					continue;
				}
				field = field.strip();
				if(field.startsWith("Nome") || field.startsWith("Cognome")) {
					row.getCellByIndex(1).removeContent();
				}
				if(field.startsWith("Data")) {
					row.getCellByIndex(1).removeContent();
					row.getCellByIndex(1).setDateValue(new GregorianCalendar());
					row.getCellByIndex(1).setStringValue(null);
					row.getCellByIndex(1).setDisplayText(null);
				}
			}
		}
		document.save(outputFile);
	}
	
	public void extraxtSparProgettoFormativoTemplate(String inputFile, String outputFile, 
			boolean skipPersonalData) throws Exception {
		TextDocument document = TextDocument.loadDocument(inputFile);
		List<Table> tableList = document.getTableList();
		Table table = tableList.get(0);
		if(!table.getCellByPosition(0, 0).getStringValue().strip().startsWith("Cognome") || 
				!table.getCellByPosition(2, 0).getStringValue().strip().startsWith("Nome") ||
				!table.getCellByPosition(0, 1).getStringValue().strip().startsWith("Codice Fiscale") ||
				!table.getCellByPosition(0, 2).getStringValue().strip().startsWith("Nato a")) {
			throw new RuntimeException("formato non corretto");
		}
		if(skipPersonalData) {
			table.removeRowsByIndex(0, table.getRowCount());
		} else {
			//cognome
			Cell cell = table.getCellByPosition(1, 0);
			cell.removeContent();
			cell.setStringValue(null);
			//nome
			cell = table.getCellByPosition(3, 0);
			cell.removeContent();
			cell.setStringValue(null);
			//codice fiscale
			cell = table.getCellByPosition(1, 1);
			cell.removeContent();
			cell.setStringValue(null);
			//nato a
			cell = table.getCellByPosition(1, 2);
			cell.removeContent();
			cell.setStringValue(null);
			cell = table.getCellByPosition(2, 2);
			cell.removeContent();
			cell.setStringValue(null);
			cell = table.getCellByPosition(3, 2);
			cell.removeContent();
			cell.setStringValue(null);
		}
		table = tableList.get(3);
		if(!table.getCellByPosition(0, 0).getStringValue().strip().startsWith("Residente a") ||
				!table.getCellByPosition(3, 0).getStringValue().strip().startsWith("cap") ||
				!table.getCellByPosition(0, 1).getStringValue().strip().startsWith("Indirizzo") ||
				!table.getCellByPosition(0, 2).getStringValue().strip().startsWith("Domiciliato a") ||
				!table.getCellByPosition(0, 3).getStringValue().strip().startsWith("Telefono") ||
				!table.getCellByPosition(3, 3).getStringValue().strip().startsWith("email") ||
				!table.getCellByPosition(0, 4).getStringValue().strip().startsWith("Titolo di studio")) {
			throw new RuntimeException("formato non corretto");
		}
		if(skipPersonalData) {
			table.removeRowsByIndex(0, table.getRowCount());
		} else {
			//residente
			Cell cell = table.getCellByPosition(1, 0);
			cell.removeContent();
			cell.setStringValue(null);
			//cap
			cell = table.getCellByPosition(4, 0);
			cell.removeContent();
			cell.setDoubleValue(0.0);
			cell.setStringValue(null);
			//indirizzo
			cell = table.getCellByPosition(1, 1);
			cell.removeContent();
			cell.setStringValue(null);
			//domicilio
			cell = table.getCellByPosition(2, 2);
			cell.removeContent();
			cell.setStringValue(null);
			//telefono
			cell = table.getCellByPosition(1, 3);
			cell.removeContent();
			cell.setStringValue(null);
			//email
			cell = table.getCellByPosition(4, 3);
			cell.removeContent();
			cell.setStringValue(null);
		}
		document.save(outputFile);
	}
	
	public void extraxtSparSchedaPersonaleTemplate(String inputFile, String outputFile, 
			boolean skipPersonalData) throws Exception {
		TextDocument document = TextDocument.loadDocument(inputFile);
		List<Table> tableList = document.getTableList();
		Table table = tableList.get(0);
		if(!table.getCellByPosition(0, 0).getStringValue().strip().startsWith("COGNOME") || 
				!table.getCellByPosition(0, 1).getStringValue().strip().startsWith("NOME") ||
				!table.getCellByPosition(0, 2).getStringValue().strip().startsWith("DATA DI NASCITA") ||
				!table.getCellByPosition(0, 3).getStringValue().strip().startsWith("NAZIONALITÀ") ||
				!table.getCellByPosition(0, 4).getStringValue().strip().startsWith("DOMICILIO") ||
				!table.getCellByPosition(0, 5).getStringValue().strip().startsWith("CODICE FISCALE") ||
				!table.getCellByPosition(0, 6).getStringValue().strip().startsWith("TESSERA SANITARIA") ||
				!table.getCellByPosition(0, 7).getStringValue().strip().startsWith("ISCRIZIONE CPI") ||
				!table.getCellByPosition(0, 8).getStringValue().strip().startsWith("TELEFONO")) {
			throw new RuntimeException("formato non corretto");
		}
		if(skipPersonalData) {
			table.removeRowsByIndex(0, table.getRowCount());
		} else {
			for(Row row : table.getRowList()) {
				if((row.getRowIndex()==3) || (row.getRowIndex()==9) ||
						(row.getRowIndex()==10) || (row.getRowIndex()==11)) {
					continue;
				} else if((row.getRowIndex()==13) || (row.getRowIndex()==16)) {
					Cell cell = row.getCellByIndex(1);
					cell.removeContent();
					cell.setDateValue(new GregorianCalendar());
					cell.setStringValue(null);
					cell = row.getCellByIndex(2);
					cell.removeContent();
					cell.setDateValue(new GregorianCalendar());					
					cell.setStringValue(null);					
				} else if((row.getRowIndex()==2) || (row.getRowIndex()==14) ||
						(row.getRowIndex()==15)) {
					Cell cell = row.getCellByIndex(1);
					cell.removeContent();
					cell.setDateValue(new GregorianCalendar());
					cell.setStringValue(null);
				} else {
					Cell cell = row.getCellByIndex(1);
					cell.removeContent();
					cell.setStringValue(null);
				}
			}
		}
		document.save(outputFile);
	}
	
	public static void main(String[] args) {
		PersonalDataApp app = new PersonalDataApp();
		List<File> files = new ArrayList<>();
		try {
			for (int i = 0; i < args.length; i++) {
				String dir = args[i];
				Files.walk(Paths.get(dir))
				.filter(path -> 
					path.toFile().isFile() && path.toString().toLowerCase().endsWith(".odt")
				)
				.forEach(path -> 
					files.add(path.toFile())
				);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		List<String> fileMatchList = new ArrayList<>();
		String outputFile;
		String inputFile;
		int fileId = 0;
		for(File file : files) {
			inputFile = file.getAbsolutePath();
			outputFile = "./odt/" + fileId + ".odt";
			try {
				if(file.getName().toUpperCase().startsWith("CAS_CV")) {
					app.extraxtCasCVTemplate(inputFile, outputFile, false);
				} else if(file.getName().toUpperCase().startsWith("CAS_MODELLO")) {
					app.extraxtCasModelloSchedaTemplate(inputFile, outputFile, false);
				} else if(file.getName().toUpperCase().startsWith("SPRAR_PROGETTO")) {
					app.extraxtSparProgettoFormativoTemplate(inputFile, outputFile, false);
				} else if(file.getName().toUpperCase().startsWith("SPRAR_SCHEDA")) {
					app.extraxtSparSchedaPersonaleTemplate(inputFile, outputFile, false);
				} else {
					continue;
				}
				fileMatchList.add(fileId + "," + inputFile);
				fileId++;
			} catch (Exception e) {
				System.err.println(inputFile + ":" + e.getMessage());
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("fileMap.csv"));
			for(String item : fileMatchList) {
				writer.write(item + "\n");
			}
	    writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
}
