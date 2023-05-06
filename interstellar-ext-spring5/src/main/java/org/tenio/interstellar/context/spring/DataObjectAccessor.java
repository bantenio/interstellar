package org.tenio.interstellar.context.spring;

import org.springframework.asm.MethodVisitor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.CodeFlow;
import org.springframework.expression.spel.CompilablePropertyAccessor;
import org.springframework.util.Assert;
import org.tenio.interstellar.context.DataObject;

public class DataObjectAccessor implements CompilablePropertyAccessor {

    @Override
    public boolean isCompilable() {
        return true;
    }

    @Override
    public Class<?> getPropertyType() {
        return Object.class;
    }

    @Override
    public void generateCode(String propertyName, MethodVisitor mv, CodeFlow cf) {
        String descriptor = cf.lastDescriptor();
        if (descriptor == null || !descriptor.equals("Lorg/tenio/interstellar/context/DataObject")) {
            if (descriptor == null) {
                cf.loadTarget(mv);
            }
            CodeFlow.insertCheckCast(mv, "Lorg/tenio/interstellar/context/DataObject");
        }
        mv.visitLdcInsn(propertyName);
        mv.visitMethodInsn(INVOKEINTERFACE, "org/tenio/interstellar/context/DataObject", "getValue", "(Ljava/lang/String;)Ljava/lang/Object;", true);
    }

    @Override
    public Class<?>[] getSpecificTargetClasses() {
        return new Class<?>[]{DataObject.class};
    }

    @Override
    public boolean canRead(EvaluationContext context, Object target, String name) throws AccessException {
        return (target instanceof DataObject && ((DataObject) target).containsKey(name));
    }

    @Override
    public TypedValue read(EvaluationContext context, Object target, String name) throws AccessException {
        Assert.state(target instanceof DataObject, "Target must be of type DataObject");
        DataObject map = (DataObject) target;
        Object value = map.getValue(name);
        if (value == null && !map.containsKey(name)) {
            throw new AccessException("no such field " + name);
        }
        return new TypedValue(value);
    }

    @Override
    public boolean canWrite(EvaluationContext context, Object target, String name) throws AccessException {
        return true;
    }

    @Override
    public void write(EvaluationContext context, Object target, String name, Object newValue) throws AccessException {
        Assert.state(target instanceof DataObject, "Target must be a DataObject");
        DataObject map = (DataObject) target;
        map.put(name, newValue);
    }
}
