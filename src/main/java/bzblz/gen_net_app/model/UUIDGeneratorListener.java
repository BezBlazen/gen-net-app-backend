package bzblz.gen_net_app.model;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import java.util.UUID;

@Component
public class UUIDGeneratorListener extends AbstractMongoEventListener<Object> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        super.onBeforeConvert(event);
        Object entity = event.getSource();
        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            if (field.isAnnotationPresent(GeneratedUUID.class)) {
                field.setAccessible(true);
                if (field.get(entity) == null) {
                    field.set(entity, UUID.randomUUID());
                }
            }
        });
    }
}