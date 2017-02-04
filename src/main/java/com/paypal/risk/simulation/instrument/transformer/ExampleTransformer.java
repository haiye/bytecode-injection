/**
 * 
 */
package com.paypal.risk.simulation.instrument.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * This example class shows how to manipulate / replace an existing class' byte
 * code.
 * 
 * @author xchen7
 *
 */
public class ExampleTransformer implements ClassFileTransformer {
    
    /**
     * Inner visitor class.
     * 
     * @author xchen7
     *
     */
    static class ExampleClassVisitor extends ClassVisitor {

        public ExampleClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                String signature, String[] exceptions) {

            if ("putReturnValueInContext".equals(name)) {
                System.out.println("Visiting putReturnValueInContext()");

                /**
                 * First remove method putReturnValueInContext() from byte code.
                 * See http://asm.ow2.org/doc/faq.html#Q1
                 */
                return null;
            }

            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    public byte[] transform(ClassLoader loader, String className,
            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {

        if ("com/paypal/risk/idi/variables/AbstractBaseVariable"
                .equals(className)) {
            System.out.println("transforming class AbstractBaseVariable");
            byte[] bytecode = new byte[classfileBuffer.length];
            System.arraycopy(classfileBuffer, 0, bytecode, 0,
                    classfileBuffer.length);

            /*
             * Pass the original Java byte code to class reader.
             */
            ClassReader cr = new ClassReader(bytecode);
            ClassWriter cw = new ClassWriter(0);
            ClassVisitor cv = new ExampleClassVisitor(Opcodes.ASM5, cw);
            cr.accept(cv, 0);

            /*
             * Rewrite the original AbstractBaseVariable#putReturnValueInContext() method.
             */
            {
                MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC,
                        "putReturnValueInContext",
                        "(Ljava/lang/Object;Ljava/util/Map;)V",
                        "(Ljava/lang/Object;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;)V",
                        null);
                mv.visitCode();
                mv.visitVarInsn(Opcodes.ALOAD, 2);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "com/paypal/risk/idi/variables/AbstractBaseVariable",
                        "getName", "()Ljava/lang/String;", false);
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map",
                        "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
                        true);

                /*
                 * ---------- Inject code begin ----------
                 * Injected:
                 *   System.out.println(getName());
                 *   System.out.println(value);
                 */
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
                        "Ljava/io/PrintStream;");
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "com/paypal/risk/idi/variables/AbstractBaseVariable",
                        "getName", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
                        "println", "(Ljava/lang/String;)V", false);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
                        "Ljava/io/PrintStream;");
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
                        "println", "(Ljava/lang/Object;)V", false);
                /*
                 * ---------- Inject code end ----------
                 */

                mv.visitInsn(Opcodes.POP);
                mv.visitInsn(Opcodes.RETURN);
                mv.visitMaxs(3, 3);
                mv.visitEnd();
            }

            return cw.toByteArray();
        }

        return null;
    }

}
