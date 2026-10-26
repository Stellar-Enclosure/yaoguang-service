package top.egon.cola.component.yaoguang.admin.permission;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import top.egon.cola.component.yaoguang.admin.knowledge.domain.dto.*;
import top.egon.cola.component.yaoguang.admin.shared.converter.KnowledgePermissionProjectionConverter;
import top.egon.cola.component.yaoguang.admin.wiki.domain.dto.*;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

/** The transport split changes permission ownership, not the existing JSON or validation contract. */
class YaoguangProjectionContractTest {
    private final ObjectMapper json = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void everyOperationProjectionPreservesAllPopulatedPropertiesAndNestedValues() throws Exception {
        var converter = new KnowledgePermissionProjectionConverter();
        int count = 0;
        for (Method method : KnowledgePermissionProjectionConverter.class.getDeclaredMethods()) {
            if (!java.lang.reflect.Modifier.isPublic(method.getModifiers())) continue;
            var type = json.constructType(method.getGenericParameterTypes()[0]);
            JsonNode data = example(type, 0);
            Object source = json.treeToValue(data, type);
            Object target = method.invoke(converter, source);
            JsonNode targetJson = json.valueToTree(target);
            JsonNode sourceJson = json.valueToTree(source);
            assertThat(targetJson).as(method.getName()).isEqualTo(sourceJson);
            count++;
        }
        assertThat(count).isEqualTo(11);
    }

    @Test
    void splitInputsKeepEveryConstraintAndValidationGroup() {
        Map<Class<?>, Class<?>> inputs = Map.of(
                KnowledgeBaseCreateCommandDTO.class, KnowledgeBaseCommandDTO.class,
                KnowledgeMemberWriteDTO.class, KnowledgeMemberDTO.class,
                KnowledgeMembersReplaceCommandDTO.class, KnowledgeMembersCommandDTO.class,
                WikiDraftWriteCommandDTO.class, WikiDraftCommandDTO.class,
                WikiSourceWriteDTO.class, WikiSourceDTO.class);
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            inputs.forEach((projection, original) -> {
                var baseline = validator.getConstraintsForClass(original);
                var actual = validator.getConstraintsForClass(projection);
                assertThat(actual.getConstrainedProperties().stream().map(value -> value.getPropertyName()).toList())
                        .as(projection.getName()).containsExactlyInAnyOrderElementsOf(
                                baseline.getConstrainedProperties().stream().map(value -> value.getPropertyName()).toList());
                baseline.getConstrainedProperties().forEach(property -> {
                    var projected = actual.getConstraintsForProperty(property.getPropertyName());
                    assertThat(projected.isCascaded()).isEqualTo(property.isCascaded());
                    Set<String> expected = new TreeSet<>();
                    property.getConstraintDescriptors().forEach(value -> expected.add(value.getAnnotation().toString()));
                    Set<String> found = new TreeSet<>();
                    projected.getConstraintDescriptors().forEach(value -> found.add(value.getAnnotation().toString()));
                    assertThat(found).as(projection.getName() + ":" + property.getPropertyName()).isEqualTo(expected);
                });
            });
        }
    }

    private JsonNode example(JavaType type, int depth) {
        if (depth > 8) throw new IllegalArgumentException("unexpected recursive public DTO");
        if (type.isEnumType()) return json.valueToTree(type.getRawClass().getEnumConstants()[0]);
        if (type.isTypeOrSubTypeOf(String.class)) return JsonNodeFactory.instance.textNode("sample");
        if (type.isTypeOrSubTypeOf(Instant.class)) return JsonNodeFactory.instance.textNode("2026-10-25T00:00:00Z");
        if (type.isTypeOrSubTypeOf(Boolean.class) || type.hasRawClass(boolean.class)) return JsonNodeFactory.instance.booleanNode(true);
        if (type.isPrimitive() || Number.class.isAssignableFrom(type.getRawClass())) return JsonNodeFactory.instance.numberNode(7);
        if (type.isCollectionLikeType()) return JsonNodeFactory.instance.arrayNode().add(example(type.getContentType(), depth + 1));
        var node = JsonNodeFactory.instance.objectNode();
        json.getSerializationConfig().introspect(type).findProperties().forEach(property -> {
            if (property.getPrimaryMember() != null) node.set(property.getName(), example(property.getPrimaryType(), depth + 1));
        });
        return node;
    }
}
