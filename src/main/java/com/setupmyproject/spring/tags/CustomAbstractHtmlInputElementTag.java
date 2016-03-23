package com.setupmyproject.spring.tags;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.tags.form.AbstractMultiCheckedElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * Copia da classe do Spring MVC para abrir espaço para adicicao de novos
 * atributos dinâmicos.
 * 
 * @author alberto
 *
 */
public abstract class CustomAbstractHtmlInputElementTag extends
		AbstractMultiCheckedElementTag {

	/**
	 * Renders the '{@code input type="radio"}' element with the configured
	 * {@link #setItems(Object)} values. Marks the element as checked if the
	 * value matches the bound value.
	 */
	@Override
	@SuppressWarnings("rawtypes")
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		Object items = getItems();
		Object itemsObject = (items instanceof String ? evaluate("items", items)
				: items);

		String itemValue = getItemValue();
		String itemLabel = getItemLabel();
		String valueProperty = (itemValue != null ? ObjectUtils
				.getDisplayString(evaluate("itemValue", itemValue)) : null);
		String labelProperty = (itemLabel != null ? ObjectUtils
				.getDisplayString(evaluate("itemLabel", itemLabel)) : null);

		Class<?> boundType = getBindStatus().getValueType();
		if (itemsObject == null && boundType != null && boundType.isEnum()) {
			itemsObject = boundType.getEnumConstants();
		}

		if (itemsObject == null) {
			throw new IllegalArgumentException(
					"Attribute 'items' is required and must be a Collection, an Array or a Map");
		}

		if (itemsObject.getClass().isArray()) {
			Object[] itemsArray = (Object[]) itemsObject;
			for (int i = 0; i < itemsArray.length; i++) {
				Object item = itemsArray[i];
				writeObjectEntry(tagWriter, valueProperty, labelProperty, item,
						i);
			}
		} else if (itemsObject instanceof Collection) {
			final Collection<?> optionCollection = (Collection<?>) itemsObject;
			int itemIndex = 0;
			for (Iterator<?> it = optionCollection.iterator(); it.hasNext(); itemIndex++) {
				Object item = it.next();
				writeObjectEntry(tagWriter, valueProperty, labelProperty, item,
						itemIndex);
			}
		} else if (itemsObject instanceof Map) {
			final Map<?, ?> optionMap = (Map<?, ?>) itemsObject;
			int itemIndex = 0;
			for (Iterator it = optionMap.entrySet().iterator(); it.hasNext(); itemIndex++) {
				Map.Entry entry = (Map.Entry) it.next();
				writeMapEntry(tagWriter, valueProperty, labelProperty, entry,
						itemIndex);
			}
		} else {
			throw new IllegalArgumentException(
					"Attribute 'items' must be an array, a Collection or a Map");
		}

		return SKIP_BODY;
	}

	private void writeObjectEntry(TagWriter tagWriter, String valueProperty,
			String labelProperty, Object item, int itemIndex)
			throws JspException {

		BeanWrapper wrapper = PropertyAccessorFactory
				.forBeanPropertyAccess(item);
		Object renderValue;
		if (valueProperty != null) {
			renderValue = resolveProperty(valueProperty, wrapper);
		} else if (item instanceof Enum) {
			renderValue = ((Enum<?>) item).name();
		} else {
			renderValue = item;
		}
		Object renderLabel = (labelProperty != null ? resolveProperty(
				labelProperty, wrapper) : item);
		writeElementTag(tagWriter, item, renderValue, renderLabel, itemIndex);
	}

	private Object resolveProperty(String labelProperty, BeanWrapper wrapper) {
		try {
			return wrapper.getPropertyValue(labelProperty);
		} catch (NotReadablePropertyException exception) {
			String restOfTheProperty = labelProperty.substring(1);
			String firstLetter = labelProperty.substring(0, 1).toUpperCase();
			Object target = wrapper.getWrappedInstance();
			try {
				return target.getClass().getMethod("get"+firstLetter.concat(restOfTheProperty)).invoke(target).toString();
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void writeMapEntry(TagWriter tagWriter, String valueProperty,
			String labelProperty, Map.Entry<?, ?> entry, int itemIndex)
			throws JspException {

		Object mapKey = entry.getKey();
		Object mapValue = entry.getValue();
		BeanWrapper mapKeyWrapper = PropertyAccessorFactory
				.forBeanPropertyAccess(mapKey);
		BeanWrapper mapValueWrapper = PropertyAccessorFactory
				.forBeanPropertyAccess(mapValue);
		Object renderValue = (valueProperty != null ? resolveProperty(
				valueProperty, mapKeyWrapper) : mapKey.toString());
		Object renderLabel = (labelProperty != null ? resolveProperty(
				labelProperty, mapValueWrapper) : mapValue.toString());
		writeElementTag(tagWriter, mapKey, renderValue, renderLabel, itemIndex);
	}

	private void writeElementTag(TagWriter tagWriter, Object item,
			Object value, Object label, int itemIndex) throws JspException {

		tagWriter.startTag(getElement());
		if (itemIndex > 0) {
			Object resolvedDelimiter = evaluate("delimiter", getDelimiter());
			if (resolvedDelimiter != null) {
				tagWriter.appendValue(resolvedDelimiter.toString());
			}
		}
		tagWriter.startTag("input");
		String id = resolveId();
		writeOptionalAttribute(tagWriter, "id", id);
		writeOptionalAttribute(tagWriter, "name", getName());
		writeOptionalAttributes(tagWriter, item);
		tagWriter.writeAttribute("type", getInputType());
		renderFromValue(item, value, tagWriter);
		tagWriter.endTag();
		tagWriter.startTag("label");
		tagWriter.writeAttribute("for", id);
		tagWriter.appendValue(convertToDisplayString(label));
		tagWriter.endTag();
		tagWriter.endTag();
	}

	/**
	 * If you want to add an optional property associated with the current item.
	 * 
	 * @param tagWriter
	 * @param item
	 * @throws JspException
	 */
	protected void writeOptionalAttributes(TagWriter tagWriter, Object item)
			throws JspException {

	}

}
