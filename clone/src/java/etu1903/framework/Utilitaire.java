/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1903.framework;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ITU
 */
public class Utilitaire {
    public static void main(String[] args) throws Exception
    {
        int[] list=new int[4];
        list[0]=1;
        list[1]=2;
        list[2]=3;
        list[3]=4;
        int[] listVaovao=Utilitaire.mamadikaTableauInt(list, 2);
        
        for(int i=0;i<listVaovao.length;i++)
            System.out.println("l["+i+"]="+listVaovao[i]);
    }

    @ClassIdentifier(id = "infoUrl")
    public static String infoUrl(String url, String nomDomaine) throws Exception {
        int index = url.indexOf(nomDomaine);
        if (index == -1) {
            // La sous-chaîne n'a pas été trouvée dans la chaîne originale
            throw new Exception("nom de domaine non valide");
        } else {
            // Supprime la sous-chaîne de la chaîne originale et renvoie la nouvelle chaîne
             url=url.substring(0, index) + url.substring(index + nomDomaine.length());
        }
        
        index = url.indexOf("?");
        if (index == -1) {
            // La sous-chaîne n'a pas été trouvée dans la chaîne originale
            return url;
        } else {
            // Supprime la sous-chaîne de la chaîne originale et renvoie la nouvelle chaîne
            return url.substring(0, index);
        }
    }
    
    public static String infoUrl2(String url) throws Exception {
          return url.substring(1,url.length());
    }
    
    public static List<Class<?>> getClasses2(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
    
//    public static String[] findMethodsAnnotatedWith(String packageName, String annotationValue) throws ClassNotFoundException, IOException, URISyntaxException, Exception {
//        List<String[]> result = new ArrayList<>();
//        for (Class<?> clazz : getClasses2(packageName)) {
//            for (Method method : clazz.getDeclaredMethods()) {
//                if (method.isAnnotationPresent(ClassIdentifier.class)) {
//                    Annotation annotation = method.getAnnotation(ClassIdentifier.class);
//                    if (annotation instanceof ClassIdentifier && ((ClassIdentifier) annotation).id().equals(annotationValue)) {
//                        String[] methodInfo = new String[2];
//                        methodInfo[0] = clazz.getName();
//                        methodInfo[1] = method.getName();
//                        result.add(methodInfo);
//                    }
//                }
//            }
//        }
//        if(result.size()>0)
//            return result.get(0);
//        throw new Exception("la methode n'existe pas");
//    }
    
    public static String[] findMethodsAnnotatedWith(List<Class<?>> classes, String annotationValue) throws ClassNotFoundException, IOException, URISyntaxException, Exception {
        List<String[]> result = new ArrayList<>();
        for (Class<?> clazz : classes) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(ClassIdentifier.class)) {
                    Annotation annotation = method.getAnnotation(ClassIdentifier.class);
                    if (annotation instanceof ClassIdentifier && ((ClassIdentifier) annotation).id().equals(annotationValue)) {
                        String[] methodInfo = new String[2];
                        methodInfo[0] = clazz.getName();
                        methodInfo[1] = method.getName();
                        result.add(methodInfo);
                    }
                }
            }
        }
        if(result.size()>0)
            return result.get(0);
        throw new Exception("la methode n'existe pas");
    }
    
    public static int[] mamadikaTableauInt(int[] listInt,int indiceVoloany)
    {
        int[] listIntVaovao=new int[listInt.length];
        int j=0;
        int k=0;
        for(int i=indiceVoloany;i<listInt.length+indiceVoloany;i++)
        {
            if(i>=listInt.length)
            {
                listIntVaovao[j]=listInt[k];
                k++;
            }
            else
                listIntVaovao[j]=listInt[i];
            j++;
        }
        return listIntVaovao;
    }
    
    
    public static HashMap<String,Mapping> getAnnotatedMethods(String packageName, Class<? extends Annotation> annotationClass) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = getClasses2(packageName);
        HashMap<String,Mapping> annotatedMethods = new HashMap<String,Mapping>();
        for (Class<?> cls : classes) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Annotation annotation = method.getAnnotation(annotationClass);
                if (annotation != null) {
                    annotatedMethods.put(((ClassIdentifier) annotation).id(), new Mapping( cls.getName(), method.getName()));
                }
            }
        }
        return annotatedMethods;
    }
}
