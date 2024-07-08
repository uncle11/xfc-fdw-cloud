package com.xfc.dict.anno;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.xfc.dict.service.IDictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Objects;

/**
 * Description: new java files header.
 *
 * @author hjx
 * @date 2024/6/27 8:52
 */

@Component
public class DictSerializer extends StdSerializer<Object> implements ContextualSerializer {
    private IDictionaryValueService dictionaryValueService;
    private String type;

    @Autowired
    public DictSerializer(IDictionaryValueService dictionaryValueService) {
        super(Object.class);
        this.dictionaryValueService = dictionaryValueService;
    }

    public DictSerializer(String type, IDictionaryValueService dictionaryValueService) {
        super(Object.class);
        this.type = type;
        this.dictionaryValueService = dictionaryValueService;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeObject(value);
            return;
        }

        String label = null;
        if (dictionaryValueService != null && type != null) {
            try {
                String response = dictionaryValueService.getLabelByValue(value.toString());
                label = response; // 设置为空时返回 "null"
            } catch (RuntimeException e) {
                label = null;
            }
        }
        gen.writeObject(value);
        gen.writeFieldName(gen.getOutputContext().getCurrentName() + "Label");
        gen.writeObject(label);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            Dict dict = property.getAnnotation(Dict.class);
            if (dict != null) {
                return new DictSerializer(dict.type(), dictionaryValueService);
            }
        }
        return this;
    }


}