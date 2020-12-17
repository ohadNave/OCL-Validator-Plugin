////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package tudresden.ocl20.pivot.standalone.example;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.eclipse.emf.common.util.TreeIterator;
//import org.eclipse.emf.common.util.URI;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.ecore.resource.ResourceSet;
//import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
//import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
//import tudresden.ocl20.pivot.language.ocl.impl.RelationalOperationCallExpCSImpl;
//import tudresden.ocl20.pivot.language.ocl.resource.ocl.mopp.OclResource;
//import tudresden.ocl20.pivot.language.ocl.staticsemantics.OclStaticSemantics;
//import tudresden.ocl20.pivot.language.ocl.staticsemantics.OclStaticSemanticsException;
//import tudresden.ocl20.pivot.language.ocl.staticsemantics.postporcessor.OclStaticSemanticsProvider;
//import tudresden.ocl20.pivot.model.IModel;
//import tudresden.ocl20.pivot.parser.IOclParser;
//import tudresden.ocl20.pivot.parser.ParseException;
//import tudresden.ocl20.pivot.parser.SemanticException;
//import tudresden.ocl20.pivot.pivotmodel.Constraint;
//
//public class TestParser implements IOclParser {
//
//    public List<Constraint> doParse(IModel model, URI uri) throws ParseException {
//        return this.doParse(model, uri, true);
//    }
//
//    public List<Constraint> doParse(IModel model, URI uri, boolean addToModel) throws ParseException {
//        try {
//            ResourceSet rs = new ResourceSetImpl();
//            OclResource resource = new OclResource(uri);
//            rs.getResources().add(resource);
//            resource.setModel(model);
//            resource.load(Collections.EMPTY_MAP);
//            return this.staticSemanticsAnalysis(resource);
//        } catch (IOException var6) {
//            throw new ParseException(var6.getMessage(), var6);
//        }
//    }
//
//    public List<Constraint> parseOclString(String oclCode, IModel model) throws ParseException {
//        return this.parseOclString(oclCode, model, true);
//    }
//
//    public List<Constraint> parseOclString(String oclCode, IModel model, boolean addToModel) throws ParseException {
//        ResourceSet rs = new ResourceSetImpl();
//        OclResource resource = new OclResource(URI.createFileURI("temp.ocl"));
//        rs.getResources().add(resource);
//        resource.setModel(model);
//
//        try {
//            resource.load(new ByteArrayInputStream(oclCode.getBytes()), (Map)null);
//            return this.staticSemanticsAnalysis(resource);
//        } catch (IOException var7) {
//            throw new ParseException("Unable to load OCL file.", var7);
//        }
//    }
//
////     while (x.hasNext())
////        {
////            Object NextNode =x.next();
////            Class NodeClass = NextNode.getClass();
////            System.out.println("======================================================================================="+NodeClass.getName()+"=========================================================================================================");
////            Method[] ClassMethods= NodeClass.getMethods();
////            for(Method m:ClassMethods){
////                System.out.println(NodeClass.getName());
////                System.out.println("Method Name:"+m.getName());
////                System.out.println("Return Type:"+m.getGenericReturnType());
////                if(NodeClass.getName().equals("tudresden.ocl20.pivot.language.ocl.impl.ImplicitPropertyCallCSImpl")){
////                    System.out.println("OVER HERE BROOOOOOOOOOOOOOOOOOOOOO @^$#%^#$&%#$&$#&#$%^&#$%^#%");
////                    System.out.println(((tudresden.ocl20.pivot.language.ocl.impl.ImplicitPropertyCallCSImpl)NextNode).getProperty().toString());
////                }
////            }
////            System.out.println("=======================================================================================Fields=========================================================================================================");
////            Field[] classFields= NodeClass.getFields();
////            for(Field f:classFields){
////                System.out.println("field Name:"+f.getName());
////                System.out.println("Type:"+f.getType());
////            }
////            System.out.println("=================================================================================================================================================================");
////            System.out.println();
////        }
//
////        Class Satlabiba = RelationalOperationCallExpCSImpl.class;
////        Method[] ClassMethods= Satlabiba.getMethods();
////        for(Method m:ClassMethods){
////            System.out.println(Satlabiba.getName());
////            System.out.println("Method Name:"+m.getName());
////            System.out.println("Return Type:"+m.getGenericReturnType());
////        }
////        Field[] classFields= Satlabiba.getFields();
////        for(Field f:classFields){
////            System.out.println("field Name:"+f.getName());
////            System.out.println("Type:"+f.getType());
////        }
//
//
//
//
//    private List<Constraint> staticSemanticsAnalysis(OclResource resource) throws ParseException, SemanticException {
//        this.checkForErrors(resource);
//        OclStaticSemantics staticSemantics = OclStaticSemanticsProvider.getStaticSemantics(resource);
//        // I added from here
//        TreeIterator x = resource.getAllContents();
//        while (x.hasNext())
//        {
//            Object NextNode =x.next();
//            Class NodeClass = NextNode.getClass();
//            System.out.println("Class Name - "+NodeClass.getName() +"    :     toString: "+NextNode.toString());
//        }
//        // To here
//        List constraints;
//        try {
//            constraints = staticSemantics.cs2EssentialOcl((EObject)resource.getContents().get(0));
//        } catch (OclStaticSemanticsException var5) {
//            throw new SemanticException(var5.getMessage(), var5);
//        }
//
//        this.checkForErrors(resource);
//        return constraints;
//    }
//
//    private void checkForErrors(OclResource resource) throws ParseException {
//        if (!resource.getErrors().isEmpty()) {
//            StringBuffer errorMsg = new StringBuffer();
//            Iterator i$ = resource.getErrors().iterator();
//
//            while(i$.hasNext()) {
//                Diagnostic error = (Diagnostic)i$.next();
//                errorMsg.append("line " + error.getLine() + ", coloumn " + error.getColumn() + ": " + error.getMessage() + System.getProperty("line.separator"));
//            }
//
//            throw new SemanticException(errorMsg.toString());
//        }
//    }
//}
