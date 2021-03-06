package horse.ponecraft.core.unicorn;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import scala.tools.asm.Type;

public class HideBotaniaTabTransformer implements IClassTransformer
{
	class GuiResearchBrowserVisitor extends ClassVisitor
	{
		public GuiResearchBrowserVisitor(int api, ClassVisitor cv)
		{
			super(api, cv);
		}

		class ExtendEldritchConditionVisitor extends MethodVisitor
		{
			public ExtendEldritchConditionVisitor(int api, MethodVisitor mv)
			{
				super(api, mv);
			}

			@Override
			public void visitCode()
			{
				super.visitCode();

				Label notHumanus = new Label();

				super.visitVarInsn(Opcodes.ALOAD, 0);
				super.visitFieldInsn(Opcodes.GETFIELD, "thaumcraft/api/aspects/Aspect", "tag", Type.getObjectType("java/lang/String").getDescriptor());
				super.visitLdcInsn("humanus");
				super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
				super.visitJumpInsn(Opcodes.IFEQ, notHumanus);
				super.visitLdcInsn("Equus");
				super.visitInsn(Opcodes.ARETURN);
				super.visitLabel(notHumanus);
			}
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
		{
			MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

			if (name.equals("getName"))
			{
				return new ExtendEldritchConditionVisitor(this.api, mv);
			}

			return mv;
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if (name.equals("thaumcraft.api.aspects.Aspect"))
		{
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			GuiResearchBrowserVisitor cv = new GuiResearchBrowserVisitor(Opcodes.ASM4, cw);
			ClassReader cr = new ClassReader(basicClass);
			cr.accept(cv, 0);
			return cw.toByteArray();
		}

		return basicClass;
	}
}
