package com.gamephone.common.jackjson;

import java.io.IOException;
import java.nio.charset.Charset;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.Assert;

public class MappingJacksonModifyHttpMessageConverter extends MappingJacksonHttpMessageConverter {

    private ObjectMapper objectMapper=new ObjectMapper();

    private boolean prefixJson;

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
        HttpMessageNotWritableException {

        JsonEncoding encoding=getJsonEncoding(outputMessage.getHeaders().getContentType());
        JsonGenerator jsonGenerator=this.objectMapper.getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
        try {
            if(this.prefixJson) {
                jsonGenerator.writeRaw("{} && ");
            }
            if(object instanceof ResponseBodyWrapper) {
                ResponseBodyWrapper responseBody=(ResponseBodyWrapper)object;
                this.objectMapper.writerWithView(responseBody.getView()).writeValue(jsonGenerator, responseBody.getObject());
            } else {
                this.objectMapper.writeValue(jsonGenerator, object);
            }
        } catch(IOException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "ObjectMapper must not be null");
        this.objectMapper=objectMapper;
        super.setObjectMapper(objectMapper);
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson=prefixJson;
        super.setPrefixJson(prefixJson);
    }
    
    private JsonEncoding getJsonEncoding(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            Charset charset = contentType.getCharSet();
            for (JsonEncoding encoding : JsonEncoding.values()) {
                if (charset.name().equals(encoding.getJavaName())) {
                    return encoding;
                }
            }
        }
        return JsonEncoding.UTF8;
    }
}
