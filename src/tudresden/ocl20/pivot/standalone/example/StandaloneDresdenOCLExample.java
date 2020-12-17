package tudresden.ocl20.pivot.standalone.example;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import tudresden.ocl20.pivot.examples.pml.PmlPackage;
import tudresden.ocl20.pivot.examples.simple.Person;
import tudresden.ocl20.pivot.examples.simple.Professor;
import tudresden.ocl20.pivot.examples.simple.Student;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.language.ocl.resource.ocl.mopp.OclResource;
import tudresden.ocl20.pivot.language.ocl.staticsemantics.OclStaticSemantics;
import tudresden.ocl20.pivot.language.ocl.staticsemantics.OclStaticSemanticsException;
import tudresden.ocl20.pivot.language.ocl.staticsemantics.postporcessor.OclStaticSemanticsProvider;
import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.modelinstancetype.java.internal.modelinstance.JavaModelInstance;
import tudresden.ocl20.pivot.parser.ParseException;
import tudresden.ocl20.pivot.parser.SemanticException;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;
import tudresden.ocl20.pivot.tools.codegen.declarativ.IOcl2DeclSettings;
import tudresden.ocl20.pivot.tools.codegen.declarativ.Ocl2DeclCodeFactory;
import tudresden.ocl20.pivot.tools.codegen.declarativ.impl.Ocl2DeclSettings;
import tudresden.ocl20.pivot.tools.codegen.ocl2java.IOcl2JavaSettings;
import tudresden.ocl20.pivot.tools.codegen.ocl2java.Ocl2JavaFactory;
import tudresden.ocl20.pivot.tools.template.TemplatePlugin;

public class StandaloneDresdenOCLExample {


	public static void main(String[] args) throws Exception {

		String constraintID = "";
		String ecoreFilePath = "";
		String constraintName = "";
		String ObjectName = "";
		String OCL_expression = "";
		StandaloneFacade.INSTANCE.initialize(new URL("file:"
				+ new File("log4j.properties").getAbsolutePath()));
		BufferedReader reader = new BufferedReader(new FileReader("Constraints_To_Validate.txt"));
		File valids = new File("validOCL.txt");
		File invalid = new File("invalidOCL.txt");
//		if (ASTFile.createNewFile()) {
//			System.out.println("File created: " + ASTFile.getName());
//		} else {
//			System.out.println("File already exists.");
//		}
		FileWriter validWriter = new FileWriter("validOCL.txt");
		FileWriter invalidWriter = new FileWriter("invalidOCL.txt");
		String Line = reader.readLine();
		boolean First = true;
		boolean nonEmptyExpression = true;
		while (Line != null)
		{
			int counter = 1;
			int index = 1;
			String[] SplitLine = Line.substring(1).split("#");
			constraintID = SplitLine[0];
			ecoreFilePath = SplitLine[1];
			constraintName = SplitLine[2];
			ObjectName = SplitLine[3];
			if( SplitLine.length == 5 ){
				OCL_expression = SplitLine[4];
				nonEmptyExpression = true;
			}

			if(nonEmptyExpression){
				File File = new File(ecoreFilePath);
				IModel model = StandaloneFacade.INSTANCE.loadEcoreModel(File);

				try{
					List<Constraint> list = parseOclString("-- The id of a plug-in must be defined.\n" +
									"context " + ObjectName+ " \n" +
									"inv " + constraintName+ ": \n" +
									OCL_expression + "\n" +
									"\n"
							,model);

					for (Constraint C : list) {
						System.out.println(C);
						System.out.println("Constrain NO" + constraintID);
						counter++;
					}
					validWriter.write(OCL_expression);
					validWriter.flush();
				}
				catch(SemanticException e){
					invalidWriter.write(OCL_expression);
					invalidWriter.flush();
//				e.printStackTrace();
				}
				Line = reader.readLine();
			}
			}

		validWriter.close();
		invalidWriter.close();

	}


	//		List<Constraint> list = parseOclString("-- The id of a plug-in must be defined.\n" +
//				"context Plugin\n" +
//				"inv pluginIdIsDefined: \n" +
//				"  not self.id.oclIsUndefined()\n" +
//				"\n"
//				,model);


//	private static void pml() {
//
//		/*
//		 * PML
//		 */
//		System.out.println();
//		System.out.println("Plug-in Modeling Language (PML)");
//		System.out.println("-------------------------------");
//		System.out.println();
//
//		try {
//			IModel model = StandaloneFacade.INSTANCE.loadEcoreModel(ecoreFile);
//
//			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
//					"pml", new XMIResourceFactoryImpl());
//			PmlPackage.eINSTANCE.eClass();
//
//			IModelInstance modelInstance = StandaloneFacade.INSTANCE
//					.loadEcoreModelInstance(model, pmlInstance);
//
//			List<Constraint> constraintList = StandaloneFacade.INSTANCE
//					.parseOclConstraints(model, pmlOclConstraints);
//
//			for (IInterpretationResult result : StandaloneFacade.INSTANCE
//					.interpretEverything(modelInstance, constraintList)) {
//				System.out.println("  " + result.getModelObject() + ": "
//						+ result.getResult());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


	private static File getUMLResources() {
		return new File(
				"lib/org.eclipse.uml2.uml.resources_3.1.0.v201005031530.jar");
	}






	public List<Constraint> doParse(IModel model, URI uri) throws ParseException {
		return doParse(model, uri, true);
	}

	public List<Constraint> doParse(IModel model, URI uri, boolean addToModel) throws ParseException {
		try {
			ResourceSet rs = new ResourceSetImpl();
			OclResource resource = new OclResource(uri);
			rs.getResources().add(resource);
			resource.setModel(model);
			resource.load(Collections.EMPTY_MAP);
			return staticSemanticsAnalysis(resource);
		} catch (IOException var6) {
			throw new ParseException(var6.getMessage(), var6);
		}
	}

	public static List<Constraint> parseOclString(String oclCode, IModel model) throws ParseException {
		return parseOclString(oclCode, model, true);
	}

	public static List<Constraint> parseOclString(String oclCode, IModel model, boolean addToModel) throws ParseException {
		ResourceSet rs = new ResourceSetImpl();
		OclResource resource = new OclResource(URI.createFileURI("temp.ocl"));
		rs.getResources().add(resource);
		resource.setModel(model);

		try {
			resource.load(new ByteArrayInputStream(oclCode.getBytes()), (Map)null);
			return staticSemanticsAnalysis(resource);
		} catch (IOException var7) {
			throw new ParseException("Unable to load OCL file.", var7);
		}
	}

//     while (x.hasNext())
//        {
//            Object NextNode =x.next();
//            Class NodeClass = NextNode.getClass();
//            System.out.println("======================================================================================="+NodeClass.getName()+"=========================================================================================================");
//            Method[] ClassMethods= NodeClass.getMethods();
//            for(Method m:ClassMethods){
//                System.out.println(NodeClass.getName());
//                System.out.println("Method Name:"+m.getName());
//                System.out.println("Return Type:"+m.getGenericReturnType());
//                if(NodeClass.getName().equals("tudresden.ocl20.pivot.language.ocl.impl.ImplicitPropertyCallCSImpl")){
//                    System.out.println("OVER HERE BROOOOOOOOOOOOOOOOOOOOOO @^$#%^#$&%#$&$#&#$%^&#$%^#%");
//                    System.out.println(((tudresden.ocl20.pivot.language.ocl.impl.ImplicitPropertyCallCSImpl)NextNode).getProperty().toString());
//                }
//            }
//            System.out.println("=======================================================================================Fields=========================================================================================================");
//            Field[] classFields= NodeClass.getFields();
//            for(Field f:classFields){
//                System.out.println("field Name:"+f.getName());
//                System.out.println("Type:"+f.getType());
//            }
//            System.out.println("=================================================================================================================================================================");
//            System.out.println();
//        }

//        Class Satlabiba = RelationalOperationCallExpCSImpl.class;
//        Method[] ClassMethods= Satlabiba.getMethods();
//        for(Method m:ClassMethods){
//            System.out.println(Satlabiba.getName());
//            System.out.println("Method Name:"+m.getName());
//            System.out.println("Return Type:"+m.getGenericReturnType());
//        }
//        Field[] classFields= Satlabiba.getFields();
//        for(Field f:classFields){
//            System.out.println("field Name:"+f.getName());
//            System.out.println("Type:"+f.getType());
//        }




	private static List<Constraint> staticSemanticsAnalysis(OclResource resource) throws ParseException, SemanticException {
		checkForErrors(resource);
		OclStaticSemantics staticSemantics = OclStaticSemanticsProvider.getStaticSemantics(resource);
		// I added from here
		TreeIterator x = resource.getAllContents();
		while (x.hasNext())
		{
			Object NextNode =x.next();
			Class NodeClass = NextNode.getClass();
			System.out.println("Class Name - "+NodeClass.getName() +"    :     toString: "+NextNode.toString());
		}
		// To here
		List constraints;
		try {
			constraints = staticSemantics.cs2EssentialOcl((EObject)resource.getContents().get(0));
		} catch (OclStaticSemanticsException var5) {
			throw new SemanticException(var5.getMessage(), var5);
		}

		checkForErrors(resource);
		return constraints;
	}

	private static void checkForErrors(OclResource resource) throws ParseException {
		if (!resource.getErrors().isEmpty()) {
			StringBuffer errorMsg = new StringBuffer();
			Iterator i$ = resource.getErrors().iterator();

			while(i$.hasNext()) {
				Resource.Diagnostic error = (Resource.Diagnostic)i$.next();
				errorMsg.append("line " + error.getLine() + ", coloumn " + error.getColumn() + ": " + error.getMessage() + System.getProperty("line.separator"));
			}

			throw new SemanticException(errorMsg.toString());
		}
	}





}
