package rnd.mywt.client.bean;

import rnd.expression.Expression;
import rnd.expression.XChangeEvent;
import rnd.expression.XChangeListener;
import rnd.mywt.client.expression.BeanListPropertyExpression;
import rnd.mywt.client.expression.BeanPropertyExpression;
import rnd.mywt.client.mvc.field.Field;
import rnd.mywt.client.mvc.field.Table;
import rnd.mywt.client.mvc.page.form.Form;

public class BindingManager {

	public static void initForm(Form form, _BoundBean bean) {
		// Logger.startMethod("BindingManager", "initForm");

		for (Field field : form.getFields()) {

			Expression beanExp = field instanceof Table ? new BeanListPropertyExpression(field.getBoundTo()) : new BeanPropertyExpression(field.getBoundTo());
			Expression fieldExp = new BeanPropertyExpression(field.getFieldProperty());

			fieldExp.setValue(field, beanExp.getValue(bean));

		}

		// Logger.endMethod("BindingManager", "initForm");
	}

	public static void bindForm(Form form, _BoundBean bean) {
//		Logger.startMethod("BindingManager", "bindForm");

		for (Field field : form.getFields()) {

//			Logger.log("field", field);
//			Logger.log("FieldProperty", field.getFieldProperty());
//			Logger.log("BoundTo", field.getBoundTo());

			if (field instanceof Table) {
				Expression beanExp = new BeanListPropertyExpression(field.getBoundTo());
				field.setValue(field.getFieldProperty(), beanExp.getValue(bean));
			} else {
				Expression fieldExp = new BeanPropertyExpression(field.getFieldProperty());
				Expression beanExp = new BeanPropertyExpression(field.getBoundTo());
				bindExpression(field, fieldExp, bean, beanExp);
				bindExpression(bean, beanExp, field, fieldExp);

			}
		}

//		Logger.endMethod("BindingManager", "bindForm");
	}

	// TODO unbindForm
	public static void unbindForm(Form form, _BoundBean bean) {
	}

	public static void bindExpression(Object srcObject, Expression srcExp, final Object trgObject, final Expression trgExp) {
		srcExp.addXChangeListener(srcObject, new XChangeListener() {
			public void stateChanged(XChangeEvent changeEvent) {
				// Logger.startMethod("XChangeListener", "stateChanged");
				trgExp.setValue(trgObject, changeEvent.getNewValue());
				// Logger.endMethod("XChangeListener", "stateChanged");
			}
		});
	}
}
